package io.github.rigazilla.memory.cognition.auth;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.jboss.logging.Logger;

/**
 * Owns the access token: one cached token, one background renewal, back-off on failures.
 *
 * <ul>
 *   <li>{@link #start()} fetches the first token synchronously; a failure is fatal for the caller.
 *   <li>Renewal is scheduled at {@code expiry - min(30 s, 10% of the lifetime at receipt)}. While
 *       it runs, and while it keeps failing, the still-valid cached token is served.
 *   <li>A failed renewal is retried with a back-off of 100 ms doubling to 30 s, until closed.
 *   <li>{@link #invalidate(String)} discards the cached token only if it is the rejected one, and
 *       triggers one renewal (at most one per second, so a server that keeps rejecting fresh
 *       tokens cannot make this hammer the token endpoint).
 *   <li>Only an expired or invalidated token makes {@link #token()} callers wait, for at most
 *       10 s.
 *   <li>{@link #close()} stops renewal; a still-valid cached token keeps being served.
 * </ul>
 *
 * <p>Errors are {@link TokenFetchException}s with fixed messages: never the secret or a response
 * body.
 */
public final class OidcTokenProvider implements AutoCloseable {

    static final Duration MAX_RENEW_LEAD = Duration.ofSeconds(30);
    static final long INITIAL_BACKOFF_MILLIS = 100;
    static final long MAX_BACKOFF_MILLIS = 30_000;
    static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);
    static final Duration MIN_INVALIDATE_REFETCH_INTERVAL = Duration.ofSeconds(1);

    private static final Logger LOG = Logger.getLogger(OidcTokenProvider.class);

    private final TokenSource source;
    private final Clock clock;
    private final Scheduler scheduler;

    private final Object lock = new Object();
    private final List<CompletableFuture<String>> waiters = new ArrayList<>();
    private TokenSource.Token current;
    private Instant lastFetch = Instant.MIN;
    private boolean renewing;
    private boolean closed;
    private long backoffMillis;
    private long generation;

    OidcTokenProvider(TokenSource source, Clock clock, Scheduler scheduler) {
        this.source = source;
        this.clock = clock;
        this.scheduler = scheduler;
    }

    /** Creates a provider with a real clock and scheduler. */
    public static OidcTokenProvider create(TokenSource source) {
        return new OidcTokenProvider(source, Clock.systemUTC(), new ExecutorScheduler("oidc-token-renewal"));
    }

    /**
     * Fetches the first token synchronously and schedules its renewal.
     *
     * @throws TokenFetchException if no usable token could be obtained
     */
    public void start() {
        TokenSource.Token token = fetchValidated();
        synchronized (lock) {
            accept(token);
        }
    }

    /**
     * The access token to use now. Completes immediately with the cached token while it is valid;
     * otherwise when the next token arrives, or exceptionally after 10 s or when closed.
     */
    public CompletionStage<String> token() {
        synchronized (lock) {
            if (current != null && clock.instant().isBefore(current.expiresAt())) {
                return CompletableFuture.completedFuture(current.accessToken());
            }
            if (closed) {
                return CompletableFuture.failedFuture(
                        new TokenFetchException("OIDC token expired and the token provider is closed"));
            }
            CompletableFuture<String> waiter = new CompletableFuture<>();
            waiters.add(waiter);
            scheduler.schedule(() -> {
                if (waiter.completeExceptionally(new TokenFetchException("Timed out waiting for an OIDC token"))) {
                    synchronized (lock) {
                        waiters.remove(waiter);
                    }
                }
            }, WAIT_TIMEOUT);
            return waiter;
        }
    }

    /**
     * Reports that memory-service rejected {@code rejectedToken}. A no-op unless that is still the
     * cached token (a concurrent caller may already have replaced it).
     */
    public void invalidate(String rejectedToken) {
        synchronized (lock) {
            if (current == null || !current.accessToken().equals(rejectedToken)) {
                return;
            }
            current = null;
            if (closed || renewing) {
                return;
            }
            Duration wait = Duration.between(clock.instant(), lastFetch.plus(MIN_INVALIDATE_REFETCH_INTERVAL));
            scheduleRenewalLocked(wait.isNegative() ? Duration.ZERO : wait);
        }
    }

    /** Stops renewal and fails the waiters; a still-valid cached token keeps being served. */
    @Override
    public void close() {
        List<CompletableFuture<String>> toFail;
        synchronized (lock) {
            if (closed) {
                return;
            }
            closed = true;
            generation++;
            toFail = new ArrayList<>(waiters);
            waiters.clear();
        }
        scheduler.close();
        toFail.forEach(w -> w.completeExceptionally(
                new TokenFetchException("OIDC token provider closed while waiting for a token")));
    }

    // ---- renewal ---------------------------------------------------------------------------

    private void scheduleRenewalLocked(Duration delay) {
        long expected = ++generation;
        scheduler.schedule(() -> renew(expected), delay);
    }

    private void renew(long expectedGeneration) {
        synchronized (lock) {
            if (closed || renewing || expectedGeneration != generation) {
                return;
            }
            renewing = true;
        }
        TokenSource.Token token = null;
        TokenFetchException failure = null;
        try {
            token = fetchValidated();
        } catch (TokenFetchException e) {
            failure = e;
        }
        synchronized (lock) {
            renewing = false;
            if (closed) {
                return;
            }
            if (token != null) {
                accept(token);
                return;
            }
            backoffMillis = backoffMillis == 0
                    ? INITIAL_BACKOFF_MILLIS
                    : Math.min(backoffMillis * 2, MAX_BACKOFF_MILLIS);
            LOG.warnf("OIDC token renewal failed (%s); retrying in %d ms", failure.getMessage(), backoffMillis);
            scheduleRenewalLocked(Duration.ofMillis(backoffMillis));
        }
    }

    private TokenSource.Token fetchValidated() {
        TokenSource.Token token;
        try {
            token = source.fetch();
        } catch (TokenFetchException e) {
            throw e;
        } catch (RuntimeException e) {
            // A TokenSource should only throw TokenFetchException; never let anything else carry
            // provider data into the logs.
            throw new TokenFetchException("OIDC token request failed (" + e.getClass().getSimpleName() + ")");
        }
        if (token == null || token.accessToken() == null || token.accessToken().isBlank()
                || token.expiresAt() == null || !token.expiresAt().isAfter(clock.instant())) {
            throw new TokenFetchException("OIDC token response requires an access token and a positive expires_in");
        }
        return token;
    }

    /** Stores a fresh token, resets the back-off, wakes the waiters and schedules the next renewal. */
    private void accept(TokenSource.Token token) {
        Instant now = clock.instant();
        current = token;
        lastFetch = now;
        backoffMillis = 0;
        List<CompletableFuture<String>> ready = new ArrayList<>(waiters);
        waiters.clear();
        Duration remaining = Duration.between(now, token.expiresAt());
        Duration tenth = remaining.dividedBy(10);
        Duration lead = tenth.compareTo(MAX_RENEW_LEAD) < 0 ? tenth : MAX_RENEW_LEAD;
        scheduleRenewalLocked(remaining.minus(lead));
        // Dependents of a waiter must not block: BearerCallCredentials moves on to the gRPC executor.
        ready.forEach(w -> w.complete(token.accessToken()));
    }
}
