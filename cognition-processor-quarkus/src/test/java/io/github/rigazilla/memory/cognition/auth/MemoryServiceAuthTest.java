package io.github.rigazilla.memory.cognition.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import io.github.rigazilla.memory.cognition.config.CognitionConfig;
import io.grpc.ClientInterceptor;
import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.OidcClients;
import io.quarkus.oidc.client.Tokens;
import io.quarkus.oidc.client.runtime.OidcClientConfig;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;

/** Startup, mode wiring and shutdown of {@link MemoryServiceAuth} (no Quarkus runtime needed). */
class MemoryServiceAuthTest {

    private StubMemoryServiceConfig config;
    private OidcClients oidcClients;
    private OidcClient oidcClient;
    private MemoryServiceAuth auth;

    @BeforeEach
    void setUp() {
        config = new StubMemoryServiceConfig();
        oidcClients = mock(OidcClients.class);
        oidcClient = mock(OidcClient.class);
        when(oidcClients.newClient(any(OidcClientConfig.class))).thenReturn(Uni.createFrom().item(oidcClient));
        CognitionConfig cognition = mock(CognitionConfig.class, Answers.RETURNS_DEEP_STUBS);
        when(cognition.worker().id()).thenReturn("cognition_processor");

        auth = new MemoryServiceAuth();
        auth.memoryService = config;
        auth.cognition = cognition;
        auth.oidcClients = oidcClients;
    }

    private void oidcSettings() {
        config.clientSecret = Optional.of("secret");
        config.tokenUrl = Optional.of("https://idp.example/token");
    }

    private void tokenIssued() {
        Tokens tokens = new Tokens("access", Instant.now().plus(Duration.ofHours(1)).getEpochSecond(),
                Duration.ofSeconds(5), null, null, new JsonObject(), "cognition_processor");
        when(oidcClient.getTokens()).thenReturn(Uni.createFrom().item(tokens));
    }

    @Test
    void apiKeyModeNeverTouchesTheOidcClient() {
        config.apiKey = Optional.of("key");

        auth.onStart(null);

        assertThat(auth.mode()).isEqualTo(AuthMode.API_KEY);
        assertThat(auth.channelInterceptor()).isInstanceOf(ApiKeyInterceptor.class);
        verifyNoInteractions(oidcClients, oidcClient);
        auth.close();
        verifyNoInteractions(oidcClients, oidcClient);
    }

    @Test
    void oidcModeFetchesTheFirstTokenAtStartup() {
        oidcSettings();
        tokenIssued();

        auth.onStart(null);

        assertThat(auth.mode()).isEqualTo(AuthMode.OIDC);
        assertThat(auth.channelInterceptor()).isInstanceOf(UnauthenticatedRetryInterceptor.class);
        verify(oidcClient).getTokens();
        auth.close();
    }

    @Test
    void initialisationIsIdempotent() {
        oidcSettings();
        tokenIssued();

        auth.onStart(null);
        ClientInterceptor first = auth.channelInterceptor();
        auth.onStart(null);

        assertThat(auth.channelInterceptor()).isSameAs(first);
        verify(oidcClients).newClient(any(OidcClientConfig.class));
        auth.close();
    }

    @Test
    void channelInterceptorInitialisesLazilyWhenStartupHasNotRunYet() {
        config.apiKey = Optional.of("key");

        assertThat(auth.channelInterceptor()).isInstanceOf(ApiKeyInterceptor.class);
    }

    @Test
    void partialOidcConfigurationAbortsStartupWithoutFallingBackToTheApiKey() {
        config.apiKey = Optional.of("key");
        config.clientSecret = Optional.of("secret");

        assertThatThrownBy(() -> auth.onStart(null)).isInstanceOf(AuthConfigurationException.class);
        verifyNoInteractions(oidcClients);
    }

    @Test
    void aFailingFirstTokenAbortsStartupAndClosesTheClient() throws Exception {
        oidcSettings();
        when(oidcClient.getTokens()).thenReturn(Uni.createFrom().failure(new IllegalStateException("down")));

        assertThatThrownBy(() -> auth.onStart(null)).isInstanceOf(TokenFetchException.class);

        verify(oidcClient).close();
    }

    @Test
    void aFailureCreatingTheClientAbortsStartupWithoutLeakingDetails() {
        oidcSettings();
        when(oidcClients.newClient(any(OidcClientConfig.class)))
                .thenReturn(Uni.createFrom().failure(new IllegalStateException("password=secret")));

        assertThatThrownBy(() -> auth.onStart(null))
                .isInstanceOf(TokenFetchException.class)
                .hasMessageNotContaining("password");
    }

    @Test
    void closeClosesTheOidcClient() throws Exception {
        oidcSettings();
        tokenIssued();
        auth.onStart(null);

        auth.close();

        verify(oidcClient).close();
    }

    @Test
    void closeWithoutInitialisationIsANoOp() throws Exception {
        auth.close();

        verify(oidcClient, never()).close();
    }
}
