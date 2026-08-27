package io.github.rigazilla.memory.cognition.event;

import io.github.rigazilla.memory.cognition.config.SalienceScorerConfig;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

/**
 * Pattern-based salience scorer for conversation events.
 * Scores message text 0.0–1.0 without any LLM calls.
 *
 * <p>Low-salience events (score ≤ threshold) are filtered before window insertion,
 * reducing unnecessary LLM extraction calls (estimated reduction varies by workload).
 *
 * <p>Scoring rules:
 * <ul>
 *   <li>0.0–0.3 (filter): very short, greetings, acknowledgments, farewells, fillers, thanks</li>
 *   <li>0.4–0.6 (keep): questions, statements &gt; 20 chars</li>
 *   <li>0.7–1.0 (keep): technical/preference/decision/procedure keywords, messages &gt; 50 chars</li>
 * </ul>
 *
 * <p><strong>Scoring precedence:</strong> rules are evaluated in order: named low-salience
 * patterns (greeting, ack, farewell, filler, thanks) → keyword match → length rules.
 * Length rules are evaluated longest-first: a message longer than {@code LENGTH_LONG} (50 chars)
 * scores {@code SCORE_LONG} (0.9) regardless of whether it also contains a question mark.
 * A long question therefore scores 0.9, not 0.6. This is intentional: a lengthy question
 * carries substantive content worth remembering irrespective of its syntactic form. The effect
 * on the salience distribution counters is that long questions are recorded in the high band,
 * not the medium band.
 *
 * <p>Conservative bias: null/empty text always passes through {@link #shouldKeep}.
 * {@link #score} returns 0.0 for null/empty to signal "no extractable content";
 * {@link #shouldKeep} overrides that with a pass-through before scoring, so
 * unscoreable input is never dropped.
 *
 * <p>English terms are the built-in defaults. Any language is supported by
 * configuration: override {@code salience.pattern.*} via any SmallRye config source.
 */
@ApplicationScoped
public class SalienceScorer {

    private static final Logger LOG = Logger.getLogger(SalienceScorer.class);

    // Score constants — one name per rule, values are independent tuning knobs.
    private static final double SCORE_GREETING  = 0.1;  // standalone greeting
    private static final double SCORE_ACK       = 0.2;  // acknowledgment
    private static final double SCORE_FAREWELL  = 0.2;  // farewell
    private static final double SCORE_FILLER    = 0.1;  // filler word
    private static final double SCORE_THANKS    = 0.2;  // thanks
    private static final double SCORE_SHORT     = 0.1;  // message below min-length threshold
    private static final double SCORE_KEYWORD   = 0.8;  // contains a high-salience keyword
    private static final double SCORE_LONG      = 0.9;  // message longer than LENGTH_LONG chars
    private static final double SCORE_QUESTION  = 0.6;  // contains '?'
    private static final double SCORE_STATEMENT = 0.5;  // 20–50 chars, no keyword, no '?'
    private static final double SCORE_FLOOR     = 0.4;  // 10–20 chars, no keyword, no '?'

    // Band boundaries — shared between scoring and the distribution counters in shouldKeep().
    private static final double BAND_LOW_MAX    = 0.3;
    private static final double BAND_MEDIUM_MAX = 0.6;

    // Length thresholds for the medium and long scoring rules.
    private static final int LENGTH_MEDIUM = 20;
    private static final int LENGTH_LONG   = 50;

    private final SalienceScorerConfig config;
    private final KeywordLoader keywordLoader;

    public SalienceScorer(SalienceScorerConfig config, KeywordLoader keywordLoader) {
        this.config = config;
        this.keywordLoader = keywordLoader;
    }

    // Compiled once at @PostConstruct; the container guarantees safe publication of Pattern instances
    // via its own synchronisation before any request thread can reach them — plain fields are safe.
    private Pattern greetingPattern;
    private Pattern acknowledgmentPattern;
    private Pattern farewellPattern;
    private Pattern fillerPattern;
    private Pattern thanksPattern;
    private Pattern keywordPattern; // null when the keyword list is empty

    // Config primitives cached at startup to avoid per-event SmallRye Config lookups on the hot path.
    // volatile ensures the @PostConstruct write (container thread) is visible to request-handling threads.
    private volatile boolean enabled;
    private volatile double  threshold;
    private volatile int     minLength;
    private volatile boolean metricsEnabled;
    private volatile boolean greetingEnabled;
    private volatile boolean acknowledgmentEnabled;
    private volatile boolean farewellEnabled;
    private volatile boolean fillerEnabled;
    private volatile boolean thanksEnabled;

    /** Events whose score was ≤ threshold and were dropped (LLM calls avoided). */
    final AtomicLong eventsFiltered = new AtomicLong(0);
    /** Events whose score was &gt; threshold and were passed to the window registry. */
    final AtomicLong eventsKept = new AtomicLong(0);

    /** Events that scored in the low band (0.0–0.3). */
    final AtomicLong bandLow = new AtomicLong(0);
    /** Events that scored in the medium band (0.4–0.6). */
    final AtomicLong bandMedium = new AtomicLong(0);
    /** Events that scored in the high band (0.7–1.0). */
    final AtomicLong bandHigh = new AtomicLong(0);

    @PostConstruct
    void init() {
        // Cache all config values read on the hot path so shouldKeep/scoreInternal
        // never perform a config lookup per scored event.
        enabled              = config.enabled();
        threshold            = config.threshold();
        minLength            = config.minLength();
        metricsEnabled       = config.metricsEnabled();
        SalienceScorerConfig.Pattern p = config.pattern();
        greetingEnabled      = p.greetingEnabled();
        acknowledgmentEnabled = p.acknowledgmentEnabled();
        farewellEnabled      = p.farewellEnabled();
        fillerEnabled        = p.fillerEnabled();
        thanksEnabled        = p.thanksEnabled();

        greetingPattern      = compileTermPattern(p.greetings());
        acknowledgmentPattern = compileTermPattern(p.acknowledgments());
        farewellPattern      = compileTermPattern(p.farewells());
        fillerPattern        = compileTermPattern(p.fillers());
        thanksPattern        = compileTermPattern(p.thanks());

        List<String> keywords = keywordLoader.load();
        keywordPattern = compileKeywordPattern(keywords);
        LOG.infof("SalienceScorer initialised: enabled=%s, threshold=%.2f, keywords=%d",
                enabled, threshold, keywords.size());
    }

    /**
     * Score {@code text} for salience. Returns a value in [0.0, 1.0].
     *
     * <p>Null or blank text scores 0.0 to signal "no extractable content".
     * Do not use this return value alone to decide whether to drop an event;
     * use {@link #shouldKeep} instead, which applies the conservative bias
     * and passes unscoreable input through untouched.
     *
     * @param text message text; may be null
     * @return salience score in [0.0, 1.0]
     */
    public double score(String text) {
        if (text == null || text.isBlank()) {
            return 0.0;
        }
        return scoreInternal(text);
    }

    /**
     * Returns true when the event should be passed to the window registry.
     * When {@code salience.enabled=false} all events pass through.
     *
     * <p>Null, empty, or blank text always passes through regardless of threshold,
     * because the absence of content is not evidence of low salience — it means
     * the payload carried no extractable user text.
     *
     * <p>Threshold boundary is <em>exclusive-keep</em>: the score must strictly
     * exceed {@code salience.threshold} (default 0.3) to be kept.
     *
     * @param text message text; may be null
     * @return true if the event should be processed
     */
    public boolean shouldKeep(String text) {
        if (!enabled) {
            return true;
        }

        // Null/empty/blank: no content to score — conservative bias, always pass through.
        if (text == null || text.isBlank()) {
            return true;
        }

        double s = scoreInternal(text);
        boolean keep = s > threshold;

        if (metricsEnabled) {
            if (keep) {
                eventsKept.incrementAndGet();
            } else {
                eventsFiltered.incrementAndGet();
            }
            if (s <= BAND_LOW_MAX) {
                bandLow.incrementAndGet();
            } else if (s <= BAND_MEDIUM_MAX) {
                bandMedium.incrementAndGet();
            } else {
                bandHigh.incrementAndGet();
            }
        }

        if (!keep) {
            LOG.debugf("Filtered low-salience event (score=%.2f, len=%d): %.57s",
                    s, text.strip().length(), text);
        }

        return keep;
    }

    /** Total events filtered (score ≤ threshold) since startup. Equals LLM calls avoided. */
    public long getEventsFiltered() {
        return eventsFiltered.get();
    }

    /** Total events kept (score &gt; threshold) since startup. */
    public long getEventsKept() {
        return eventsKept.get();
    }

    /** Events that scored in the low salience band (0.0–0.3) since startup. */
    public long getBandLow() {
        return bandLow.get();
    }

    /** Events that scored in the medium salience band (0.4–0.6) since startup. */
    public long getBandMedium() {
        return bandMedium.get();
    }

    /** Events that scored in the high salience band (0.7–1.0) since startup. */
    public long getBandHigh() {
        return bandHigh.get();
    }

    private double scoreInternal(String text) {
        String normalized = text.strip().toLowerCase(Locale.ROOT);

        OptionalDouble patternScore = scorePatternMatch(normalized);
        if (patternScore.isPresent()) {
            return patternScore.getAsDouble();
        }

        // High salience: keyword match — evaluated before length check so that short
        // but important messages like "bug here" (8 chars) are not filtered by length.
        if (matchesKeyword(normalized)) {
            return SCORE_KEYWORD;
        }

        return scoreLengthAndContent(normalized);
    }

    /**
     * Checks all named low-salience patterns against {@code normalized}.
     * Returns the pattern score when matched, or an empty {@link OptionalDouble} when
     * no pattern matched. Patterns are anchored at both ends so partial matches are not possible.
     */
    private OptionalDouble scorePatternMatch(String normalized) {
        if (greetingEnabled && matches(greetingPattern, normalized)) {
            return OptionalDouble.of(SCORE_GREETING);
        }
        if (acknowledgmentEnabled && matches(acknowledgmentPattern, normalized)) {
            return OptionalDouble.of(SCORE_ACK);
        }
        if (farewellEnabled && matches(farewellPattern, normalized)) {
            return OptionalDouble.of(SCORE_FAREWELL);
        }
        if (fillerEnabled && matches(fillerPattern, normalized)) {
            return OptionalDouble.of(SCORE_FILLER);
        }
        if (thanksEnabled && matches(thanksPattern, normalized)) {
            return OptionalDouble.of(SCORE_THANKS);
        }
        return OptionalDouble.empty();
    }

    /**
     * Length and content-based scoring for messages that matched no named pattern
     * and no keyword. Returns a score in the low (0.1), medium (0.4–0.6), or
     * high (0.9) band.
     */
    private double scoreLengthAndContent(String normalized) {
        if (normalized.length() < minLength) {
            return SCORE_SHORT;
        }
        if (normalized.length() > LENGTH_LONG) {
            return SCORE_LONG;
        }
        if (normalized.contains("?")) {
            return SCORE_QUESTION;
        }
        if (normalized.length() > LENGTH_MEDIUM) {
            return SCORE_STATEMENT;
        }
        return SCORE_FLOOR;
    }

    private static boolean matches(Pattern pattern, String normalized) {
        return pattern != null && pattern.matcher(normalized).matches();
    }

    private boolean matchesKeyword(String normalized) {
        return keywordPattern != null && keywordPattern.matcher(normalized).find();
    }

    /**
     * Compiles terms into an anchored alternation: {@code ^(term1|term2|…)[\s!.]*$}.
     * Returns {@code null} when the list is empty; callers must null-check before use.
     *
     * <p>Anchoring prevents partial matches — "hi, deploy the server" must not score as
     * a greeting. {@code CASE_INSENSITIVE | UNICODE_CASE} folds accented characters
     * correctly; {@code CASE_INSENSITIVE} alone is ASCII-only and would fail non-Latin
     * overrides (e.g. "Buenos Días" would not match "buenos días").
     */
    private static Pattern compileTermPattern(List<String> terms) {
        if (terms == null || terms.isEmpty()) {
            return null;
        }
        String alternation = terms.stream()
                .map(t -> Pattern.quote(t.strip()))
                .collect(joining("|"));
        return Pattern.compile("^(" + alternation + ")[\\s!.]*$",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    /**
     * Compiles {@code keywords} into a single word-boundary alternation pattern.
     * Returns {@code null} when the list is empty; callers must null-check before use.
     *
     * <p>Keywords are stored lowercase (see {@link KeywordLoader#parseKeywordLines}) and matched
     * against lowercased input (see {@link #matchesKeyword}), so {@code CASE_INSENSITIVE} is not
     * needed and is intentionally omitted. Relying on the flag alone would silently break non-ASCII
     * input because {@code CASE_INSENSITIVE} applies ASCII folding only without {@code UNICODE_CASE}.
     */
    private static Pattern compileKeywordPattern(List<String> keywords) {
        if (keywords.isEmpty()) {
            return null;
        }
        String pattern = keywords.stream()
                .map(kw -> "\\b" + Pattern.quote(kw) + "\\b")
                .collect(joining("|"));
        return Pattern.compile(pattern);
    }
}
