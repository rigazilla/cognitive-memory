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
 * External properties file override via {@code smallrye.config.locations}.
 * Loads {@code salience-es-test.properties} from the test classpath.
 */
@QuarkusTest
@TestProfile(SpanishConfigLocationsBindingTest.SpanishConfigLocationsProfile.class)
class SpanishConfigLocationsBindingTest {

    @Inject
    SalienceScorerConfig config;

    @Test
    void spanishTerms_bindFromExternalFile() {
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

    public static class SpanishConfigLocationsProfile implements QuarkusTestProfile {
        @Override
        public Map<String, String> getConfigOverrides() {
            var resource = SpanishConfigLocationsProfile.class
                    .getClassLoader().getResource("salience-es-test.properties");
            assertThat(resource)
                    .as("salience-es-test.properties must be on test classpath")
                    .isNotNull();
            return Map.of("smallrye.config.locations", resource.toString());
        }
    }
}
