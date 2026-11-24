package io.github.rigazilla.memory.cognition.grpc;

import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/** Creates consistently configured, authenticated channels for memory-service. */
public final class GrpcChannelFactory {

    private GrpcChannelFactory() {
    }

    /**
     * Creates a plaintext channel whose calls are authenticated by the given interceptor
     * (see {@code MemoryServiceAuth#channelInterceptor()}).
     */
    public static ManagedChannel create(String host, int port, ClientInterceptor auth) {
        return ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .intercept(auth)
                .build();
    }
}
