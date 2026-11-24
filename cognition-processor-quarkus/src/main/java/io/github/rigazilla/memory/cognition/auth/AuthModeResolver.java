package io.github.rigazilla.memory.cognition.auth;

import io.github.rigazilla.memory.cognition.config.MemoryServiceConfig;
import io.github.rigazilla.memory.cognition.config.MemoryServiceConfig.Auth.Method;
import io.github.rigazilla.memory.cognition.config.MemoryServiceConfig.Auth.Oidc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jboss.logging.Logger;

/**
 * Resolves and validates the authentication mode from configuration. Pure logic: no I/O, no
 * network access.
 *
 * <p>"OIDC property" means <em>any</em> {@code memory-service.auth.oidc.*} value (blank counts as
 * unset). In {@code auto} mode a single OIDC property selects {@code oidc} and the full OIDC
 * validation applies, so a partial OIDC configuration fails startup instead of silently falling
 * back to the API key.
 */
public final class AuthModeResolver {

    private static final Logger LOG = Logger.getLogger(AuthModeResolver.class);

    private static final String P_METHOD = "memory-service.auth.method";
    private static final String P_API_KEY = "memory-service.api-key";
    private static final String P_SECRET = "memory-service.auth.oidc.client-secret";
    private static final String P_DISCOVERY = "memory-service.auth.oidc.discovery-url";
    private static final String P_TOKEN_URL = "memory-service.auth.oidc.token-url";

    private AuthModeResolver() {
    }

    /**
     * Resolves the mode and validates everything that can be checked without network access.
     *
     * @param config   the memory-service configuration
     * @param workerId {@code cognition.worker.id}; must equal {@code memory-service.client-id}
     * @return the mode to use
     * @throws AuthConfigurationException if the configuration is invalid or incomplete
     */
    public static AuthMode resolve(MemoryServiceConfig config, String workerId) {
        // memory-service rejects a checkpoint client_id that differs from the authenticated client.
        if (!config.clientId().equals(workerId)) {
            throw new AuthConfigurationException("cognition.worker.id ('" + workerId
                    + "') must equal memory-service.client-id ('" + config.clientId() + "')");
        }
        Method method = config.auth().method();
        Oidc oidc = config.auth().oidc();
        List<String> oidcSet = oidcPropertiesSet(oidc);
        boolean apiKeySet = present(config.apiKey());

        switch (method) {
            case API_KEY:
                requireApiKey(apiKeySet, "api-key");
                if (!oidcSet.isEmpty()) {
                    LOG.infof("%s=api-key: ignoring the memory-service.auth.oidc.* settings", P_METHOD);
                }
                return AuthMode.API_KEY;
            case OIDC:
                validateOidc(oidc, "oidc");
                return AuthMode.OIDC;
            case AUTO:
            default:
                if (oidcSet.isEmpty()) {
                    requireApiKey(apiKeySet, "api-key");
                    return AuthMode.API_KEY;
                }
                validateOidc(oidc, "oidc (inferred from " + oidcSet.get(0) + ")");
                if (apiKeySet) {
                    LOG.warnf("%s is ignored because OIDC client credentials are configured; set %s=api-key "
                            + "to use the API key instead", P_API_KEY, P_METHOD);
                }
                return AuthMode.OIDC;
        }
    }

    private static void requireApiKey(boolean apiKeySet, String mode) {
        if (!apiKeySet) {
            throw new AuthConfigurationException("No authentication configured (mode " + mode + "): set "
                    + P_API_KEY + " or the memory-service.auth.oidc.* client credentials");
        }
    }

    private static void validateOidc(Oidc oidc, String mode) {
        if (!present(oidc.clientSecret())) {
            throw new AuthConfigurationException(P_SECRET + " is required (mode " + mode + ")");
        }
        boolean discovery = present(oidc.discoveryUrl());
        boolean tokenUrl = present(oidc.tokenUrl());
        if (discovery == tokenUrl) {
            throw new AuthConfigurationException("Exactly one of " + P_DISCOVERY + " and " + P_TOKEN_URL
                    + " must be set (mode " + mode + ")");
        }
        boolean allowInsecure = oidc.allowInsecure().orElse(false);
        if (discovery) {
            OidcUrlValidator.validate(P_DISCOVERY, oidc.discoveryUrl().get(), allowInsecure);
        } else {
            OidcUrlValidator.validate(P_TOKEN_URL, oidc.tokenUrl().get(), allowInsecure);
        }
    }

    private static List<String> oidcPropertiesSet(Oidc oidc) {
        List<String> set = new ArrayList<>();
        addIfPresent(set, "client-secret", oidc.clientSecret());
        addIfPresent(set, "discovery-url", oidc.discoveryUrl());
        addIfPresent(set, "token-url", oidc.tokenUrl());
        addIfPresent(set, "audience", oidc.audience());
        addIfPresent(set, "scope", oidc.scope());
        addIfPresent(set, "tls-configuration-name", oidc.tlsConfigurationName());
        if (oidc.allowInsecure().isPresent()) {
            set.add("memory-service.auth.oidc.allow-insecure");
        }
        return set;
    }

    private static void addIfPresent(List<String> set, String name, Optional<String> value) {
        if (present(value)) {
            set.add("memory-service.auth.oidc." + name);
        }
    }

    private static boolean present(Optional<String> value) {
        return value.isPresent() && !value.get().isBlank();
    }
}
