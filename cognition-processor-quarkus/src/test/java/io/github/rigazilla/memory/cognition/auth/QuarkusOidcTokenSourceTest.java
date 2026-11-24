package io.github.rigazilla.memory.cognition.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.OidcClientException;
import io.quarkus.oidc.client.Tokens;
import io.quarkus.oidc.client.runtime.OidcClientConfig;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuarkusOidcTokenSourceTest {

    private static final Instant NOW = Instant.parse("2030-01-01T00:00:00Z");
    private static final String SECRET = "super-secret-value";

    private final MutableClock clock = new MutableClock(NOW);
    private OidcClient client;
    private QuarkusOidcTokenSource source;

    @BeforeEach
    void setUp() {
        client = mock(OidcClient.class);
        source = new QuarkusOidcTokenSource(client, clock, Duration.ofMillis(200));
    }

    private static Tokens tokens(String accessToken, Long expiresAtEpochSeconds) {
        return new Tokens(accessToken, expiresAtEpochSeconds, Duration.ofSeconds(5), null, null,
                new JsonObject(), "cognition_processor");
    }

    private void respondWith(Tokens tokens) {
        when(client.getTokens()).thenReturn(Uni.createFrom().item(tokens));
    }

    private void failWith(Throwable failure) {
        when(client.getTokens()).thenReturn(Uni.createFrom().failure(failure));
    }

    @Test
    void fetchReturnsTheTokenAndItsExpiry() {
        long expiresAt = NOW.plusSeconds(300).getEpochSecond();
        respondWith(tokens("abc", expiresAt));

        TokenSource.Token token = source.fetch();

        assertThat(token.accessToken()).isEqualTo("abc");
        assertThat(token.expiresAt()).isEqualTo(Instant.ofEpochSecond(expiresAt));
    }

    @Test
    void fetchRejectsAMissingExpiry() {
        respondWith(tokens("abc", null));

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("expires_in");
    }

    @Test
    void fetchRejectsAnAlreadyExpiredToken() {
        respondWith(tokens("abc", NOW.getEpochSecond()));

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("non-positive");
    }

    @Test
    void fetchRejectsABlankToken() {
        respondWith(tokens("  ", NOW.plusSeconds(60).getEpochSecond()));

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("no access token");
    }

    @Test
    void fetchRejectsANullTokensObject() {
        respondWith(null);

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("no access token");
    }

    @Test
    void providerRejectionNeverLeaksTheResponseBody() {
        failWith(new OidcClientException("{\"error\":\"invalid_client\",\"secret\":\"" + SECRET + "\"}"));

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("rejected")
                .hasMessageNotContaining(SECRET)
                .hasMessageNotContaining("invalid_client")
                .hasNoCause();
    }

    @Test
    void unreachableProviderIsReportedAsSuch() {
        failWith(new OidcClientException("OIDC Server is not available"));

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("not reachable");
    }

    @Test
    void unexpectedExceptionsReportOnlyTheirClassName() {
        failWith(new IllegalStateException("password=" + SECRET));

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("IllegalStateException")
                .hasMessageNotContaining(SECRET);
    }

    @Test
    void aTimeoutFromTheClientIsCategorised() {
        failWith(new TimeoutException("slow " + SECRET));

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("timed out")
                .hasMessageNotContaining(SECRET);
    }

    @Test
    void aSlowClientHitsTheLocalTimeout() {
        when(client.getTokens()).thenReturn(Uni.createFrom().nothing());

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("timed out");
    }

    @Test
    void closeClosesTheClient() throws Exception {
        source.close();

        verify(client).close();
    }

    @Test
    void closeSwallowsFailuresOfTheClient() throws Exception {
        doThrow(new IOException("boom")).when(client).close();

        source.close();

        verify(client).close();
    }

    @Test
    void clientConfigForTokenUrlMode() {
        StubMemoryServiceConfig config = new StubMemoryServiceConfig();
        config.clientSecret = Optional.of(SECRET);
        config.tokenUrl = Optional.of(" https://idp.example/token ");
        config.scope = Optional.of("a, b ,,c");
        config.audience = Optional.of(" memory-service ");
        config.tlsConfigurationName = Optional.of("idp-tls");

        OidcClientConfig built = QuarkusOidcTokenSource.buildClientConfig(config);

        assertThat(built.clientId()).contains("cognition_processor");
        assertThat(built.credentials().secret()).contains(SECRET);
        assertThat(built.discoveryEnabled()).contains(false);
        assertThat(built.tokenPath()).contains("https://idp.example/token");
        assertThat(built.authServerUrl()).isEmpty();
        assertThat(built.scopes()).contains(List.of("a", "b", "c"));
        assertThat(built.audience()).contains(List.of("memory-service"));
        assertThat(built.tls().tlsConfigurationName()).contains("idp-tls");
        assertThat(built.followRedirects()).isFalse();
    }

    @Test
    void clientConfigForDiscoveryMode() {
        StubMemoryServiceConfig config = new StubMemoryServiceConfig();
        config.clientSecret = Optional.of(SECRET);
        config.discoveryUrl = Optional.of("https://idp.example/realms/r");

        OidcClientConfig built = QuarkusOidcTokenSource.buildClientConfig(config);

        assertThat(built.discoveryEnabled()).contains(true);
        assertThat(built.authServerUrl()).contains("https://idp.example/realms/r");
        assertThat(built.tokenPath()).isEmpty();
        assertThat(built.scopes()).isEmpty();
        assertThat(built.audience()).isEmpty();
        assertThat(built.tls().tlsConfigurationName()).isEmpty();
    }

    @Test
    void blankOptionalSettingsAreIgnored() {
        StubMemoryServiceConfig config = new StubMemoryServiceConfig();
        config.clientSecret = Optional.of(SECRET);
        config.tokenUrl = Optional.of("https://idp.example/token");
        config.scope = Optional.of("   ");
        config.audience = Optional.of("");
        config.tlsConfigurationName = Optional.of(" ");

        OidcClientConfig built = QuarkusOidcTokenSource.buildClientConfig(config);

        assertThat(built.scopes()).isEmpty();
        assertThat(built.audience()).isEmpty();
        assertThat(built.tls().tlsConfigurationName()).isEmpty();
    }
}
