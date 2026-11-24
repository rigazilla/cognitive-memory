package io.github.rigazilla.memory.cognition.auth;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import java.util.concurrent.Executor;

/**
 * Per-call credentials that apply {@code authorization: Bearer <token>} and remember which token
 * the call used, so a rejection can invalidate exactly that token. If the token is expired and a
 * renewal is in flight the call waits without blocking a thread.
 */
final class BearerCallCredentials extends CallCredentials {

    static final Metadata.Key<String> AUTHORIZATION_HEADER =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    private final OidcTokenProvider provider;
    private volatile String usedToken;

    BearerCallCredentials(OidcTokenProvider provider) {
        this.provider = provider;
    }

    /** The token this call was sent with, or {@code null} if none was applied. */
    String usedToken() {
        return usedToken;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        provider.token().whenCompleteAsync((token, error) -> {
            if (error != null) {
                applier.fail(Status.UNAVAILABLE.withDescription(error.getMessage()));
                return;
            }
            usedToken = token;
            Metadata headers = new Metadata();
            headers.put(AUTHORIZATION_HEADER, "Bearer " + token);
            applier.apply(headers);
        }, appExecutor);
    }

    @Override
    public void thisUsesUnstableApi() {
        // Required by the CallCredentials contract.
    }
}
