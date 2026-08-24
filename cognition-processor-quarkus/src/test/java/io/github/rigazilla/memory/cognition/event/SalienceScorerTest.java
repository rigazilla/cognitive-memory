package io.github.rigazilla.memory.cognition.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SalienceScorerTest {

    private SalienceScorer scorer;

    @BeforeEach
    void setUp() {
        scorer = new SalienceScorer();
        scorer.enabled = true;
        scorer.threshold = 0.3;
        scorer.minLength = 10;
        scorer.greetingEnabled = true;
        scorer.acknowledgmentEnabled = true;
        scorer.farewellEnabled = true;
        scorer.thanksEnabled = true;
        scorer.keywordsEnabled = true;
        scorer.fillerEnabled = true;
        scorer.keywordsList = Optional.empty();
        scorer.keywordsFile = Optional.empty();
        scorer.metricsEnabled = true;
    }

    private void init(SalienceScorer s) {
        s.init();
    }

    // -------------------------------------------------------------------------
    // Pattern groups — each must be filtered (score ≤ 0.3 → shouldKeep false)
    // -------------------------------------------------------------------------

    @Nested
    class PatternGroups {

        @BeforeEach
        void initScorer() {
            init(scorer);
        }

        @ParameterizedTest(name = "greeting \"{0}\" is filtered")
        @CsvSource({"hi", "hello", "good morning"})
        void greeting_isFiltered(String text) {
            assertThat(scorer.shouldKeep(text)).isFalse();
        }

        @ParameterizedTest(name = "acknowledgment \"{0}\" is filtered")
        @CsvSource({"ok", "got it", "understood"})
        void acknowledgment_isFiltered(String text) {
            assertThat(scorer.shouldKeep(text)).isFalse();
        }

        @Test
        void farewell_bye_isFiltered() {
            assertThat(scorer.shouldKeep("bye")).isFalse();
        }

        @Test
        void farewell_seeYou_isFiltered() {
            assertThat(scorer.shouldKeep("see you")).isFalse();
        }

        @ParameterizedTest(name = "filler \"{0}\" is filtered")
        @CsvSource({"um", "hmm"})
        void filler_isFiltered(String text) {
            assertThat(scorer.shouldKeep(text)).isFalse();
        }

        @Test
        void filler_like_standaloneIsFiltered() {
            // "like" alone is 4 chars — FILLER pattern fires before keyword check,
            // scoring 0.1. It must NOT be elevated to 0.8 by the keyword list.
            assertThat(scorer.score("like")).isEqualTo(0.1);
            assertThat(scorer.shouldKeep("like")).isFalse();
        }

        @ParameterizedTest(name = "thanks \"{0}\" is filtered")
        @CsvSource({"thanks", "thank you"})
        void thanks_isFiltered(String text) {
            assertThat(scorer.shouldKeep(text)).isFalse();
        }
    }

    // -------------------------------------------------------------------------
    // Anchoring regression guards — named patterns must NOT partial-match
    // -------------------------------------------------------------------------

    @Nested
    class AnchorGuards {

        @BeforeEach
        void initScorer() {
            init(scorer);
        }

        @Test
        void greeting_withSubstantiveContent_isKept() {
            // GREETING is anchored — "hi, deploy..." must NOT match
            assertThat(scorer.shouldKeep("hi, deploy the server to prod")).isTrue();
        }

        @Test
        void acknowledgment_withSubstantiveContent_isKept() {
            // ACKNOWLEDGMENT is anchored
            assertThat(scorer.shouldKeep("ok, but the build is failing on main")).isTrue();
        }
    }

    // -------------------------------------------------------------------------
    // Word boundary guards
    // -------------------------------------------------------------------------

    @Nested
    class WordBoundaryGuards {

        @BeforeEach
        void initScorer() {
            init(scorer);
        }

        @Test
        void test_insideLatest_doesNotElevateScore() {
            // "test" has \b word boundaries; "latest" embeds the substring but has no boundary.
            // No keyword matches → score 0.4 (len=19, no pattern, no keyword, len ≤ 20) → kept.
            assertThat(scorer.score("latest build status")).isEqualTo(0.4);
            assertThat(scorer.shouldKeep("latest build status")).isTrue();
        }

        @Test
        void like_asPreferenceKeyword_isKept() {
            // "I like dark mode" — "like" is at a word boundary, scores as keyword
            assertThat(scorer.score("I like dark mode")).isEqualTo(0.8);
            assertThat(scorer.shouldKeep("I like dark mode")).isTrue();
        }

        @Test
        void code_insideCodebase_doesNotMatch() {
            // "code" has \b boundaries; "codebase" embeds it but \bcode\b does not match.
            // len=18, no keyword, no '?', len ≤ 20 → 0.4
            assertThat(scorer.score("check the codebase")).isEqualTo(0.4);
        }

        @Test
        void error_insideErrors_doesNotMatch() {
            // "errors" does not have a word boundary after "error"
            // len=17, no keyword, no '?', len ≤ 20 → 0.4
            assertThat(scorer.score("all errors logged")).isEqualTo(0.4);
        }
    }

    // -------------------------------------------------------------------------
    // Recall guards — keyword check precedes min-length so short keyword-bearing
    // messages are not dropped by the length rule
    // -------------------------------------------------------------------------

    @Nested
    class RecallGuards {

        @BeforeEach
        void initScorer() {
            init(scorer);
        }

        @Test
        void bugHere_veryShortKeywordMessage_isKept() {
            // "bug here" = 8 chars, below minLength(10). Keyword check fires before
            // the length rule, so the message scores 0.8 and is kept.
            assertThat(scorer.score("bug here")).isEqualTo(0.8);
            assertThat(scorer.shouldKeep("bug here")).isTrue();
        }

        @Test
        void prodDown_isKept() {
            // "prod down" = 9 chars — "prod" is in the bundled keyword list, scores 0.8 → kept
            assertThat(scorer.score("prod down")).isEqualTo(0.8);
            assertThat(scorer.shouldKeep("prod down")).isTrue();
        }

        @Test
        void rollback_isKept() {
            // "rollback!" = 9 chars — "rollback" is in the bundled keyword list, scores 0.8 → kept
            assertThat(scorer.score("rollback!")).isEqualTo(0.8);
            assertThat(scorer.shouldKeep("rollback!")).isTrue();
        }
    }

    // -------------------------------------------------------------------------
    // Threshold boundary
    // -------------------------------------------------------------------------

    @Nested
    class ThresholdBoundary {

        @BeforeEach
        void initScorer() {
            init(scorer);
        }

        @Test
        void scoreOf_0_4_isKept_defaultThreshold() {
            // Lowest keep score. "no pattern" = 10 chars, no keyword, no pattern → 0.4
            double s = scorer.score("no pattern");
            assertThat(s).isEqualTo(0.4);
            assertThat(scorer.shouldKeep("no pattern")).isTrue();
        }

        @Test
        void customThreshold_0_8_filtersKeywordScore() {
            // With threshold=0.8, score 0.8 (keyword) is NOT kept (0.8 > 0.8 is false)
            scorer.threshold = 0.8;
            init(scorer);
            assertThat(scorer.shouldKeep("bug here")).isFalse();
        }

        @Test
        void scoreEqualToThreshold_isFiltered() {
            // shouldKeep uses strict > so score == threshold is filtered, not kept.
            // Set threshold to 0.4 (the floor keep score) and use a 10-char no-keyword
            // input that scores exactly 0.4; it must be filtered at this threshold.
            scorer.threshold = 0.4;
            double s = scorer.score("no pattern");
            assertThat(s).isEqualTo(0.4);
            assertThat(scorer.shouldKeep("no pattern")).isFalse();
        }
    }

    // -------------------------------------------------------------------------
    // Scoring bands — explicit score assertions for each medium/high rule
    // -------------------------------------------------------------------------

    @Nested
    class ScoringBands {

        @BeforeEach
        void initScorer() {
            init(scorer);
        }

        // ---- Medium band (0.4–0.6) -------------------------------------------

        @Test
        void question_scores_0_6() {
            // "What time is it?" — contains '?' → 0.6
            assertThat(scorer.score("What time is it?")).isEqualTo(0.6);
            assertThat(scorer.shouldKeep("What time is it?")).isTrue();
        }

        @Test
        void commandRequest_showMeTheLogs_scores_0_4() {
            // "Show me the logs" = 16 chars, no keyword, no '?' → 0.4 (len ≤ 20)
            assertThat(scorer.score("Show me the logs")).isEqualTo(0.4);
            assertThat(scorer.shouldKeep("Show me the logs")).isTrue();
        }

        @Test
        void statement_over20chars_scores_0_5() {
            // "check the recent output" = 23 chars, no keyword, no '?' → 0.5
            assertThat(scorer.score("check the recent output")).isEqualTo(0.5);
            assertThat(scorer.shouldKeep("check the recent output")).isTrue();
        }

        // ---- High band (0.7–1.0) ---------------------------------------------

        @ParameterizedTest(name = "decision keyword \"{0}\" scores 0.8")
        @CsvSource({
            "we decided to use Postgres",
            "I chose Python for this",
            "selected the new framework"
        })
        void decisionKeyword_scores_0_8(String text) {
            assertThat(scorer.score(text)).isEqualTo(0.8);
            assertThat(scorer.shouldKeep(text)).isTrue();
        }

        @ParameterizedTest(name = "procedure keyword \"{0}\" scores 0.8")
        @CsvSource({
            "here are the steps to run it",
            "the review process takes time",
            "update the CI workflow config"
        })
        void procedureKeyword_scores_0_8(String text) {
            assertThat(scorer.score(text)).isEqualTo(0.8);
            assertThat(scorer.shouldKeep(text)).isTrue();
        }

        @Test
        void longMessage_over50chars_scores_0_9() {
            // > 50 chars, no keyword — scores 0.9
            String noKw = "The application was restarted and seems stable again";
            assertThat(noKw.length()).isGreaterThan(50);
            assertThat(scorer.score(noKw)).isEqualTo(0.9);
            assertThat(scorer.shouldKeep(noKw)).isTrue();
        }

        @Test
        void longQuestion_over50chars_scores_0_9_notQuestion() {
            // Length rule fires before question detection: a >50-char question scores 0.9
            // (high band), not 0.6 (medium band). See class javadoc for precedence rationale.
            String longQ = "Is the deployment pipeline broken for the staging environment right now?";
            assertThat(longQ.length()).isGreaterThan(50);
            assertThat(scorer.score(longQ)).isEqualTo(0.9);
            assertThat(scorer.shouldKeep(longQ)).isTrue();
        }
    }

    // -------------------------------------------------------------------------
    // Config behaviour
    // -------------------------------------------------------------------------

    @Nested
    class ConfigBehaviour {

        @Test
        void emptyConfig_allDefaultsActive_bundledKeywordsLoaded() {
            // All @ConfigProperty defaults active; bundled default-keywords.txt loaded
            init(scorer);
            assertThat(scorer.shouldKeep("deploy now")).isTrue();
            assertThat(scorer.shouldKeep("hi")).isFalse();
        }

        @Test
        void disabled_everythingPassesThrough() {
            scorer.enabled = false;
            init(scorer);
            assertThat(scorer.shouldKeep("hi")).isTrue();
            assertThat(scorer.shouldKeep("ok")).isTrue();
            assertThat(scorer.shouldKeep(null)).isTrue();
        }

        @Test
        void fillerDisabled_fillerNoLongerFilters() {
            scorer.fillerEnabled = false;
            init(scorer);
            // "well that works" = 15 chars: no pattern active for it, no keyword, len > 10,
            // len ≤ 20 → score 0.4 → kept
            assertThat(scorer.shouldKeep("well that works")).isTrue();
        }

        @Test
        void greetingDisabled_greetingNoLongerFilters() {
            scorer.greetingEnabled = false;
            init(scorer);
            // "good morning" = 12 chars, no keyword, not ack/farewell/filler/thanks,
            // len ≥ 10, len ≤ 20 → score 0.4 → kept
            assertThat(scorer.shouldKeep("good morning")).isTrue();
        }

        @Test
        void acknowledgmentDisabled_ackNoLongerFilters() {
            scorer.acknowledgmentEnabled = false;
            init(scorer);
            // "understood" = 10 chars, not greeting/farewell/filler/thanks,
            // len ≥ 10, len ≤ 20 → score 0.4 → kept
            assertThat(scorer.shouldKeep("understood")).isTrue();
        }

        @Test
        void farewellDisabled_farewellNoLongerFilters() {
            scorer.farewellEnabled = false;
            init(scorer);
            // "see you later" = 13 chars, not filtered by any other pattern,
            // len ≥ 10, len ≤ 20 → score 0.4 → kept
            assertThat(scorer.shouldKeep("see you later")).isTrue();
        }

        @Test
        void thanksDisabled_thanksNoLongerFilters() {
            scorer.thanksEnabled = false;
            init(scorer);
            // "thanks a lot" = 12 chars, not caught by any other pattern,
            // len ≥ 10, len ≤ 20 → score 0.4 → kept
            assertThat(scorer.shouldKeep("thanks a lot")).isTrue();
        }

        @Test
        void keywordsDisabled_keywordMessageScoresLower() {
            scorer.keywordsEnabled = false;
            init(scorer);
            // "bug here" = 8 chars, keyword disabled → falls to length check → 0.1 → filtered
            assertThat(scorer.shouldKeep("bug here")).isFalse();
        }

        @Test
        void metricsDisabled_countersDoNotIncrement() {
            scorer.metricsEnabled = false;
            init(scorer);
            scorer.shouldKeep("hi");
            scorer.shouldKeep("deploy now");
            assertThat(scorer.eventsFiltered.get()).isZero();
            assertThat(scorer.eventsKept.get()).isZero();
            assertThat(scorer.bandLow.get()).isZero();
            assertThat(scorer.bandMedium.get()).isZero();
            assertThat(scorer.bandHigh.get()).isZero();
        }

        @Test
        void minLengthAdjusted_longerThresholdFiltersMoreMessages() {
            scorer.minLength = 20;
            init(scorer);
            // "no pattern" = 10 chars; with minLength=20 → short-message rule → 0.1 → filtered
            assertThat(scorer.score("no pattern")).isEqualTo(0.1);
            assertThat(scorer.shouldKeep("no pattern")).isFalse();
        }
    }

    // -------------------------------------------------------------------------
    // Keyword loading
    // -------------------------------------------------------------------------

    @Nested
    class KeywordLoading {

        @Test
        void bundledDefaultFile_loadsAndParsesCorrectly() {
            List<String> keywords = scorer.loadKeywords();
            assertThat(keywords).isNotEmpty();
            assertThat(keywords).contains("error", "bug", "deploy", "test", "code",
                    "rollback", "prod",
                    "prefer", "like", "want", "need",
                    "decided", "chose", "selected",
                    "steps", "process", "workflow");
        }

        @Test
        void externalKeywordsFile_usedInsteadOfBundled(@TempDir Path tempDir) throws IOException {
            Path kwFile = tempDir.resolve("custom-keywords.txt");
            Files.writeString(kwFile, "# custom\ncrash\noutage\n\n");
            scorer.keywordsFile = Optional.of(kwFile.toString());

            List<String> keywords = scorer.loadKeywords();
            assertThat(keywords).containsExactlyInAnyOrder("crash", "outage");
            assertThat(keywords).doesNotContain("error", "bug");
        }

        @Test
        void missingExternalKeywordsFile_warnsAndFallsBackToBundled(@TempDir Path tempDir) {
            Path missing = tempDir.resolve("does-not-exist.txt");
            scorer.keywordsFile = Optional.of(missing.toString());

            Logger julLogger = Logger.getLogger(SalienceScorer.class.getName());
            WarnCapture warnCapture = new WarnCapture();
            julLogger.addHandler(warnCapture);
            julLogger.setLevel(Level.ALL);

            try {
                List<String> keywords = scorer.loadKeywords();
                assertThat(keywords).isNotEmpty();
                assertThat(keywords).contains("error", "bug");
                assertThat(warnCapture.sawWarn).isTrue();
            } finally {
                julLogger.removeHandler(warnCapture);
            }
        }

        @Test
        void inlineKeywordsList_overridesBundled() {
            scorer.keywordsList = Optional.of("crash,outage,incident");

            List<String> keywords = scorer.loadKeywords();
            assertThat(keywords).containsExactlyInAnyOrder("crash", "outage", "incident");
            assertThat(keywords).doesNotContain("error", "bug");
        }

        @Test
        void inlineKeywordsList_overridesExternalFile(@TempDir Path tempDir) throws IOException {
            Path kwFile = tempDir.resolve("file-keywords.txt");
            Files.writeString(kwFile, "filekeyword\n");
            scorer.keywordsFile = Optional.of(kwFile.toString());
            scorer.keywordsList = Optional.of("inlinekeyword");

            List<String> keywords = scorer.loadKeywords();
            assertThat(keywords).containsExactly("inlinekeyword");
        }

        @Test
        void bundledResourceAbsent_throwsAtStartup() {
            assertThatThrownBy(() -> scorer.loadBundledKeywords("salience/does-not-exist.txt"))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("not found");
        }

    }

    // -------------------------------------------------------------------------
    // Null / empty input — conservative bias
    // -------------------------------------------------------------------------

    @Nested
    class NullAndEmptyInput {

        @BeforeEach
        void initScorer() {
            init(scorer);
        }

        @Test
        void nullText_passesThrough() {
            assertThat(scorer.shouldKeep(null)).isTrue();
        }

        @Test
        void emptyText_passesThrough() {
            assertThat(scorer.shouldKeep("")).isTrue();
        }

        @Test
        void blankText_passesThrough() {
            assertThat(scorer.shouldKeep("   ")).isTrue();
        }
    }

    // -------------------------------------------------------------------------
    // Distribution counters
    // -------------------------------------------------------------------------

    @Nested
    class DistributionCounters {

        @BeforeEach
        void initScorer() {
            init(scorer);
        }

        @Test
        void lowBandCounter_incrementsForFilteredEvents() {
            scorer.shouldKeep("hi");
            assertThat(scorer.bandLow.get()).isEqualTo(1);
            assertThat(scorer.bandMedium.get()).isZero();
            assertThat(scorer.bandHigh.get()).isZero();
        }

        @Test
        void mediumBandCounter_incrementsForMediumScoreEvents() {
            scorer.shouldKeep("no pattern");
            assertThat(scorer.bandMedium.get()).isEqualTo(1);
            assertThat(scorer.bandLow.get()).isZero();
            assertThat(scorer.bandHigh.get()).isZero();
        }

        @Test
        void highBandCounter_incrementsForKeywordEvents() {
            scorer.shouldKeep("bug here");
            assertThat(scorer.bandHigh.get()).isEqualTo(1);
            assertThat(scorer.bandLow.get()).isZero();
            assertThat(scorer.bandMedium.get()).isZero();
        }

        @Test
        void mixedInputs_allBandsTrackedIndependently() {
            scorer.shouldKeep("hi");           // low  (0.1, filtered)
            scorer.shouldKeep("no pattern");   // medium (0.4, kept)
            scorer.shouldKeep("bug here");     // high  (0.8, kept)

            assertThat(scorer.bandLow.get()).isEqualTo(1);
            assertThat(scorer.bandMedium.get()).isEqualTo(1);
            assertThat(scorer.bandHigh.get()).isEqualTo(1);
            assertThat(scorer.eventsFiltered.get()).isEqualTo(1);
            assertThat(scorer.eventsKept.get()).isEqualTo(2);
        }

        @Test
        void distributionSumEqualsTotal() {
            scorer.shouldKeep("hi");
            scorer.shouldKeep("no pattern");
            scorer.shouldKeep("bug here");
            scorer.shouldKeep("What time is it?");   // medium (0.6)

            long total = scorer.eventsFiltered.get() + scorer.eventsKept.get();
            long dist  = scorer.bandLow.get() + scorer.bandMedium.get() + scorer.bandHigh.get();
            assertThat(dist).isEqualTo(total);
        }
    }

    // -------------------------------------------------------------------------
    // Static helper tests
    // -------------------------------------------------------------------------

    @Nested
    class StaticHelpers {

        @Test
        void parseKeywordLines_skipsCommentsAndBlanks() {
            List<String> result = SalienceScorer.parseKeywordLines(
                    List.of("# comment", "", "  Error  ", "BUG", "  "));
            assertThat(result).containsExactly("error", "bug");
        }

        @Test
        void parseKeywordLines_lowercases() {
            List<String> result = SalienceScorer.parseKeywordLines(List.of("DEPLOY", "Test"));
            assertThat(result).containsExactly("deploy", "test");
        }

        @Test
        void parseKeywordLines_deduplicates() {
            List<String> result = SalienceScorer.parseKeywordLines(
                    List.of("error", "Error", "ERROR"));
            assertThat(result).hasSize(1).containsExactly("error");
        }

        @Test
        void compileKeywordPattern_emptyList_returnsNull() {
            assertThat(SalienceScorer.compileKeywordPattern(List.of())).isNull();
        }

        @Test
        void compileKeywordPattern_nonEmpty_matchesKeywordAtWordBoundary() {
            // Behavioural: the compiled pattern must match a keyword standing alone
            // and must NOT match the same letters embedded inside a longer word.
            var p = SalienceScorer.compileKeywordPattern(List.of("test", "error"));
            assertThat(p).isNotNull();
            assertThat(p.matcher("run the test now").find()).isTrue();   // word boundary match
            assertThat(p.matcher("latest build").find()).isFalse();      // "test" embedded — no match
            assertThat(p.matcher("all errors logged").find()).isFalse(); // "error" embedded — no match
        }
    }

    // -------------------------------------------------------------------------
    // Helper: capture JUL WARN log records
    // -------------------------------------------------------------------------

    private static class WarnCapture extends Handler {
        boolean sawWarn = false;

        @Override
        public void publish(LogRecord record) {
            if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
                sawWarn = true;
            }
        }

        @Override public void flush() {}
        @Override public void close() {}
    }
}
