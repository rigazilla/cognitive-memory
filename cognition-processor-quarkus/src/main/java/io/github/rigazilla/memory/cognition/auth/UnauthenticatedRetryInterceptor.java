package io.github.rigazilla.memory.cognition.auth;

import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code oidc} mode channel interceptor: attaches {@link BearerCallCredentials} to every call and
 * replays a call once when memory-service rejects its token with {@code UNAUTHENTICATED}.
 *
 * <p>On every {@code UNAUTHENTICATED} close the token the call used is invalidated (a no-op if it
 * is no longer the cached one). The call is replayed with fresh credentials only if nothing of the
 * response (headers or message) has reached the application yet; otherwise the status is
 * forwarded, and for the event stream the existing reconnect resumes from the saved cursor with a
 * fresh token. A second rejection is propagated. The request message(s), {@code request(n)}
 * demand and half-close are buffered until the response starts so they can be replayed; every
 * call in this application is unary or server-streaming, so the buffer holds one message.
 */
public final class UnauthenticatedRetryInterceptor implements ClientInterceptor {

    private final OidcTokenProvider provider;

    public UnauthenticatedRetryInterceptor(OidcTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ReplayableCall<>(method, callOptions, next);
    }

    private final class ReplayableCall<ReqT, RespT> extends ClientCall<ReqT, RespT> {

        private final MethodDescriptor<ReqT, RespT> method;
        private final CallOptions callOptions;
        private final Channel next;

        private final Object lock = new Object();
        private final List<ReqT> sentMessages = new ArrayList<>();
        private ClientCall<ReqT, RespT> delegate;
        private BearerCallCredentials credentials;
        private Listener<RespT> applicationListener;
        private Metadata originalHeaders;
        private long requested;
        private boolean halfClosed;
        private boolean cancelled;
        private boolean responseStarted;
        private boolean replayed;

        ReplayableCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
            this.method = method;
            this.callOptions = callOptions;
            this.next = next;
            this.credentials = new BearerCallCredentials(provider);
            this.delegate = next.newCall(method, callOptions.withCallCredentials(credentials));
        }

        @Override
        public void start(Listener<RespT> listener, Metadata headers) {
            ClientCall<ReqT, RespT> first;
            synchronized (lock) {
                applicationListener = listener;
                originalHeaders = new Metadata();
                originalHeaders.merge(headers);
                first = delegate;
            }
            first.start(new AttemptListener(), headers);
        }

        @Override
        public void request(int numMessages) {
            ClientCall<ReqT, RespT> current;
            synchronized (lock) {
                requested += numMessages;
                current = delegate;
            }
            current.request(numMessages);
        }

        @Override
        public void sendMessage(ReqT message) {
            ClientCall<ReqT, RespT> current;
            synchronized (lock) {
                if (!responseStarted) {
                    sentMessages.add(message);
                }
                current = delegate;
            }
            current.sendMessage(message);
        }

        @Override
        public void halfClose() {
            ClientCall<ReqT, RespT> current;
            synchronized (lock) {
                halfClosed = true;
                current = delegate;
            }
            current.halfClose();
        }

        @Override
        public void cancel(String message, Throwable cause) {
            ClientCall<ReqT, RespT> current;
            synchronized (lock) {
                cancelled = true;
                current = delegate;
            }
            current.cancel(message, cause);
        }

        @Override
        public boolean isReady() {
            synchronized (lock) {
                return delegate.isReady();
            }
        }

        @Override
        public void setMessageCompression(boolean enabled) {
            synchronized (lock) {
                delegate.setMessageCompression(enabled);
            }
        }

        @Override
        public Attributes getAttributes() {
            synchronized (lock) {
                return delegate.getAttributes();
            }
        }

        /**
         * Replays the buffered call on a new delegate with fresh credentials.
         *
         * @return {@code false} if the application cancelled the call in the meantime
         */
        private boolean replay() {
            synchronized (lock) {
                if (cancelled) {
                    return false;
                }
                credentials = new BearerCallCredentials(provider);
                delegate = next.newCall(method, callOptions.withCallCredentials(credentials));
                Metadata headers = new Metadata();
                headers.merge(originalHeaders);
                delegate.start(new AttemptListener(), headers);
                if (requested > 0) {
                    delegate.request((int) Math.min(requested, Integer.MAX_VALUE));
                }
                for (ReqT message : sentMessages) {
                    delegate.sendMessage(message);
                }
                if (halfClosed) {
                    delegate.halfClose();
                }
                return true;
            }
        }

        private final class AttemptListener extends Listener<RespT> {

            @Override
            public void onHeaders(Metadata headers) {
                markResponseStarted();
                applicationListener.onHeaders(headers);
            }

            @Override
            public void onMessage(RespT message) {
                markResponseStarted();
                applicationListener.onMessage(message);
            }

            @Override
            public void onReady() {
                applicationListener.onReady();
            }

            @Override
            public void onClose(Status status, Metadata trailers) {
                if (status.getCode() != Status.Code.UNAUTHENTICATED) {
                    applicationListener.onClose(status, trailers);
                    return;
                }
                boolean replayNow;
                String rejectedToken;
                synchronized (lock) {
                    rejectedToken = credentials.usedToken();
                    replayNow = !responseStarted && !replayed && !cancelled;
                    if (replayNow) {
                        replayed = true;
                    }
                }
                provider.invalidate(rejectedToken);
                if (!replayNow) {
                    applicationListener.onClose(status, trailers);
                    return;
                }
                if (!replay()) {
                    applicationListener.onClose(Status.CANCELLED.withDescription("Call cancelled"), new Metadata());
                }
            }

            private void markResponseStarted() {
                synchronized (lock) {
                    responseStarted = true;
                    sentMessages.clear();
                }
            }
        }
    }
}
