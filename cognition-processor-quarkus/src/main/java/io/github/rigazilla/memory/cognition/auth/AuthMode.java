package io.github.rigazilla.memory.cognition.auth;

/** How the cognition processor authenticates to memory-service. */
public enum AuthMode {
    /** Fixed API key ({@code x-api-key} header). */
    API_KEY,
    /** OIDC client-credentials bearer token ({@code authorization} header). */
    OIDC
}
