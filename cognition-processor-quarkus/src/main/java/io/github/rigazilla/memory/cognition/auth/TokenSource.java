package io.github.rigazilla.memory.cognition.auth;

import java.time.Instant;

/** Fetches one access token with the client-credentials grant. No caching, no retry. */
public interface TokenSource {

    /**
     * Performs one token request.
     *
     * @throws TokenFetchException on any failure, including a response without an access token or
     *                             without a positive {@code expires_in}
     */
    Token fetch();

    /** An access token and its expiry. */
    record Token(String accessToken, Instant expiresAt) {
    }
}
