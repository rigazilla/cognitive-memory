package io.github.rigazilla.memory.cognition.token;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link TokenTracker}.
 *
 * <p>Pure JUnit 5 — no CDI container required.
 * {@link TokenTracker} is instantiated directly because it has no CDI-injected fields.
 */
class TokenTrackerTest {

    private TokenTracker tracker = new TokenTracker();

    // -------------------------------------------------------------------------
    // snapshot() before any record
    // -------------------------------------------------------------------------

    @Test
    void snapshotBeforeAnyRecordReturnsZeros() {
        TokenUsage.Snapshot snap = tracker.snapshot("proc", "res");

        assertThat(snap).isNotNull();
        assertThat(snap.inputTokens()).isZero();
        assertThat(snap.outputTokens()).isZero();
        assertThat(snap.totalTokens()).isZero();
        assertThat(snap.requestCount()).isZero();
        assertThat(snap.averageTokensPerRequest()).isZero();
    }

    // -------------------------------------------------------------------------
    // record() + snapshot()
    // -------------------------------------------------------------------------

    @Test
    void recordedTokensAppearInSnapshot() {
        tracker.record("proc-a", "extractor", 100, 50);

        TokenUsage.Snapshot snap = tracker.snapshot("proc-a", "extractor");

        assertThat(snap.inputTokens()).isEqualTo(100);
        assertThat(snap.outputTokens()).isEqualTo(50);
        assertThat(snap.totalTokens()).isEqualTo(150);
        assertThat(snap.requestCount()).isEqualTo(1);
    }

    @Test
    void multipleRecordsAccumulate() {
        tracker.record("proc-a", "extractor", 100, 50);
        tracker.record("proc-a", "extractor", 200, 80);

        TokenUsage.Snapshot snap = tracker.snapshot("proc-a", "extractor");

        assertThat(snap.inputTokens()).isEqualTo(300);
        assertThat(snap.outputTokens()).isEqualTo(130);
        assertThat(snap.totalTokens()).isEqualTo(430);
        assertThat(snap.requestCount()).isEqualTo(2);
        assertThat(snap.averageTokensPerRequest()).isEqualTo(215);
    }

    // -------------------------------------------------------------------------
    // Different resources are tracked independently
    // -------------------------------------------------------------------------

    @Test
    void differentResourcesAreIndependent() {
        tracker.record("proc-a", "extractor", 100, 50);
        tracker.record("proc-a", "verifier", 30, 10);

        TokenUsage.Snapshot extractor = tracker.snapshot("proc-a", "extractor");
        TokenUsage.Snapshot verifier = tracker.snapshot("proc-a", "verifier");

        assertThat(extractor.totalTokens()).isEqualTo(150);
        assertThat(verifier.totalTokens()).isEqualTo(40);
    }

    // -------------------------------------------------------------------------
    // Different processes are tracked independently
    // -------------------------------------------------------------------------

    @Test
    void differentProcessesAreIndependent() {
        tracker.record("proc-a", "extractor", 100, 50);
        tracker.record("proc-b", "extractor", 200, 80);

        TokenUsage.Snapshot a = tracker.snapshot("proc-a", "extractor");
        TokenUsage.Snapshot b = tracker.snapshot("proc-b", "extractor");

        assertThat(a.totalTokens()).isEqualTo(150);
        assertThat(b.totalTokens()).isEqualTo(280);
    }

    // -------------------------------------------------------------------------
    // Null token counts are tolerated
    // -------------------------------------------------------------------------

    @Test
    void recordWithNullValuesDoesNotThrow() {
        tracker.record("proc", "res", null, null);

        TokenUsage.Snapshot snap = tracker.snapshot("proc", "res");

        assertThat(snap.totalTokens()).isZero();
        assertThat(snap.requestCount()).isEqualTo(1);
    }
}
