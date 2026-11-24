package io.github.rigazilla.memory.cognition.auth;

/**
 * A failed token request. The message is a fixed category (never the provider's response body, the
 * client secret or the original exception text) and there is deliberately no cause, because the
 * underlying exceptions of the OIDC client can embed the response body.
 */
public class TokenFetchException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenFetchException(String message) {
        super(message);
    }
}
