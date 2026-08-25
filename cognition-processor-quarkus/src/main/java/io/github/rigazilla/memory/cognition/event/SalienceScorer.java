package io.github.rigazilla.memory.cognition.event;

import io.github.rigazilla.memory.cognition.config.SalienceScorerConfig;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
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

    private static final String BUNDLED_KEYWORDS = "salience/default-keywords.txt";

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

    /** No-arg constructor required by CDI for proxy creation. Not for direct use. */
    SalienceScorer() {
        this.config = null;
    }

    @Inject
    public SalienceScorer(SalienceScorerConfig config) {
        this.config = config;
    }

    private volatile Pattern greetingPattern;
    private volatile Pattern acknowledgmentPattern;
    private volatile Pattern farewellPattern;
    private volatile Pattern fillerPattern;
    private volatile Pattern thanksPattern;

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

    private volatile Pattern keywordPattern; // null when the keyword list is empty

    @PostConstruct
    void init() {
        SalienceScorerConfig.Pattern p = config.pattern();
        greetingPattern      = compileTermPattern(p.greetings());
        acknowledgmentPattern = compileTermPattern(p.acknowledgments());
        farewellPattern      = compileTermPattern(p.farewells());
        fillerPattern        = compileTermPattern(p.fillers());
        thanksPattern        = compileTermPattern(p.thanks());

        List<String> keywords = loadKeywords();
        keywordPattern = compileKeywordPattern(keywords);
        LOG.infof("SalienceScorer initialised: enabled=%s, threshold=%.2f, keywords=%d",
                config.enabled(), config.threshold(), keywords.size());
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
        if (!config.enabled()) {
            return true;
        }

        // Null/empty/blank: no content to score — conservative bias, always pass through.
        if (text == null || text.isBlank()) {
            return true;
        }

        double s = scoreInternal(text);
        boolean keep = s > config.threshold();

        if (config.metricsEnabled()) {
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

    // Sentinel: returned by scorePatternMatch when no named pattern matched.
    private static final double NO_MATCH = -1.0;

    private double scoreInternal(String text) {
        String normalized = text.strip().toLowerCase(Locale.ROOT);

        double patternScore = scorePatternMatch(normalized);
        if (patternScore != NO_MATCH) {
            return patternScore;
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
     * Returns the pattern score when matched, or {@link #NO_MATCH} when no pattern matched.
     * Patterns are anchored at both ends so partial matches are not possible.
     */
    private double scorePatternMatch(String normalized) {
        SalienceScorerConfig.Pattern p = config.pattern();
        if (p.greetingEnabled() && matches(greetingPattern, normalized)) {
            return SCORE_GREETING;
        }
        if (p.acknowledgmentEnabled() && matches(acknowledgmentPattern, normalized)) {
            return SCORE_ACK;
        }
        if (p.farewellEnabled() && matches(farewellPattern, normalized)) {
            return SCORE_FAREWELL;
        }
        if (p.fillerEnabled() && matches(fillerPattern, normalized)) {
            return SCORE_FILLER;
        }
        if (p.thanksEnabled() && matches(thanksPattern, normalized)) {
            return SCORE_THANKS;
        }
        return NO_MATCH;
    }

    /**
     * Length and content-based scoring for messages that matched no named pattern
     * and no keyword. Returns a score in the low (0.1), medium (0.4–0.6), or
     * high (0.9) band.
     */
    private double scoreLengthAndContent(String normalized) {
        if (normalized.length() < config.minLength()) {
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
     * Loads keywords using the priority order:
     * <ol>
     *   <li>{@code salience.keywords.list} — inline comma-separated config (highest priority)</li>
     *   <li>{@code salience.keywords.file} — path to an external file</li>
     *   <li>Bundled classpath resource {@code salience/default-keywords.txt}</li>
     * </ol>
     *
     * <p><strong>Empty / comments-only override behaviour:</strong> when an external source
     * ({@code salience.keywords.list} or {@code salience.keywords.file}) is present but parses
     * to zero effective keywords (empty file, file containing only blank lines and {@code #}
     * comments, or a list value that trims to nothing), a WARN is logged and the service falls
     * back to the bundled defaults. This asymmetry with the bundled-resource path (which aborts
     * startup on an empty result) is intentional: an empty external override is likely a
     * misconfiguration that the operator should correct, but it is not a broken build artifact;
     * continuing with bundled defaults preserves recall at the cost of losing the intended
     * customisation. Use {@code salience.keywords.enabled=false} to intentionally disable
     * keyword matching.
     */
    List<String> loadKeywords() {
        if (config.keywords().list().isPresent()) {
            List<String> result = parseKeywordLines(config.keywords().list().get());
            if (result.isEmpty()) {
                LOG.warnf("SalienceScorer: salience.keywords.list parsed to zero keywords"
                        + " (empty or comments-only) — falling back to bundled defaults");
                return loadBundledKeywords(BUNDLED_KEYWORDS);
            }
            LOG.debugf("SalienceScorer: loaded %d keywords from salience.keywords.list", result.size());
            return result;
        }

        if (config.keywords().file().isPresent()) {
            String path = config.keywords().file().get();
            try {
                String content = Files.readString(Path.of(path), StandardCharsets.UTF_8);
                List<String> result = parseKeywordLines(content.lines().toList());
                if (result.isEmpty()) {
                    LOG.warnf("SalienceScorer: salience.keywords.file '%s' parsed to zero keywords"
                            + " (empty or comments-only) — falling back to bundled defaults", path);
                    return loadBundledKeywords(BUNDLED_KEYWORDS);
                }
                LOG.infof("SalienceScorer: loaded %d keywords from file: %s", result.size(), path);
                return result;
            } catch (Exception e) {
                LOG.warnf("SalienceScorer: could not read salience.keywords.file '%s' (%s)"
                        + " — falling back to bundled defaults",
                        path, e.getMessage());
                return loadBundledKeywords(BUNDLED_KEYWORDS);
            }
        }

        return loadBundledKeywords(BUNDLED_KEYWORDS);
    }

    /**
     * Loads keywords from the given classpath resource path. Throws {@link IllegalStateException}
     * on any failure — absent resource, read error, or empty result — because a running service
     * with no keywords silently violates the high-recall acceptance criterion.
     *
     * <p>{@code catch (Exception)} is intentionally broad: it ensures every failure path —
     * including {@code IOException} from {@code readAllBytes}, {@code SecurityException} from
     * {@code getResourceAsStream}, and any bug in {@code parseKeywordLines} — carries the same
     * diagnostic message (resource path + "broken build or missing
     * quarkus.native.resources.includes") rather than escaping unwrapped.
     */
    List<String> loadBundledKeywords(String resourcePath) {
        try (var is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalStateException(
                        "Bundled resource '" + resourcePath + "' not found"
                        + " — broken build or missing quarkus.native.resources.includes");
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            List<String> result = parseKeywordLines(content.lines().toList());
            if (result.isEmpty()) {
                throw new IllegalStateException(
                        "Bundled resource '" + resourcePath + "' parsed to zero keywords"
                        + " — file may contain only comments or be empty");
            }
            LOG.debugf("SalienceScorer: loaded %d keywords from bundled defaults", result.size());
            return result;
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to read bundled resource '" + resourcePath + "'"
                    + " — broken build or missing quarkus.native.resources.includes", e);
        }
    }

    /**
     * Lowercases with {@code Locale.ROOT} (language-neutral, avoids Turkish-I surprises),
     * trims, and removes blank lines and {@code #} comments. Returns an unmodifiable list.
     */
    static List<String> parseKeywordLines(List<String> lines) {
        return lines.stream()
                .map(l -> l.strip().toLowerCase(Locale.ROOT))
                .filter(l -> !l.isEmpty() && !l.startsWith("#"))
                .distinct()
                .toList();
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
    static Pattern compileTermPattern(List<String> terms) {
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
     * <p>Keywords are stored lowercase (see {@link #parseKeywordLines}) and matched against
     * lowercased input (see {@link #matchesKeyword}), so {@code CASE_INSENSITIVE} is not needed
     * and is intentionally omitted. Relying on the flag alone would silently break non-ASCII
     * input because {@code CASE_INSENSITIVE} applies ASCII folding only without
     * {@code UNICODE_CASE}.
     */
    static Pattern compileKeywordPattern(List<String> keywords) {
        if (keywords.isEmpty()) {
            return null;
        }
        String pattern = keywords.stream()
                .map(kw -> "\\b" + Pattern.quote(kw) + "\\b")
                .collect(joining("|"));
        return Pattern.compile(pattern);
    }
}
