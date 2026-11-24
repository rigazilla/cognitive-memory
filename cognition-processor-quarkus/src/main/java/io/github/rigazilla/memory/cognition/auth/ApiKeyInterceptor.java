package io.github.rigazilla.memory.cognition.auth;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

/** {@code api-key} mode: sends {@code x-api-key} and, when set, {@code x-client-id}. */
public final class ApiKeyInterceptor implements ClientInterceptor {

    static final Metadata.Key<String> API_KEY_HEADER =
            Metadata.Key.of("x-api-key", Metadata.ASCII_STRING_MARSHALLER);
    static final Metadata.Key<String> CLIENT_ID_HEADER =
            Metadata.Key.of("x-client-id", Metadata.ASCII_STRING_MARSHALLER);

    private final String apiKey;
    private final String clientId;

    public ApiKeyInterceptor(String apiKey, String clientId) {
        this.apiKey = apiKey;
        this.clientId = clientId;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
                next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(API_KEY_HEADER, apiKey);
                if (clientId != null && !clientId.isBlank()) {
                    headers.put(CLIENT_ID_HEADER, clientId);
                }
                super.start(responseListener, headers);
            }
        };
    }
}
