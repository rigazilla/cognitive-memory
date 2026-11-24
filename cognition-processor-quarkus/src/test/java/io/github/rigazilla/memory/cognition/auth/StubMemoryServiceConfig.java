package io.github.rigazilla.memory.cognition.auth;

import io.github.rigazilla.memory.cognition.config.MemoryServiceConfig;
import java.util.Optional;

/** Mutable {@link MemoryServiceConfig} for unit tests (all values unset by default). */
final class StubMemoryServiceConfig implements MemoryServiceConfig {

    String clientId = "cognition_processor";
    Optional<String> apiKey = Optional.empty();
    Auth.Method method = Auth.Method.AUTO;
    Optional<String> clientSecret = Optional.empty();
    Optional<String> discoveryUrl = Optional.empty();
    Optional<String> tokenUrl = Optional.empty();
    Optional<String> audience = Optional.empty();
    Optional<String> scope = Optional.empty();
    Optional<Boolean> allowInsecure = Optional.empty();
    Optional<String> tlsConfigurationName = Optional.empty();

    @Override
    public Grpc grpc() {
        return new Grpc() {
            @Override
            public String host() {
                return "localhost";
            }

            @Override
            public int port() {
                return 8082;
            }
        };
    }

    @Override
    public Auth auth() {
        return new Auth() {
            @Override
            public Method method() {
                return method;
            }

            @Override
            public Oidc oidc() {
                return new Oidc() {
                    @Override
                    public Optional<String> clientSecret() {
                        return clientSecret;
                    }

                    @Override
                    public Optional<String> discoveryUrl() {
                        return discoveryUrl;
                    }

                    @Override
                    public Optional<String> tokenUrl() {
                        return tokenUrl;
                    }

                    @Override
                    public Optional<String> audience() {
                        return audience;
                    }

                    @Override
                    public Optional<String> scope() {
                        return scope;
                    }

                    @Override
                    public Optional<Boolean> allowInsecure() {
                        return allowInsecure;
                    }

                    @Override
                    public Optional<String> tlsConfigurationName() {
                        return tlsConfigurationName;
                    }
                };
            }
        };
    }

    @Override
    public Optional<String> apiKey() {
        return apiKey;
    }

    @Override
    public String clientId() {
        return clientId;
    }
}
