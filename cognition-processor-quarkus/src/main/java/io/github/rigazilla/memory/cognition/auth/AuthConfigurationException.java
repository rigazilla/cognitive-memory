package io.github.rigazilla.memory.cognition.auth;

/**
 * Invalid or incomplete authentication configuration. Thrown at startup; the message names the
 * offending property and never contains a secret.
 */
public class AuthConfigurationException extends IllegalStateException {

    private static final long serialVersionUID = 1L;

    public AuthConfigurationException(String message) {
        super(message);
    }
}
