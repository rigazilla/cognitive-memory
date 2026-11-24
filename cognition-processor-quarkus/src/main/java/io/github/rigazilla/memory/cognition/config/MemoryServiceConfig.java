package io.github.rigazilla.memory.cognition.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;
import java.util.Optional;

/**
 * Typed configuration for the memory-service connection.
 * <p>
 * Replaces scattered {@code @ConfigProperty(name = "memory-service.*")} fields
 * with a single injectable interface.  Prefer injecting {@code MemoryServiceConfig}
 * over individual {@code @ConfigProperty} fields in all new code.
 *
 * <p>Corresponding {@code application.properties} keys:
 * <ul>
 *   <li>{@code memory-service.grpc.host}</li>
 *   <li>{@code memory-service.grpc.port}</li>
 *   <li>{@code memory-service.api-key}</li>
 *   <li>{@code memory-service.client-id}</li>
 *   <li>{@code memory-service.auth.method}</li>
 *   <li>{@code memory-service.auth.oidc.*}</li>
 * </ul>
 */
@ConfigMapping(prefix = "memory-service")
public interface MemoryServiceConfig {

    /** gRPC transport settings. */
    Grpc grpc();

    /** Authentication settings (method selection and OIDC client credentials). */
    Auth auth();

    /**
     * API key used to authenticate with memory-service in {@code api-key} mode.
     * Optional: not needed when OIDC client credentials are configured.
     */
    @WithName("api-key")
    Optional<String> apiKey();

    /**
     * Client ID of the cognition processor. Sent as the {@code x-client-id} gRPC header and, in
     * {@code oidc} mode, used as the OIDC {@code client_id}. Must be listed in
     * {@code MEMORY_SERVICE_ROLES_ADMIN_CLIENTS} for the admin role and must equal
     * {@code cognition.worker.id}.
     */
    @WithName("client-id")
    @WithDefault("cognition_processor")
    String clientId();

    /** gRPC host/port sub-group. */
    interface Grpc {
        /** Hostname or IP of the memory-service gRPC endpoint. */
        @WithDefault("localhost")
        String host();

        /** Port of the memory-service gRPC endpoint. */
        @WithDefault("8082")
        int port();
    }

    /** Authentication sub-group ({@code memory-service.auth.*}). */
    interface Auth {
        /** Authentication method; {@code auto} infers it from the OIDC settings. */
        @WithDefault("auto")
        Method method();

        /** OIDC client-credentials settings; every value is optional. */
        Oidc oidc();

        /** Authentication method selection. */
        enum Method {
            /** Use OIDC if any OIDC value is set, otherwise the API key. */
            AUTO,
            /** Force the fixed API key; OIDC settings are ignored. */
            API_KEY,
            /** Force OIDC client credentials. */
            OIDC
        }

        /** OIDC client-credentials sub-group ({@code memory-service.auth.oidc.*}). */
        interface Oidc {
            /** Client secret of the confidential OIDC client. */
            @WithName("client-secret")
            Optional<String> clientSecret();

            /** OIDC discovery URL; exclusive with {@code token-url}. */
            @WithName("discovery-url")
            Optional<String> discoveryUrl();

            /** Direct token endpoint URL; exclusive with {@code discovery-url}. */
            @WithName("token-url")
            Optional<String> tokenUrl();

            /** Optional {@code audience} form parameter of the token request. */
            Optional<String> audience();

            /** Optional {@code scope} form parameter, comma separated. */
            Optional<String> scope();

            /** Development opt-in that allows a non-loopback plaintext {@code http://} URL. */
            @WithName("allow-insecure")
            Optional<Boolean> allowInsecure();

            /** Optional name of a {@code quarkus.tls.<name>} entry used for the token requests. */
            @WithName("tls-configuration-name")
            Optional<String> tlsConfigurationName();
        }
    }
}
