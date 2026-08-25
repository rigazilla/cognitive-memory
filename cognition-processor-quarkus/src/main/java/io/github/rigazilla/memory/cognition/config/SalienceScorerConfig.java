package io.github.rigazilla.memory.cognition.config;

import java.util.List;
import java.util.Optional;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

/**
 * Configuration mapping for {@link io.github.rigazilla.memory.cognition.event.SalienceScorer}.
 *
 * <p>All properties are optional with built-in defaults, so existing deployments require
 * no configuration changes. English behaviour is identical to pre-PR: the built-in
 * {@code @WithDefault} values ship exactly the same English terms as before, and no
 * code change is needed to reach other languages — supply overrides via any SmallRye
 * config source (see README for worked examples).
 *
 * <p><strong>Override semantics:</strong> supplying a term-list property <em>replaces</em>
 * the built-in English defaults entirely — it does not append. A multilingual deployment
 * must list every term it wants matched, including any English terms it wishes to retain.
 *
 * <p>Method names follow camelCase and are automatically mapped to kebab-case
 * property keys (Quarkus default naming strategy), e.g.:
 * <ul>
 *   <li>{@code minLength()} → {@code salience.min-length}</li>
 *   <li>{@code pattern().greetingEnabled()} → {@code salience.pattern.greeting-enabled}</li>
 *   <li>{@code pattern().greetings()} → {@code salience.pattern.greetings}</li>
 * </ul>
 *
 * <p>Example {@code application.properties} overrides:
 * <pre>
 * salience.threshold=0.4
 * salience.keywords.list=bug,deploy,rollback
 * salience.pattern.greetings=hi,hello,bonjour,hola,ciao
 * </pre>
 */
@ConfigMapping(prefix = "salience")
public interface SalienceScorerConfig {

    /** Master switch: set to {@code false} to pass all events through unscored. */
    @WithDefault("true")
    boolean enabled();

    /**
     * Score threshold. Events whose score is &le; this value are filtered;
     * score must strictly exceed this value to be kept.
     * Range: 0.0&ndash;1.0.
     */
    @WithDefault("0.3")
    double threshold();

    /**
     * Minimum message length in characters. Messages shorter than this score 0.1 and are dropped.
     */
    @WithDefault("10")
    int minLength();

    /**
     * Whether salience distribution counters are tracked and exposed.
     * Key is {@code salience.metrics.enabled} — dot-separated, preserved by
     * {@code @WithName} because kebab-case would otherwise produce
     * {@code salience.metrics-enabled}. Default: {@code true}.
     */
    @WithName("metrics.enabled")
    @WithDefault("true")
    boolean metricsEnabled();

    Pattern pattern();

    Keywords keywords();

    interface Pattern {

        /** Enable the greeting pattern check. */
        @WithDefault("true")
        boolean greetingEnabled();

        /**
         * Greeting terms — compiled into an anchored, case-insensitive, Unicode-aware pattern.
         * Supplying this property <strong>replaces</strong> the built-in English defaults;
         * list every term you want matched, including any English terms to retain.
         * Override via any SmallRye config source (see class javadoc and README).
         */
        @WithDefault("hi,hello,hey,greetings,good morning,good afternoon,good evening")
        List<String> greetings();

        /** Enable the acknowledgment pattern check. */
        @WithDefault("true")
        boolean acknowledgmentEnabled();

        /**
         * Acknowledgment terms — replaces built-in English defaults when supplied;
         * list all desired terms. Override mechanism same as {@link #greetings()}.
         */
        @WithDefault("ok,okay,sure,got it,understood,sounds good,makes sense,alright")
        List<String> acknowledgments();

        /** Enable the farewell pattern check. */
        @WithDefault("true")
        boolean farewellEnabled();

        /**
         * Farewell terms — replaces built-in English defaults when supplied;
         * list all desired terms. Override mechanism same as {@link #greetings()}.
         */
        @WithDefault("bye,goodbye,see you,talk later,take care,have a good day")
        List<String> farewells();

        /** Enable the filler-word pattern check. */
        @WithDefault("true")
        boolean fillerEnabled();

        /**
         * Filler terms — replaces built-in English defaults when supplied;
         * list all desired terms. Override mechanism same as {@link #greetings()}.
         */
        @WithDefault("um,uh,hmm,well,so,like")
        List<String> fillers();

        /** Enable the thanks pattern check. */
        @WithDefault("true")
        boolean thanksEnabled();

        /**
         * Thanks terms — replaces built-in English defaults when supplied;
         * list all desired terms. Override mechanism same as {@link #greetings()}.
         */
        @WithDefault("thanks,thank you,thx,ty")
        List<String> thanks();
    }

    interface Keywords {

        /**
         * Highest-priority keyword override: comma-separated list.
         * When present, overrides both {@link #file()} and the bundled defaults.
         * Optional; absent by default.
         */
        Optional<List<String>> list();

        /**
         * Path to an external keyword file (one keyword per line, {@code #} comments
         * and blank lines ignored). When set, used instead of the bundled
         * {@code salience/default-keywords.txt}. If the file is missing or unreadable,
         * a WARN is logged and bundled defaults are used.
         * Optional; absent by default.
         */
        Optional<String> file();
    }
}
