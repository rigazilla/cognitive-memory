package io.github.rigazilla.memory.cognition.auth;

import io.github.rigazilla.memory.cognition.config.MemoryServiceConfig;
import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.OidcClientException;
import io.quarkus.oidc.client.OidcClientConfigBuilder;
import io.quarkus.oidc.client.OidcClients;
import io.quarkus.oidc.client.Tokens;
import io.quarkus.oidc.client.runtime.OidcClientConfig;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import org.jboss.logging.Logger;

/**
 * {@link TokenSource} backed by a programmatically created Quarkus {@link OidcClient}
 * (client-credentials grant). The client does not cache: every {@link #fetch()} is a new grant.
 *
 * <p>The client's exceptions can embed the provider's response body, so every failure is mapped to
 * a {@link TokenFetchException} carrying only a fixed category (or the exception class name).
 * Redirects are not followed. A response without an access token or without a positive
 * {@code expires_in} is rejected.
 */
public final class QuarkusOidcTokenSource implements TokenSource, AutoCloseable {

    static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);

    private static final Logger LOG = Logger.getLogger(QuarkusOidcTokenSource.class);
    private static final String UNREACHABLE_MESSAGE = "OIDC Server is not available";

    private final OidcClient client;
    private final Clock clock;
    private final Duration timeout;

    QuarkusOidcTokenSource(OidcClient client, Clock clock, Duration timeout) {
        this.client = client;
        this.clock = clock;
        this.timeout = timeout;
    }

    /**
     * Builds the OIDC client for the validated {@code memory-service.auth.oidc.*} settings. With a
     * discovery URL the provider metadata is fetched lazily, so an unreachable provider shows up
     * on the first {@link #fetch()}, not here.
     *
     * @throws TokenFetchException if the client cannot be created
     */
    public static QuarkusOidcTokenSource create(OidcClients clients, MemoryServiceConfig config) {
        OidcClientConfig clientConfig = buildClientConfig(config);
        try {
            OidcClient client = clients.newClient(clientConfig).await().atMost(DEFAULT_TIMEOUT);
            return new QuarkusOidcTokenSource(client, Clock.systemUTC(), DEFAULT_TIMEOUT);
        } catch (Exception e) {
            throw new TokenFetchException("Could not create the OIDC client: " + describe(e));
        }
    }

    static OidcClientConfig buildClientConfig(MemoryServiceConfig config) {
        MemoryServiceConfig.Auth.Oidc oidc = config.auth().oidc();
        OidcClientConfigBuilder builder = OidcClientConfig.builder()
                .id("memory-service")
                .clientId(config.clientId())
                .credentials(oidc.clientSecret().orElseThrow())
                // Never follow a redirect from the token endpoint.
                .followRedirects(false);
        if (oidc.discoveryUrl().isPresent()) {
            builder.discoveryEnabled(true).authServerUrl(oidc.discoveryUrl().get().trim());
        } else {
            builder.discoveryEnabled(false).tokenPath(oidc.tokenUrl().orElseThrow().trim());
        }
        oidc.scope().filter(s -> !s.isBlank()).ifPresent(s -> builder.scopes(
                Arrays.stream(s.split(",")).map(String::trim).filter(p -> !p.isEmpty()).toArray(String[]::new)));
        oidc.audience().filter(a -> !a.isBlank()).ifPresent(a -> builder.audience(a.trim()));
        oidc.tlsConfigurationName().filter(n -> !n.isBlank()).ifPresent(n -> builder.tlsConfigurationName(n.trim()));
        return builder.build();
    }

    @Override
    public Token fetch() {
        Tokens tokens;
        try {
            tokens = client.getTokens().await().atMost(timeout);
        } catch (Exception e) {
            throw new TokenFetchException("OIDC token request failed: " + describe(e));
        }
        String accessToken = tokens == null ? null : tokens.getAccessToken();
        if (accessToken == null || accessToken.isBlank()) {
            throw new TokenFetchException("OIDC token response has no access token");
        }
        Long expiresAtEpochSeconds = tokens.getAccessTokenExpiresAt();
        if (expiresAtEpochSeconds == null) {
            throw new TokenFetchException("OIDC token response has no expires_in");
        }
        Instant expiresAt = Instant.ofEpochSecond(expiresAtEpochSeconds);
        if (!expiresAt.isAfter(clock.instant())) {
            throw new TokenFetchException("OIDC token response has a non-positive expires_in");
        }
        return new Token(accessToken, expiresAt);
    }

    @Override
    public void close() {
        try {
            client.close();
        } catch (IOException | RuntimeException e) {
            LOG.debugf("Closing the OIDC client failed: %s", e.getClass().getSimpleName());
        }
    }

    private static boolean isTimeout(Throwable t) {
        return t instanceof TimeoutException || t instanceof io.smallrye.mutiny.TimeoutException;
    }

    /** Fixed category or class name only: never the exception message (it may hold the response body). */
    static String describe(Throwable t) {
        if (isTimeout(t) || isTimeout(t.getCause())) {
            return "timed out";
        }
        if (t instanceof OidcClientException) {
            return UNREACHABLE_MESSAGE.equals(t.getMessage())
                    ? "the OIDC provider is not reachable (or its TLS certificate was not accepted)"
                    : "the OIDC provider rejected the request";
        }
        return t.getClass().getSimpleName();
    }
}
