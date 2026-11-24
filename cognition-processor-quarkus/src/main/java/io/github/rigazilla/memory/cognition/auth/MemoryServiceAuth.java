package io.github.rigazilla.memory.cognition.auth;

import io.github.rigazilla.memory.cognition.config.CognitionConfig;
import io.github.rigazilla.memory.cognition.config.MemoryServiceConfig;
import io.grpc.ClientInterceptor;
import io.quarkus.oidc.client.OidcClients;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import org.jboss.logging.Logger;

/**
 * Authentication facade for all memory-service gRPC channels.
 *
 * <p>An early {@link StartupEvent} observer resolves and validates the mode and, in {@code oidc}
 * mode, obtains the first token before {@code GrpcAdminEventClient} connects, so a configuration
 * or token error aborts startup instead of ending up in that client's reconnect loop. In
 * {@code api-key} mode it only validates (no network access, no OIDC client is created).
 */
@ApplicationScoped
public class MemoryServiceAuth {

    private static final Logger LOG = Logger.getLogger(MemoryServiceAuth.class);

    @Inject
    MemoryServiceConfig memoryService;

    @Inject
    CognitionConfig cognition;

    @Inject
    OidcClients oidcClients;

    private volatile AuthMode mode;
    private volatile ClientInterceptor interceptor;
    private OidcTokenProvider tokenProvider;
    private QuarkusOidcTokenSource tokenSource;

    /** Runs before the default-priority startup observers (for example {@code GrpcAdminEventClient}). */
    void onStart(@Observes @Priority(Interceptor.Priority.LIBRARY_BEFORE) StartupEvent event) {
        initialise();
    }

    /** The interceptor every memory-service channel must install. */
    public ClientInterceptor channelInterceptor() {
        initialise();
        return interceptor;
    }

    /** The active authentication mode. */
    public AuthMode mode() {
        initialise();
        return mode;
    }

    /**
     * Stops token renewal. The cached token is still served until it expires, so the final
     * checkpoint save in {@code GrpcAdminEventClient.onShutdown} stays authenticated whatever the
     * bean destruction order is.
     */
    @PreDestroy
    synchronized void close() {
        if (tokenProvider != null) {
            tokenProvider.close();
        }
        if (tokenSource != null) {
            tokenSource.close();
        }
    }

    private synchronized void initialise() {
        if (interceptor != null) {
            return;
        }
        AuthMode resolved = AuthModeResolver.resolve(memoryService, cognition.worker().id());
        ClientInterceptor created;
        switch (resolved) {
            case API_KEY:
                created = new ApiKeyInterceptor(memoryService.apiKey().orElseThrow(), memoryService.clientId());
                break;
            case OIDC:
            default:
                created = createOidcInterceptor();
                break;
        }
        mode = resolved;
        interceptor = created;
        LOG.infof("memory-service authentication mode: %s (client id %s)", resolved, memoryService.clientId());
    }

    /** Fetches the first token; any failure aborts startup. */
    private ClientInterceptor createOidcInterceptor() {
        QuarkusOidcTokenSource source = QuarkusOidcTokenSource.create(oidcClients, memoryService);
        OidcTokenProvider provider = OidcTokenProvider.create(source);
        try {
            provider.start();
        } catch (RuntimeException e) {
            provider.close();
            source.close();
            throw e;
        }
        tokenSource = source;
        tokenProvider = provider;
        return new UnauthenticatedRetryInterceptor(provider);
    }
}
