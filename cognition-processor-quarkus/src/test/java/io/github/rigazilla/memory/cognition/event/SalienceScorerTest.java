package io.github.rigazilla.memory.cognition.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SalienceScorerTest {

    private SalienceScorer scorer;
    private SalienceScorerConfigStub salienceConfig;

    @BeforeEach
    void setUp() {
        salienceConfig = new SalienceScorerConfigStub();
        scorer = initialised(salienceConfig);
    }

    /**
     * Constructs and fully initialises a {@link SalienceScorer} from {@code config}.
     * Calling {@code init()} here mirrors what CDI's {@code @PostConstruct} does in production,
     * without requiring a container.
     */
    private static SalienceScorer initialised(SalienceScorerConfigStub config) {
        SalienceScorer s = new SalienceScorer(config, new KeywordLoader(config));
        s.init();
        return s;
    }

    // -------------------------------------------------------------------------
    // Pattern groups — each must be filtered (score ≤ 0.3 → shouldKeep false)
    // -------------------------------------------------------------------------

    @Nested
    class PatternGroups {

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

        @Test
        void greeting_withSubstantiveContent_isKept() {
            assertThat(scorer.shouldKeep("hi, deploy the server to prod")).isTrue();
        }

        @Test
        void acknowledgment_withSubstantiveContent_isKept() {
            assertThat(scorer.shouldKeep("ok, but the build is failing on main")).isTrue();
        }
    }

    // -------------------------------------------------------------------------
    // Word boundary guards
    // -------------------------------------------------------------------------

    @Nested
    class WordBoundaryGuards {

        @Test
        void test_insideLatest_doesNotElevateScore() {
            assertThat(scorer.score("latest build status")).isEqualTo(0.4);
            assertThat(scorer.shouldKeep("latest build status")).isTrue();
        }

        @Test
        void like_asPreferenceKeyword_isKept() {
            assertThat(scorer.score("I like dark mode")).isEqualTo(0.8);
            assertThat(scorer.shouldKeep("I like dark mode")).isTrue();
        }

        @Test
        void code_insideCodebase_doesNotMatch() {
            assertThat(scorer.score("check the codebase")).isEqualTo(0.4);
        }

        @Test
        void error_insideErrors_doesNotMatch() {
            assertThat(scorer.score("all errors logged")).isEqualTo(0.4);
        }
    }

    // -------------------------------------------------------------------------
    // Recall guards — keyword check precedes min-length so short keyword-bearing
    // messages are not dropped by the length rule
    // -------------------------------------------------------------------------

    @Nested
    class RecallGuards {

        @Test
        void bugHere_veryShortKeywordMessage_isKept() {
            assertThat(scorer.score("bug here")).isEqualTo(0.8);
            assertThat(scorer.shouldKeep("bug here")).isTrue();
        }

        @Test
        void prodDown_isKept() {
            assertThat(scorer.score("prod down")).isEqualTo(0.8);
            assertThat(scorer.shouldKeep("prod down")).isTrue();
        }

        @Test
        void rollback_isKept() {
            assertThat(scorer.score("rollback!")).isEqualTo(0.8);
            assertThat(scorer.shouldKeep("rollback!")).isTrue();
        }
    }

    // -------------------------------------------------------------------------
    // Threshold boundary
    // -------------------------------------------------------------------------

    @Nested
    class ThresholdBoundary {

        @Test
        void scoreOf_0_4_isKept_defaultThreshold() {
            double s = scorer.score("no pattern");
            assertThat(s).isEqualTo(0.4);
            assertThat(scorer.shouldKeep("no pattern")).isTrue();
        }

        @Test
        void customThreshold_0_8_filtersKeywordScore() {
            salienceConfig.threshold = 0.8;
            scorer = initialised(salienceConfig);
            assertThat(scorer.shouldKeep("bug here")).isFalse();
        }

        @Test
        void scoreEqualToThreshold_isFiltered() {
            salienceConfig.threshold = 0.4;
            scorer = initialised(salienceConfig);
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

        @Test
        void question_scores_0_6() {
            assertThat(scorer.score("What time is it?")).isEqualTo(0.6);
            assertThat(scorer.shouldKeep("What time is it?")).isTrue();
        }

        @Test
        void commandRequest_showMeTheLogs_scores_0_4() {
            assertThat(scorer.score("Show me the logs")).isEqualTo(0.4);
            assertThat(scorer.shouldKeep("Show me the logs")).isTrue();
        }

        @Test
        void statement_over20chars_scores_0_5() {
            assertThat(scorer.score("check the recent output")).isEqualTo(0.5);
            assertThat(scorer.shouldKeep("check the recent output")).isTrue();
        }

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
            String noKw = "The application was restarted and seems stable again";
            assertThat(noKw.length()).isGreaterThan(50);
            assertThat(scorer.score(noKw)).isEqualTo(0.9);
            assertThat(scorer.shouldKeep(noKw)).isTrue();
        }

        @Test
        void longQuestion_over50chars_scores_0_9_notQuestion() {
            String longQ = "Is the deployment pipeline broken for the staging environment right now?";
            assertThat(longQ.length()).isGreaterThan(50);
            assertThat(scorer.score(longQ)).isEqualTo(0.9);
            assertThat(scorer.shouldKeep(longQ)).isTrue();
        }
    }

    // -------------------------------------------------------------------------
    // Config behaviour — each test builds a fresh scorer with the mutated config
    // -------------------------------------------------------------------------

    @Nested
    class ConfigBehaviour {

        @Test
        void emptyConfig_allDefaultsActive_bundledKeywordsLoaded() {
            assertThat(scorer.shouldKeep("deploy now")).isTrue();
            assertThat(scorer.shouldKeep("hi")).isFalse();
        }

        @Test
        void disabled_everythingPassesThrough() {
            salienceConfig.enabled = false;
            scorer = initialised(salienceConfig);
            assertThat(scorer.shouldKeep("hi")).isTrue();
            assertThat(scorer.shouldKeep("ok")).isTrue();
            assertThat(scorer.shouldKeep(null)).isTrue();
        }

        @Test
        void fillerDisabled_fillerNoLongerFilters() {
            salienceConfig.pattern.fillerEnabled = false;
            scorer = initialised(salienceConfig);
            assertThat(scorer.shouldKeep("well that works")).isTrue();
        }

        @Test
        void greetingDisabled_greetingNoLongerFilters() {
            salienceConfig.pattern.greetingEnabled = false;
            scorer = initialised(salienceConfig);
            assertThat(scorer.shouldKeep("good morning")).isTrue();
        }

        @Test
        void acknowledgmentDisabled_ackNoLongerFilters() {
            salienceConfig.pattern.acknowledgmentEnabled = false;
            scorer = initialised(salienceConfig);
            assertThat(scorer.shouldKeep("understood")).isTrue();
        }

        @Test
        void farewellDisabled_farewellNoLongerFilters() {
            salienceConfig.pattern.farewellEnabled = false;
            scorer = initialised(salienceConfig);
            assertThat(scorer.shouldKeep("see you later")).isTrue();
        }

        @Test
        void thanksDisabled_thanksNoLongerFilters() {
            salienceConfig.pattern.thanksEnabled = false;
            scorer = initialised(salienceConfig);
            assertThat(scorer.shouldKeep("thanks a lot")).isTrue();
        }

        @Test
        void customKeywordList_excludingBug_keywordMessageScoresLower() {
            salienceConfig.keywords.list = Optional.of(List.of("outage", "incident"));
            scorer = initialised(salienceConfig);
            assertThat(scorer.shouldKeep("bug here")).isFalse();
        }

        @Test
        void metricsDisabled_countersDoNotIncrement() {
            salienceConfig.metricsEnabled = false;
            scorer = initialised(salienceConfig);
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
            salienceConfig.minLength = 20;
            scorer = initialised(salienceConfig);
            assertThat(scorer.score("no pattern")).isEqualTo(0.1);
            assertThat(scorer.shouldKeep("no pattern")).isFalse();
        }
    }

    // -------------------------------------------------------------------------
    // Null / empty input — conservative bias
    // -------------------------------------------------------------------------

    @Nested
    class NullAndEmptyInput {

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
            scorer.shouldKeep("hi");
            scorer.shouldKeep("no pattern");
            scorer.shouldKeep("bug here");

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
            scorer.shouldKeep("What time is it?");

            long total = scorer.eventsFiltered.get() + scorer.eventsKept.get();
            long dist  = scorer.bandLow.get() + scorer.bandMedium.get() + scorer.bandHigh.get();
            assertThat(dist).isEqualTo(total);
        }
    }
}
