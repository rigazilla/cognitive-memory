package io.github.rigazilla.memory.cognition.grpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.MethodDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class GrpcChannelFactoryTest {

    private static final MethodDescriptor.Marshaller<byte[]> BYTE_MARSHALLER =
            new MethodDescriptor.Marshaller<>() {
                @Override
                public InputStream stream(byte[] value) {
                    return new ByteArrayInputStream(value);
                }

                @Override
                public byte[] parse(InputStream stream) {
                    return new byte[0];
                }
            };

    private static final MethodDescriptor<byte[], byte[]> METHOD = MethodDescriptor.<byte[], byte[]>newBuilder()
            .setType(MethodDescriptor.MethodType.UNARY)
            .setFullMethodName("test.Service/Call")
            .setRequestMarshaller(BYTE_MARSHALLER)
            .setResponseMarshaller(BYTE_MARSHALLER)
            .build();

    @Test
    void createsChannelThatRunsTheAuthInterceptor() {
        AtomicInteger intercepted = new AtomicInteger();
        ClientInterceptor auth = new ClientInterceptor() {
            @Override
            public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
                    MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
                intercepted.incrementAndGet();
                return next.newCall(method, callOptions);
            }
        };

        ManagedChannel channel = GrpcChannelFactory.create("localhost", 50051, auth);
        try {
            assertEquals("localhost:50051", channel.authority());
            assertFalse(channel.isShutdown());
            channel.newCall(METHOD, CallOptions.DEFAULT);
            assertEquals(1, intercepted.get());
        } finally {
            channel.shutdownNow();
        }
        assertTrue(channel.isShutdown());
    }
}
