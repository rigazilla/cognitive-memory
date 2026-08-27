package io.github.rigazilla.memory.cognition.event;

import io.github.rigazilla.memory.cognition.config.SalienceScorerConfig;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Config profile override: {@code salience.pattern.*} keys supplied via
 * {@link QuarkusTestProfile#getConfigOverrides()} replace the English defaults.
 */
@QuarkusTest
@TestProfile(SpanishProfileBindingTest.SpanishProfile.class)
class SpanishProfileBindingTest {

    @Inject
    SalienceScorerConfig config;

    @Test
    void spanishTerms_bindViaProfile() {
        assertThat(config.pattern().greetings())
                .contains("hola", "buenos días", "buenas tardes")
                .doesNotContain("hi", "hello", "hey");
        assertThat(config.pattern().acknowledgments())
                .contains("vale", "entendido")
                .doesNotContain("ok", "understood");
        assertThat(config.pattern().farewells())
                .contains("adiós", "hasta luego")
                .doesNotContain("bye", "goodbye");
        assertThat(config.pattern().fillers())
                .contains("pues", "bueno")
                .doesNotContain("um", "hmm");
        assertThat(config.pattern().thanks())
                .contains("gracias", "muchas gracias")
                .doesNotContain("thanks", "thank you");
    }

    public static class SpanishProfile implements QuarkusTestProfile {
        @Override
        public Map<String, String> getConfigOverrides() {
            return Map.of(
                    "salience.pattern.greetings",      "hola,buenos días,buenas tardes",
                    "salience.pattern.acknowledgments", "vale,entendido",
                    "salience.pattern.farewells",       "adiós,hasta luego",
                    "salience.pattern.fillers",         "pues,bueno",
                    "salience.pattern.thanks",          "gracias,muchas gracias"
            );
        }
    }
}
