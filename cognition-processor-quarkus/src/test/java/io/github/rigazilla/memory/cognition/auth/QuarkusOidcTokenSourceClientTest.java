package io.github.rigazilla.memory.cognition.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.quarkus.oidc.client.OidcClients;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Exercises the real Quarkus OIDC client against a local HTTP stub playing the identity provider.
 * The application itself runs in api-key mode: only the {@link OidcClients} bean is used here.
 */
@QuarkusTest
class QuarkusOidcTokenSourceClientTest {

    private static final String SECRET = "super-secret-value";

    @Inject
    OidcClients oidcClients;

    private HttpServer idp;
    private final CopyOnWriteArrayList<Recorded> requests = new CopyOnWriteArrayList<>();
    private final AtomicReference<Reply> reply = new AtomicReference<>();
    private StubMemoryServiceConfig config;
    private QuarkusOidcTokenSource source;

    private record Recorded(String path, String authorization, String body) {
    }

    private record Reply(int status, String body, String location) {
        static Reply json(int status, String body) {
            return new Reply(status, body, null);
        }
    }

    @BeforeEach
    void startIdp() throws IOException {
        idp = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
        idp.createContext("/", this::handle);
        idp.start();
        config = new StubMemoryServiceConfig();
        config.clientSecret = Optional.of(SECRET);
        reply.set(Reply.json(200, tokenJson("access-1", "300")));
    }

    @AfterEach
    void stopIdp() {
        if (source != null) {
            source.close();
        }
        idp.stop(0);
    }

    private String base() {
        return "http://127.0.0.1:" + idp.getAddress().getPort();
    }

    private static String tokenJson(String token, String expiresIn) {
        String expires = expiresIn == null ? "" : ",\"expires_in\":" + expiresIn;
        return "{\"access_token\":\"" + token + "\",\"token_type\":\"Bearer\"" + expires + "}";
    }

    private void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        requests.add(new Recorded(path, exchange.getRequestHeaders().getFirst("Authorization"), body));
        if (path.endsWith("/.well-known/openid-configuration")) {
            send(exchange, 200, "{\"issuer\":\"" + base() + "/realms/r\",\"token_endpoint\":\"" + base()
                    + "/realms/r/protocol/openid-connect/token\"}", null);
            return;
        }
        Reply r = reply.get();
        send(exchange, r.status(), r.body(), r.location());
    }

    private static void send(HttpExchange exchange, int status, String body, String location) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        if (location != null) {
            exchange.getResponseHeaders().add("Location", location);
        }
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private QuarkusOidcTokenSource tokenUrlSource() {
        config.tokenUrl = Optional.of(base() + "/token");
        source = QuarkusOidcTokenSource.create(oidcClients, config);
        return source;
    }

    private Recorded lastTokenRequest() {
        return requests.get(requests.size() - 1);
    }

    private static int closedPort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0, 1, InetAddress.getLoopbackAddress())) {
            return socket.getLocalPort();
        }
    }

    @Test
    void clientCredentialsGrantWithBasicAuthScopeAndAudience() {
        config.scope = Optional.of("read,write");
        config.audience = Optional.of("memory-service");

        TokenSource.Token token = tokenUrlSource().fetch();

        assertThat(token.accessToken()).isEqualTo("access-1");
        assertThat(token.expiresAt()).isAfter(Instant.now().plus(Duration.ofSeconds(200)))
                .isBefore(Instant.now().plus(Duration.ofSeconds(400)));
        Recorded request = lastTokenRequest();
        assertThat(request.path()).isEqualTo("/token");
        String expectedBasic = Base64.getEncoder()
                .encodeToString(("cognition_processor:" + SECRET).getBytes(StandardCharsets.UTF_8));
        assertThat(request.authorization()).isEqualTo("Basic " + expectedBasic);
        String body = URLDecoder.decode(request.body(), StandardCharsets.UTF_8);
        assertThat(body).contains("grant_type=client_credentials")
                .contains("scope=read write")
                .contains("audience=memory-service")
                .doesNotContain(SECRET);
    }

    @Test
    void everyFetchIsANewGrant() {
        QuarkusOidcTokenSource tokenSource = tokenUrlSource();

        tokenSource.fetch();
        tokenSource.fetch();

        assertThat(requests).hasSize(2);
    }

    @Test
    void discoveryResolvesTheTokenEndpoint() {
        config.discoveryUrl = Optional.of(base() + "/realms/r");
        source = QuarkusOidcTokenSource.create(oidcClients, config);

        TokenSource.Token token = source.fetch();

        assertThat(token.accessToken()).isEqualTo("access-1");
        assertThat(requests).extracting(Recorded::path)
                .contains("/realms/r/.well-known/openid-configuration")
                .endsWith("/realms/r/protocol/openid-connect/token");
    }

    @Test
    void providerRejectionDoesNotLeakItsResponseBody() {
        reply.set(Reply.json(400, "{\"error\":\"invalid_client\",\"error_description\":\"" + SECRET + "\"}"));
        QuarkusOidcTokenSource tokenSource = tokenUrlSource();

        assertThatThrownBy(tokenSource::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageNotContaining(SECRET)
                .hasMessageNotContaining("invalid_client");
    }

    @Test
    void redirectsAreNotFollowed() {
        reply.set(new Reply(302, "", base() + "/elsewhere"));
        QuarkusOidcTokenSource tokenSource = tokenUrlSource();

        assertThatThrownBy(tokenSource::fetch).isInstanceOf(TokenFetchException.class);
        assertThat(requests).extracting(Recorded::path).containsOnly("/token");
    }

    @Test
    void missingExpiresInIsRejected() {
        reply.set(Reply.json(200, tokenJson("access-1", null)));
        QuarkusOidcTokenSource tokenSource = tokenUrlSource();

        assertThatThrownBy(tokenSource::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageContaining("expires_in");
    }

    @Test
    void zeroExpiresInIsRejected() {
        reply.set(Reply.json(200, tokenJson("access-1", "0")));
        QuarkusOidcTokenSource tokenSource = tokenUrlSource();

        assertThatThrownBy(tokenSource::fetch).isInstanceOf(TokenFetchException.class);
    }

    @Test
    void unreachableTokenEndpointIsReportedWithoutLeakingDetails() throws IOException {
        config.tokenUrl = Optional.of("http://127.0.0.1:" + closedPort() + "/token");
        source = QuarkusOidcTokenSource.create(oidcClients, config);

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageNotContaining(SECRET);
    }

    @Test
    void unreachableDiscoveryEndpointFailsTheFirstFetch() throws IOException {
        config.discoveryUrl = Optional.of("http://127.0.0.1:" + closedPort() + "/realms/r");
        source = QuarkusOidcTokenSource.create(oidcClients, config);

        assertThatThrownBy(source::fetch)
                .isInstanceOf(TokenFetchException.class)
                .hasMessageNotContaining(SECRET);
    }
}
