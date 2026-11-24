package io.github.rigazilla.memory.cognition.auth;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OidcUrlValidatorTest {

    private static final String PROP = "test.url";

    @ParameterizedTest
    @ValueSource(strings = {
            "https://idp.example.com/token",
            "HTTPS://idp.example.com:8443/realms/x/protocol/openid-connect/token",
            "http://localhost:8180/token",
            "http://LOCALHOST/token",
            "http://127.0.0.1:8180/token",
            "http://127.1.2.3/token",
            "http://[::1]:8180/token"})
    void accepts(String url) {
        assertThatCode(() -> OidcUrlValidator.validate(PROP, url, false)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http://keycloak:8080/token",
            "http://idp.example.com/token",
            "http://10.0.0.5/token",
            "http://localhost.example.com/token"})
    void rejectsPlaintextForNonLoopback(String url) {
        assertThatThrownBy(() -> OidcUrlValidator.validate(PROP, url, false))
                .isInstanceOf(AuthConfigurationException.class)
                .hasMessageContaining(PROP)
                .hasMessageContaining("plaintext http")
                .hasMessageContaining("allow-insecure");
    }

    @Test
    void allowInsecureOpensNonLoopbackHttp() {
        assertThatCode(() -> OidcUrlValidator.validate(PROP, "http://keycloak:8080/token", true))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "   ",
            "/relative/path",
            "idp.example.com/token",
            "ftp://idp.example.com/token",
            "file:///etc/passwd",
            "https:///token",
            "https://user:pw@idp.example.com/token",
            "https://user@idp.example.com/token",
            "https://idp.example.com/token#frag",
            "https://id p.example.com/token",
            "http://under_score/token"})
    void rejectsMalformedUrls(String url) {
        assertThatThrownBy(() -> OidcUrlValidator.validate(PROP, url, true))
                .isInstanceOf(AuthConfigurationException.class)
                .hasMessageContaining(PROP)
                .satisfies(e -> {
                    if (url.contains("pw")) {
                        org.assertj.core.api.Assertions.assertThat(e.getMessage()).doesNotContain("pw");
                    }
                });
    }
}
