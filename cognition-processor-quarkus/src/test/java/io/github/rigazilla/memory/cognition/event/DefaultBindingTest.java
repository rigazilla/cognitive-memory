package io.github.rigazilla.memory.cognition.event;

import io.github.rigazilla.memory.cognition.config.SalienceScorerConfig;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Default config binding: all resolved property keys match pre-PR names and defaults.
 * Specifically verifies that {@code salience.metrics.enabled} (dot-separated)
 * still binds after the {@code @WithName("metrics.enabled")} change — without
 * {@code @WithName}, kebab-case would have produced {@code salience.metrics-enabled}.
 *
 * <p>Also injects {@link SalienceScorer} directly to prove that {@code @PostConstruct}
 * ran and the patterns were compiled under CDI wiring.
 */
@QuarkusTest
class DefaultBindingTest {

    @Inject
    SalienceScorerConfig config;

    @Inject
    SalienceScorer scorer;

    @Test
    void scorer_defaultPatterns_compiledUnderCdi() {
        // "good morning" is 12 chars (> minLength=10) and in the default greeting list.
        // Without @PostConstruct, greetingPattern is null → scores 0.4 (floor) → kept.
        // With @PostConstruct, it matches the greeting pattern → scores 0.1 → filtered.
        // This proves @PostConstruct ran and patterns compiled under CDI wiring.
        assertThat(scorer.shouldKeep("good morning")).isFalse();
    }

    @Test
    void allResolvedKeysMatchPrePrDefaults() {
        assertThat(config.enabled()).isTrue();           // salience.enabled
        assertThat(config.threshold()).isEqualTo(0.3);  // salience.threshold
        assertThat(config.minLength()).isEqualTo(10);   // salience.min-length

        assertThat(config.pattern().greetingEnabled()).isTrue();       // salience.pattern.greeting-enabled
        assertThat(config.pattern().acknowledgmentEnabled()).isTrue(); // salience.pattern.acknowledgment-enabled
        assertThat(config.pattern().farewellEnabled()).isTrue();       // salience.pattern.farewell-enabled
        assertThat(config.pattern().fillerEnabled()).isTrue();         // salience.pattern.filler-enabled
        assertThat(config.pattern().thanksEnabled()).isTrue();         // salience.pattern.thanks-enabled

        assertThat(config.pattern().greetings()).contains("hi", "hello", "hey");
        assertThat(config.pattern().acknowledgments()).contains("ok", "understood");
        assertThat(config.pattern().farewells()).contains("bye", "goodbye");
        assertThat(config.pattern().fillers()).contains("um", "hmm");
        assertThat(config.pattern().thanks()).contains("thanks", "thank you");

        assertThat(config.keywords().list()).isEmpty();  // salience.keywords.list — absent by default
        assertThat(config.keywords().file()).isEmpty();  // salience.keywords.file — absent by default
    }

    @Test
    void metricsEnabled_dotKey_bindsCorrectly() {
        // salience.metrics.enabled (dot-separated) must bind via @WithName("metrics.enabled").
        // Without @WithName kebab-case produces salience.metrics-enabled — a different key.
        assertThat(config.metricsEnabled()).isTrue(); // salience.metrics.enabled
    }
}
