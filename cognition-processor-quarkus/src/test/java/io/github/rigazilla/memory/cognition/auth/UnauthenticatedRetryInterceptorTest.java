package io.github.rigazilla.memory.cognition.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Server;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.grpc.ServerServiceDefinition;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.ClientCalls;
import io.grpc.stub.ServerCalls;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The interceptor against a real (Netty, localhost) gRPC server that validates the bearer token,
 * so it also proves that per-call credentials work on a plaintext channel.
 */
class UnauthenticatedRetryInterceptorTest {

    private static final MethodDescriptor.Marshaller<String> STRINGS = new MethodDescriptor.Marshaller<>() {
        @Override
        public InputStream stream(String value) {
            return new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public String parse(InputStream stream) {
            try {
                return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            } catch (java.io.IOException e) {
                throw new IllegalStateException(e);
            }
        }
    };

    private static MethodDescriptor<String, String> method(String name, MethodDescriptor.MethodType type) {
        return MethodDescriptor.<String, String>newBuilder()
                .setType(type)
                .setFullMethodName("test.Service/" + name)
                .setRequestMarshaller(STRINGS)
                .setResponseMarshaller(STRINGS)
                .build();
    }

    private static final MethodDescriptor<String, String> UNARY = method("Unary", MethodDescriptor.MethodType.UNARY);
    private static final MethodDescriptor<String, String> STREAM =
            method("Stream", MethodDescriptor.MethodType.SERVER_STREAMING);
    private static final MethodDescriptor<String, String> STREAM_THEN_UNAUTH =
            method("StreamThenUnauth", MethodDescriptor.MethodType.SERVER_STREAMING);
    private static final MethodDescriptor<String, String> HANG =
            method("Hang", MethodDescriptor.MethodType.SERVER_STREAMING);

    private static final Metadata.Key<String> AUTHORIZATION =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    /** Delays up to one second run at once so replay tests do not wait for the invalidate spacing. */
    private static final class FastScheduler implements Scheduler {
        private final ExecutorScheduler delegate = new ExecutorScheduler("test-oidc-renewal");

        @Override
        public void schedule(Runnable task, Duration delay) {
            delegate.schedule(task, delay.compareTo(Duration.ofSeconds(1)) <= 0 ? Duration.ZERO : delay);
        }

        @Override
        public void close() {
            delegate.close();
        }
    }

    private final Set<String> validTokens = new CopyOnWriteArraySet<>();
    private final List<String> authorizationSeen = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger serverAttempts = new AtomicInteger();

    private Server server;
    private ManagedChannel channel;
    private OidcTokenProvider provider;
    private FakeTokenSource source;
    private MutableClock clock;

    @BeforeEach
    void setUp() throws Exception {
        ServerServiceDefinition service = ServerServiceDefinition.builder("test.Service")
                .addMethod(UNARY, ServerCalls.asyncUnaryCall((req, obs) -> {
                    obs.onNext("echo:" + req);
                    obs.onCompleted();
                }))
                .addMethod(STREAM, ServerCalls.asyncServerStreamingCall((req, obs) -> {
                    obs.onNext("a");
                    obs.onNext("b");
                    obs.onCompleted();
                }))
                .addMethod(STREAM_THEN_UNAUTH, ServerCalls.asyncServerStreamingCall((req, obs) -> {
                    obs.onNext("a");
                    obs.onError(Status.UNAUTHENTICATED.withDescription("expired").asRuntimeException());
                }))
                .addMethod(HANG, ServerCalls.asyncServerStreamingCall((req, obs) -> { }))
                .build();
        server = NettyServerBuilder.forPort(0)
                .addService(ServerInterceptors.intercept(service, new BearerCheck()))
                .build().start();
        clock = new MutableClock(Instant.now());
        source = new FakeTokenSource(clock);
    }

    private final class BearerCheck implements ServerInterceptor {
        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
                ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
            serverAttempts.incrementAndGet();
            String authorization = headers.get(AUTHORIZATION);
            authorizationSeen.add(authorization);
            if (authorization != null && authorization.startsWith("Bearer ")
                    && validTokens.contains(authorization.substring("Bearer ".length()))) {
                return next.startCall(call, headers);
            }
            call.close(Status.UNAUTHENTICATED.withDescription("invalid token"), new Metadata());
            return new ServerCall.Listener<>() { };
        }
    }

    private void connect() {
        provider = new OidcTokenProvider(source, clock, new FastScheduler());
        provider.start();
        channel = NettyChannelBuilder.forAddress("localhost", server.getPort())
                .usePlaintext()
                .intercept(new UnauthenticatedRetryInterceptor(provider))
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (channel != null) {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
        if (provider != null) {
            provider.close();
        }
        server.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    private String unary() {
        return ClientCalls.blockingUnaryCall(channel, UNARY, CallOptions.DEFAULT.withDeadlineAfter(10, TimeUnit.SECONDS),
                "hi");
    }

    private static final Duration HOUR = Duration.ofHours(1);

    @Test
    void sendsTheCachedBearerTokenOnAPlaintextChannel() {
        source.thenToken(HOUR);
        validTokens.add("token-1");
        connect();

        assertThat(unary()).isEqualTo("echo:hi");
        assertThat(authorizationSeen).containsExactly("Bearer token-1");
        assertThat(source.fetches()).isEqualTo(1);
    }

    @Test
    void replaysAUnaryCallOnceWithAFreshTokenAfterUnauthenticated() {
        source.thenToken(HOUR).thenToken(HOUR);
        validTokens.add("token-2");
        connect();

        assertThat(unary()).isEqualTo("echo:hi");
        assertThat(authorizationSeen).containsExactly("Bearer token-1", "Bearer token-2");
        assertThat(source.fetches()).isEqualTo(2);
    }

    @Test
    void aSecondRejectionIsPropagated() {
        source.thenToken(HOUR).thenToken(HOUR).thenToken(HOUR);
        connect();

        assertThatThrownBy(this::unary)
                .isInstanceOfSatisfying(StatusRuntimeException.class,
                        e -> assertThat(e.getStatus().getCode()).isEqualTo(Status.Code.UNAUTHENTICATED));
        assertThat(serverAttempts.get()).isEqualTo(2);
    }

    @Test
    void replaysAServerStreamThatWasRejectedBeforeItsFirstMessage() {
        source.thenToken(HOUR).thenToken(HOUR);
        validTokens.add("token-2");
        connect();

        Iterator<String> messages = ClientCalls.blockingServerStreamingCall(
                channel, STREAM, CallOptions.DEFAULT.withDeadlineAfter(10, TimeUnit.SECONDS), "go");
        List<String> received = new ArrayList<>();
        messages.forEachRemaining(received::add);

        assertThat(received).containsExactly("a", "b");
        assertThat(authorizationSeen).containsExactly("Bearer token-1", "Bearer token-2");
    }

    @Test
    void doesNotReplayAStreamRejectedAfterItsFirstMessageButInvalidatesTheToken() throws Exception {
        source.thenToken(HOUR).thenToken(HOUR);
        validTokens.add("token-1");
        connect();

        Iterator<String> messages = ClientCalls.blockingServerStreamingCall(
                channel, STREAM_THEN_UNAUTH, CallOptions.DEFAULT.withDeadlineAfter(10, TimeUnit.SECONDS), "go");
        assertThat(messages.next()).isEqualTo("a");
        assertThatThrownBy(messages::next)
                .isInstanceOfSatisfying(StatusRuntimeException.class,
                        e -> assertThat(e.getStatus().getCode()).isEqualTo(Status.Code.UNAUTHENTICATED));

        assertThat(serverAttempts.get()).isEqualTo(1);
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
        while (source.fetches() < 2 && System.nanoTime() < deadline) {
            Thread.sleep(20);
        }
        assertThat(source.fetches()).as("the rejected token triggered a refresh").isEqualTo(2);
    }

    @Test
    void concurrentRejectionsOfTheSameTokenCauseOneRefresh() throws Exception {
        source.thenToken(HOUR).thenToken(HOUR).thenToken(HOUR);
        validTokens.add("token-2");
        connect();

        ExecutorService pool = Executors.newFixedThreadPool(10);
        try {
            CountDownLatch go = new CountDownLatch(1);
            List<Future<String>> results = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                results.add(pool.submit(() -> {
                    go.await();
                    return unary();
                }));
            }
            go.countDown();
            for (Future<String> result : results) {
                assertThat(result.get(20, TimeUnit.SECONDS)).isEqualTo("echo:hi");
            }
        } finally {
            pool.shutdownNow();
        }
        assertThat(source.fetches()).isEqualTo(2);
    }

    @Test
    void failsWithUnavailableWhenNoTokenCanBeObtained() {
        source.thenToken(Duration.ofSeconds(100));
        validTokens.add("token-1");
        connect();
        clock.advance(Duration.ofSeconds(101));
        provider.close();

        assertThatThrownBy(this::unary)
                .isInstanceOfSatisfying(StatusRuntimeException.class, e -> {
                    assertThat(e.getStatus().getCode()).isEqualTo(Status.Code.UNAVAILABLE);
                    assertThat(e.getStatus().getDescription()).contains("closed");
                });
        assertThat(serverAttempts.get()).isZero();
    }

    @Test
    void cancelIsForwardedToTheCurrentCall() throws Exception {
        source.thenToken(HOUR);
        validTokens.add("token-1");
        connect();

        CountDownLatch closed = new CountDownLatch(1);
        AtomicReference<Status> status = new AtomicReference<>();
        ClientCall<String, String> call = channel.newCall(HANG, CallOptions.DEFAULT);
        call.start(new ClientCall.Listener<>() {
            @Override
            public void onClose(Status s, Metadata trailers) {
                status.set(s);
                closed.countDown();
            }
        }, new Metadata());
        call.sendMessage("go");
        call.halfClose();
        call.request(1);
        call.cancel("test", null);

        assertThat(closed.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(status.get().getCode()).isEqualTo(Status.Code.CANCELLED);
    }
}
