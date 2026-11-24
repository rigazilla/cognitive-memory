package io.github.rigazilla.memory.cognition.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OidcTokenProviderTest {

    private static final Duration HOUR = Duration.ofHours(1);

    private MutableClock clock;
    private ManualScheduler scheduler;
    private FakeTokenSource source;
    private OidcTokenProvider provider;

    @BeforeEach
    void setUp() {
        clock = new MutableClock(Instant.parse("2026-01-01T00:00:00Z"));
        scheduler = new ManualScheduler();
        source = new FakeTokenSource(clock);
        provider = new OidcTokenProvider(source, clock, scheduler);
    }

    private static String value(CompletableFuture<String> future) {
        assertThat(future).isCompleted();
        return future.join();
    }

    private String token() {
        return value(provider.token().toCompletableFuture());
    }

    // ---- start -----------------------------------------------------------------------------

    @Test
    void startFetchesTheFirstTokenAndServesIt() {
        source.thenToken(HOUR);
        provider.start();
        assertThat(token()).isEqualTo("token-1");
        assertThat(source.fetches()).isEqualTo(1);
    }

    @Test
    void failedFirstFetchIsFatal() {
        source.thenFail();
        assertThatThrownBy(provider::start).isInstanceOf(TokenFetchException.class);
        assertThat(scheduler.size()).isZero();
    }

    @Test
    void rejectsAlreadyExpiredAndBlankTokens() {
        source.then(() -> new TokenSource.Token("t", clock.instant()))
                .then(() -> new TokenSource.Token(" ", clock.instant().plus(HOUR)))
                .then(() -> new TokenSource.Token("t", null));
        for (int i = 0; i < 3; i++) {
            assertThatThrownBy(provider::start)
                    .isInstanceOf(TokenFetchException.class)
                    .hasMessageContaining("positive expires_in");
        }
    }

    @Test
    void foreignExceptionsAreWrappedWithoutTheirMessage() {
        source.then(() -> {
            throw new IllegalStateException("client_secret=hunter2 body={...}");
        });
        assertThatThrownBy(provider::start)
                .isInstanceOf(TokenFetchException.class)
                .hasMessage("OIDC token request failed (IllegalStateException)")
                .hasNoCause();
    }

    // ---- renewal scheduling ----------------------------------------------------------------

    @ParameterizedTest(name = "lifetime {0} s renews after {1} ms")
    @CsvSource({"3600, 3570000", "300, 270000", "100, 90000", "5, 4500"})
    void renewsAtExpiryMinusTheSmallerOfThirtySecondsAndTenPercent(long lifetimeSeconds, long delayMillis) {
        source.thenToken(Duration.ofSeconds(lifetimeSeconds));
        provider.start();
        assertThat(scheduler.last().delay()).isEqualTo(Duration.ofMillis(delayMillis));
    }

    @Test
    void renewalReplacesTheTokenAndSchedulesTheNextOne() {
        source.thenToken(HOUR).thenToken(HOUR);
        provider.start();
        scheduler.runLast();
        assertThat(token()).isEqualTo("token-2");
        assertThat(scheduler.size()).isEqualTo(1);
        assertThat(scheduler.last().delay()).isEqualTo(Duration.ofSeconds(3570));
    }

    @Test
    void cachedTokenKeepsBeingServedWhileRenewalIsInFlight() {
        AtomicReference<String> seenDuringRenewal = new AtomicReference<>();
        source.thenToken(HOUR).then(() -> {
            seenDuringRenewal.set(value(provider.token().toCompletableFuture()));
            return new TokenSource.Token("token-2", clock.instant().plus(HOUR));
        });
        provider.start();
        scheduler.runLast();
        assertThat(seenDuringRenewal.get()).isEqualTo("token-1");
        assertThat(token()).isEqualTo("token-2");
    }

    // ---- failures and back-off -------------------------------------------------------------

    @Test
    void failedRenewalsBackOffFromOneHundredMillisDoublingToThirtySeconds() {
        source.thenToken(HOUR);
        for (int i = 0; i < 12; i++) {
            source.thenFail();
        }
        provider.start();
        scheduler.runLast();

        List<Long> delays = new ArrayList<>();
        delays.add(scheduler.last().delay().toMillis());
        for (int i = 0; i < 11; i++) {
            scheduler.runLast();
            delays.add(scheduler.last().delay().toMillis());
        }
        assertThat(delays).containsExactly(
                100L, 200L, 400L, 800L, 1600L, 3200L, 6400L, 12800L, 25600L, 30000L, 30000L, 30000L);
        // The still-valid token was served the whole time.
        assertThat(token()).isEqualTo("token-1");
    }

    @Test
    void backOffResetsAfterASuccess() {
        source.thenToken(HOUR).thenFail().thenFail().thenToken(HOUR).thenFail();
        provider.start();
        scheduler.runLast();
        scheduler.runLast();
        assertThat(scheduler.last().delay()).isEqualTo(Duration.ofMillis(200));
        scheduler.runLast(); // success
        scheduler.runLast(); // failure again
        assertThat(scheduler.last().delay()).isEqualTo(Duration.ofMillis(100));
    }

    @Test
    void failedRenewalNeverDropsAStillValidToken() {
        source.thenToken(HOUR).thenFail();
        provider.start();
        scheduler.runLast();
        clock.advance(Duration.ofMinutes(59));
        assertThat(token()).isEqualTo("token-1");
    }

    @Test
    void expiredTokenMakesCallersWaitUntilTheNextTokenArrives() {
        source.thenToken(Duration.ofSeconds(100)).thenFail().thenToken(HOUR);
        provider.start();
        scheduler.runLast();
        clock.advance(Duration.ofSeconds(101));

        CompletableFuture<String> waiter = provider.token().toCompletableFuture();
        assertThat(waiter).isNotDone();

        scheduler.run(0); // the back-off retry (the wait time-out task is second)
        assertThat(value(waiter)).isEqualTo("token-2");
    }

    @Test
    void waitIsBoundedAndTimesOut() {
        source.thenToken(Duration.ofSeconds(100));
        provider.start();
        clock.advance(Duration.ofSeconds(101));

        CompletableFuture<String> waiter = provider.token().toCompletableFuture();
        ManualScheduler.Scheduled timeout = scheduler.tasks.stream()
                .filter(t -> t.delay().equals(OidcTokenProvider.WAIT_TIMEOUT)).findFirst().orElseThrow();
        timeout.task().run();

        assertThat(waiter).isCompletedExceptionally();
        assertThatThrownBy(waiter::join).hasMessageContaining("Timed out waiting for an OIDC token");
    }

    // ---- invalidate ------------------------------------------------------------------------

    @Test
    void invalidatingTheCachedTokenDropsItAndTriggersOneRefetchAtMostPerSecond() {
        source.thenToken(HOUR).thenToken(HOUR);
        provider.start();

        provider.invalidate("token-1");

        assertThat(scheduler.last().delay()).isEqualTo(OidcTokenProvider.MIN_INVALIDATE_REFETCH_INTERVAL);
        CompletableFuture<String> waiter = provider.token().toCompletableFuture();
        assertThat(waiter).isNotDone();
        scheduler.tasks.stream().filter(t -> t.delay().equals(OidcTokenProvider.MIN_INVALIDATE_REFETCH_INTERVAL))
                .findFirst().orElseThrow().task().run();
        assertThat(value(waiter)).isEqualTo("token-2");
    }

    @Test
    void invalidateRefetchIsImmediateWhenTheTokenIsOlderThanTheMinimumInterval() {
        source.thenToken(HOUR);
        provider.start();
        clock.advance(Duration.ofSeconds(5));
        provider.invalidate("token-1");
        assertThat(scheduler.last().delay()).isEqualTo(Duration.ZERO);
    }

    @Test
    void invalidatingAnOlderTokenIsANoOp() {
        source.thenToken(HOUR).thenToken(HOUR);
        provider.start();
        scheduler.runLast(); // token-2 is cached now
        int scheduled = scheduler.size();

        provider.invalidate("token-1");

        assertThat(scheduler.size()).isEqualTo(scheduled);
        assertThat(token()).isEqualTo("token-2");
    }

    @Test
    void invalidatingAnUnknownTokenIsANoOp() {
        source.thenToken(HOUR);
        provider.start();
        int scheduled = scheduler.size();
        provider.invalidate(null);
        provider.invalidate("other");
        assertThat(scheduler.size()).isEqualTo(scheduled);
        assertThat(token()).isEqualTo("token-1");
    }

    @Test
    void invalidateDuringAnInFlightRenewalDoesNotStartASecondFetch() {
        source.thenToken(HOUR).then(() -> {
            provider.invalidate("token-1");
            return new TokenSource.Token("token-2", clock.instant().plus(HOUR));
        });
        provider.start();
        scheduler.runLast();
        assertThat(source.fetches()).isEqualTo(2);
        assertThat(token()).isEqualTo("token-2");
        assertThat(scheduler.size()).isEqualTo(1);
    }

    @Test
    void aSupersededScheduledRenewalDoesNothing() {
        source.thenToken(HOUR).thenToken(HOUR);
        provider.start();
        provider.invalidate("token-1");
        ManualScheduler.Scheduled proactive = scheduler.tasks.get(0);
        proactive.task().run();
        assertThat(source.fetches()).isEqualTo(1);
    }

    // ---- close -----------------------------------------------------------------------------

    @Test
    void closeStopsRenewalButTheCachedTokenIsServedUntilItExpires() {
        source.thenToken(Duration.ofMinutes(10)).thenToken(HOUR);
        provider.start();
        provider.close();
        provider.close(); // idempotent

        assertThat(scheduler.closed).isTrue();
        scheduler.run(0);
        assertThat(source.fetches()).isEqualTo(1);
        assertThat(token()).isEqualTo("token-1");

        clock.advance(Duration.ofMinutes(11));
        CompletableFuture<String> expired = provider.token().toCompletableFuture();
        assertThat(expired).isCompletedExceptionally();
        assertThatThrownBy(expired::join).hasMessageContaining("closed");
    }

    @Test
    void closeFailsPendingWaiters() {
        source.thenToken(Duration.ofSeconds(100));
        provider.start();
        clock.advance(Duration.ofSeconds(101));
        CompletableFuture<String> waiter = provider.token().toCompletableFuture();

        provider.close();

        assertThat(waiter).isCompletedExceptionally();
    }

    @Test
    void aRenewalFinishingAfterCloseIsDiscarded() {
        AtomicBoolean closedDuringFetch = new AtomicBoolean();
        source.thenToken(HOUR).then(() -> {
            provider.close();
            closedDuringFetch.set(true);
            return new TokenSource.Token("token-2", clock.instant().plus(HOUR));
        });
        provider.start();
        scheduler.runLast();
        assertThat(closedDuringFetch).isTrue();
        assertThat(token()).isEqualTo("token-1");
    }

    // ---- concurrency -----------------------------------------------------------------------

    @Test
    void concurrentCallersAlwaysGetATokenWhileTheTokenIsBeingRenewed() throws Exception {
        java.util.concurrent.atomic.AtomicInteger counter = new java.util.concurrent.atomic.AtomicInteger();
        TokenSource shortLived = () -> new TokenSource.Token(
                "t" + counter.incrementAndGet(), Instant.now().plusMillis(300));
        OidcTokenProvider real = OidcTokenProvider.create(shortLived);
        ExecutorService pool = Executors.newFixedThreadPool(8);
        try {
            real.start();
            long end = System.nanoTime() + TimeUnit.SECONDS.toNanos(2);
            List<Future<Integer>> results = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                results.add(pool.submit(() -> {
                    int served = 0;
                    while (System.nanoTime() < end) {
                        assertThat(real.token().toCompletableFuture().get(5, TimeUnit.SECONDS)).startsWith("t");
                        served++;
                    }
                    return served;
                }));
            }
            for (Future<Integer> result : results) {
                assertThat(result.get()).isPositive();
            }
            assertThat(counter.get()).isGreaterThan(3);
        } catch (ExecutionException e) {
            throw new AssertionError(e.getCause());
        } finally {
            pool.shutdownNow();
            real.close();
        }
    }
}
