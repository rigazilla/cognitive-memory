package io.github.rigazilla.memory.cognition.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.rigazilla.memory.cognition.config.MemoryServiceConfig.Auth.Method;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AuthModeResolverTest {

    private static final String WORKER = "cognition_processor";
    private static final String SECRET = "s3cr3t-value";

    private static StubMemoryServiceConfig config(Consumer<StubMemoryServiceConfig> setup) {
        StubMemoryServiceConfig c = new StubMemoryServiceConfig();
        setup.accept(c);
        return c;
    }

    private static Consumer<StubMemoryServiceConfig> completeOidc() {
        return c -> {
            c.clientSecret = Optional.of(SECRET);
            c.tokenUrl = Optional.of("https://idp.example.com/token");
        };
    }

    // ---- mode resolution table ------------------------------------------------------------

    static Stream<Arguments> successCases() {
        Consumer<StubMemoryServiceConfig> key = c -> c.apiKey = Optional.of("k");
        Consumer<StubMemoryServiceConfig> keyAndOidc = key.andThen(completeOidc());
        return Stream.of(
                Arguments.of("auto, api key only", Method.AUTO, key, AuthMode.API_KEY),
                Arguments.of("auto, complete oidc", Method.AUTO, completeOidc(), AuthMode.OIDC),
                Arguments.of("auto, complete oidc and api key (key ignored)", Method.AUTO, keyAndOidc,
                        AuthMode.OIDC),
                Arguments.of("auto, discovery url", Method.AUTO,
                        (Consumer<StubMemoryServiceConfig>) c -> {
                            c.clientSecret = Optional.of(SECRET);
                            c.discoveryUrl = Optional.of("https://idp.example.com/realms/x");
                        }, AuthMode.OIDC),
                Arguments.of("api-key, api key only", Method.API_KEY, key, AuthMode.API_KEY),
                Arguments.of("api-key, oidc settings ignored", Method.API_KEY, keyAndOidc, AuthMode.API_KEY),
                Arguments.of("api-key, partial oidc ignored", Method.API_KEY,
                        key.andThen(c -> c.audience = Optional.of("a")), AuthMode.API_KEY),
                Arguments.of("oidc, complete", Method.OIDC, completeOidc(), AuthMode.OIDC),
                Arguments.of("oidc, complete and api key", Method.OIDC, keyAndOidc, AuthMode.OIDC),
                Arguments.of("oidc, loopback http allowed", Method.OIDC,
                        (Consumer<StubMemoryServiceConfig>) c -> {
                            c.clientSecret = Optional.of(SECRET);
                            c.tokenUrl = Optional.of("http://localhost:8180/token");
                        }, AuthMode.OIDC),
                Arguments.of("oidc, non-loopback http with allow-insecure", Method.OIDC,
                        (Consumer<StubMemoryServiceConfig>) c -> {
                            c.clientSecret = Optional.of(SECRET);
                            c.tokenUrl = Optional.of("http://keycloak:8080/token");
                            c.allowInsecure = Optional.of(true);
                        }, AuthMode.OIDC));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("successCases")
    void resolvesMode(String name, Method method, Consumer<StubMemoryServiceConfig> setup, AuthMode expected) {
        StubMemoryServiceConfig c = config(setup);
        c.method = method;
        assertThat(AuthModeResolver.resolve(c, WORKER)).isEqualTo(expected);
    }

    static Stream<Arguments> failureCases() {
        Consumer<StubMemoryServiceConfig> key = c -> c.apiKey = Optional.of("k");
        return Stream.of(
                Arguments.of("auto, nothing configured", Method.AUTO,
                        (Consumer<StubMemoryServiceConfig>) c -> { }, "No authentication configured"),
                Arguments.of("auto, blank api key", Method.AUTO,
                        (Consumer<StubMemoryServiceConfig>) c -> c.apiKey = Optional.of("  "),
                        "No authentication configured"),
                Arguments.of("api-key, no key", Method.API_KEY,
                        (Consumer<StubMemoryServiceConfig>) c -> { }, "No authentication configured"),
                Arguments.of("api-key, no key but complete oidc", Method.API_KEY, completeOidc(),
                        "No authentication configured"),
                Arguments.of("oidc, nothing set", Method.OIDC,
                        (Consumer<StubMemoryServiceConfig>) c -> { },
                        "memory-service.auth.oidc.client-secret is required"),
                Arguments.of("oidc, only api key", Method.OIDC, key,
                        "memory-service.auth.oidc.client-secret is required"),
                Arguments.of("oidc, secret but no url", Method.OIDC,
                        (Consumer<StubMemoryServiceConfig>) c -> c.clientSecret = Optional.of(SECRET),
                        "Exactly one of"),
                Arguments.of("oidc, url but no secret", Method.OIDC,
                        (Consumer<StubMemoryServiceConfig>) c ->
                                c.tokenUrl = Optional.of("https://idp.example.com/token"),
                        "client-secret is required"),
                Arguments.of("oidc, both urls", Method.OIDC,
                        completeOidc().andThen(c ->
                                c.discoveryUrl = Optional.of("https://idp.example.com/realms/x")),
                        "Exactly one of"),
                Arguments.of("oidc, non-loopback http", Method.OIDC,
                        (Consumer<StubMemoryServiceConfig>) c -> {
                            c.clientSecret = Optional.of(SECRET);
                            c.tokenUrl = Optional.of("http://keycloak:8080/token");
                        }, "plaintext http"),
                Arguments.of("oidc, discovery url with user info", Method.OIDC,
                        (Consumer<StubMemoryServiceConfig>) c -> {
                            c.clientSecret = Optional.of(SECRET);
                            c.discoveryUrl = Optional.of("https://user:pw@idp.example.com/realms/x");
                        }, "memory-service.auth.oidc.discovery-url must be an absolute"),
                // auto: any single oidc.* value switches to oidc and fails full validation
                Arguments.of("auto, audience alone", Method.AUTO,
                        key.andThen(c -> c.audience = Optional.of("a")), "client-secret is required"),
                Arguments.of("auto, scope alone", Method.AUTO,
                        key.andThen(c -> c.scope = Optional.of("openid")), "client-secret is required"),
                Arguments.of("auto, allow-insecure alone", Method.AUTO,
                        key.andThen(c -> c.allowInsecure = Optional.of(false)), "client-secret is required"),
                Arguments.of("auto, tls name alone", Method.AUTO,
                        key.andThen(c -> c.tlsConfigurationName = Optional.of("idp")),
                        "client-secret is required"),
                Arguments.of("auto, secret alone", Method.AUTO,
                        key.andThen(c -> c.clientSecret = Optional.of(SECRET)), "Exactly one of"),
                Arguments.of("auto, url alone", Method.AUTO,
                        key.andThen(c -> c.tokenUrl = Optional.of("https://idp.example.com/token")),
                        "client-secret is required"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("failureCases")
    void rejectsInvalidConfiguration(String name, Method method, Consumer<StubMemoryServiceConfig> setup,
            String messagePart) {
        StubMemoryServiceConfig c = config(setup);
        c.method = method;
        assertThatThrownBy(() -> AuthModeResolver.resolve(c, WORKER))
                .isInstanceOf(AuthConfigurationException.class)
                .hasMessageContaining(messagePart)
                .satisfies(e -> assertThat(e.getMessage()).doesNotContain(SECRET).doesNotContain("pw@"));
    }

    @Test
    void blankOidcValuesCountAsUnset() {
        StubMemoryServiceConfig c = config(x -> {
            x.apiKey = Optional.of("k");
            x.clientSecret = Optional.of("");
            x.audience = Optional.of("   ");
        });
        assertThat(AuthModeResolver.resolve(c, WORKER)).isEqualTo(AuthMode.API_KEY);
    }

    @Test
    void inferredModeIsNamedInTheError() {
        StubMemoryServiceConfig c = config(x -> x.audience = Optional.of("a"));
        assertThatThrownBy(() -> AuthModeResolver.resolve(c, WORKER))
                .hasMessageContaining("inferred from memory-service.auth.oidc.audience");
    }

    // ---- worker id == client id, in both modes --------------------------------------------

    @ParameterizedTest
    @MethodSource("bothModes")
    void workerIdMustEqualClientIdInBothModes(Consumer<StubMemoryServiceConfig> setup) {
        StubMemoryServiceConfig c = config(setup);
        assertThatThrownBy(() -> AuthModeResolver.resolve(c, "other-worker"))
                .isInstanceOf(AuthConfigurationException.class)
                .hasMessageContaining("cognition.worker.id ('other-worker') must equal "
                        + "memory-service.client-id ('cognition_processor')");
    }

    static Stream<Consumer<StubMemoryServiceConfig>> bothModes() {
        return Stream.of(
                (Consumer<StubMemoryServiceConfig>) c -> c.apiKey = Optional.of("k"),
                completeOidc());
    }
}
