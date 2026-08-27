package io.github.rigazilla.memory.cognition.event;

import io.github.rigazilla.memory.cognition.config.SalienceScorerConfig;
import io.smallrye.config.PropertiesConfigSource;
import io.smallrye.config.SmallRyeConfig;
import io.smallrye.config.SmallRyeConfigBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tier 2 — Scorer behaviour via {@link SmallRyeConfigBuilder} (plain JUnit, no CDI).
 *
 * <p>Builds a {@link SalienceScorerConfig} entirely in-process using a
 * {@link PropertiesConfigSource} carrying the desired overrides, wires it into a fresh
 * {@link SalienceScorer}, calls {@code init()} directly, then asserts scoring behaviour.
 * No Quarkus application is started; gRPC is never involved.
 *
 * <p>Tier 1 (full CDI/Quarkus container) tests live in separate top-level files:
 * {@link DefaultBindingTest}, {@link SpanishProfileBindingTest},
 * {@link SpanishConfigLocationsBindingTest}.
 */
class SalienceScorerConfigBindingTest {

    @Nested
    class ScorerBehaviourTest {

        private SalienceScorer buildScorer(Map<String, String> overrides) {
            Properties props = new Properties();
            props.putAll(overrides);

            // @WithDefault annotations on the interface supply all defaults;
            // PropertiesConfigSource carries only the per-test overrides.
            SmallRyeConfig cfg = new SmallRyeConfigBuilder()
                    .withMapping(SalienceScorerConfig.class)
                    .withSources(new PropertiesConfigSource(props, "test-overrides", 300))
                    .build();

            SalienceScorerConfig salienceConfig = cfg.getConfigMapping(SalienceScorerConfig.class);
            SalienceScorer scorer = new SalienceScorer(salienceConfig, new KeywordLoader(salienceConfig));
            scorer.init();
            return scorer;
        }

        @Test
        void accentedTerm_isCaseInsensitive_unicodeAware() {
            // Verifies that Pattern.UNICODE_CASE is active: "Buenos Días" (mixed-case, accented)
            // must match the lowercase term "buenos días" configured in the pattern.
            // CASE_INSENSITIVE alone (ASCII-only folding) would NOT fold the accented 'í'.
            SalienceScorer scorer = buildScorer(Map.of(
                    "salience.pattern.greetings", "buenos días"
            ));

            assertThat(scorer.shouldKeep("buenos días")).isFalse();   // exact match — filtered
            assertThat(scorer.shouldKeep("Buenos Días")).isFalse();   // mixed case, accented — filtered (UNICODE_CASE)
            assertThat(scorer.shouldKeep("BUENOS DÍAS")).isFalse();   // upper case, accented — filtered (UNICODE_CASE)
        }

        @Test
        void spanishGreeting_isFiltered_andEnglishDefaultAbsentFromPattern_isKept() {
            // "good morning" is 12 chars (> minLength=10) and is in the English default
            // greeting list but absent from the Spanish override — so with the Spanish
            // override active it falls through to the length rule and scores 0.4 (kept).
            SalienceScorer scorer = buildScorer(Map.of(
                    "salience.pattern.greetings", "hola,buenos días,buenas tardes"
            ));

            assertThat(scorer.shouldKeep("hola")).isFalse();          // Spanish greeting — filtered
            assertThat(scorer.shouldKeep("buenos días")).isFalse();   // Spanish greeting — filtered
            assertThat(scorer.shouldKeep("good morning")).isTrue();   // English default, absent from Spanish override — kept
        }

        @Test
        void spanishGreeting_viaConfigLocations_isFiltered() {
            // Same assertion exercised via the SmallRye config source approach,
            // mirroring what smallrye.config.locations does at runtime.
            SalienceScorer scorer = buildScorer(Map.of(
                    "salience.pattern.greetings",      "hola,buenos días,buenas tardes",
                    "salience.pattern.acknowledgments", "vale,entendido",
                    "salience.pattern.farewells",       "adiós,hasta luego",
                    "salience.pattern.fillers",         "pues,bueno",
                    "salience.pattern.thanks",          "gracias,muchas gracias"
            ));

            assertThat(scorer.shouldKeep("hola")).isFalse();
            assertThat(scorer.shouldKeep("buenos días")).isFalse();
            assertThat(scorer.shouldKeep("good morning")).isTrue();  // English — absent from Spanish override
            assertThat(scorer.shouldKeep("vale")).isFalse();         // Spanish ack
            assertThat(scorer.shouldKeep("adiós")).isFalse();        // Spanish farewell
            assertThat(scorer.shouldKeep("gracias")).isFalse();      // Spanish thanks
        }

        @Test
        void metricsEnabled_default_isTrue() {
            SalienceScorer scorer = buildScorer(Map.of());
            // Exercises that salience.metrics.enabled binds correctly in SmallRye
            // (the @WithName change). shouldKeep increments counters only when enabled.
            // "hola" scores 0.1 (below minLength=10) → filtered; "deploy the system" hits keyword → kept.
            scorer.shouldKeep("hola");
            scorer.shouldKeep("deploy the system");
            assertThat(scorer.eventsFiltered.get()).isEqualTo(1);
            assertThat(scorer.eventsKept.get()).isEqualTo(1);
        }

        @Test
        void metricsEnabled_false_suppressesCounters() {
            SalienceScorer scorer = buildScorer(Map.of("salience.metrics.enabled", "false"));
            scorer.shouldKeep("hola");
            scorer.shouldKeep("deploy the system");
            assertThat(scorer.eventsFiltered.get()).isZero();
            assertThat(scorer.eventsKept.get()).isZero();
        }
    }
}
