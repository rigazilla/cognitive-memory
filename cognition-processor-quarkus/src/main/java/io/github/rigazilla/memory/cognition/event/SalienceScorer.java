package io.github.rigazilla.memory.cognition.event;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
 * unscoreable input is never dropped. English-only, v1.
 */
@ApplicationScoped
public class SalienceScorer {

    private static final Logger LOG = Logger.getLogger(SalienceScorer.class);

    private static final String BUNDLED_KEYWORDS = "salience/default-keywords.txt";

    // -------------------------------------------------------------------------
    // Score constants — one name per rule, values are independent tuning knobs.
    // -------------------------------------------------------------------------

    private static final double SCORE_GREETING  = 0.1;  // standalone greeting (hi, hello, hey …)
    private static final double SCORE_ACK       = 0.2;  // acknowledgment (ok, got it, sure …)
    private static final double SCORE_FAREWELL  = 0.2;  // farewell (bye, see you, take care …)
    private static final double SCORE_FILLER    = 0.1;  // filler word (um, uh, hmm, well …)
    private static final double SCORE_THANKS    = 0.2;  // thanks (thanks, thank you, thx …)
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

    // -------------------------------------------------------------------------
    // Pre-compiled patterns — anchored at both ends (^ and $) so that
    // "hi, deploy the server" does NOT match GREETING.
    // -------------------------------------------------------------------------

    private static final Pattern GREETING = Pattern.compile(
            "^(hi|hello|hey|greetings|good morning|good afternoon|good evening)[\\s!.]*$",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern ACKNOWLEDGMENT = Pattern.compile(
            "^(ok|okay|sure|got it|understood|sounds good|makes sense|alright)[\\s!.]*$",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern FAREWELL = Pattern.compile(
            "^(bye|goodbye|see you|talk later|take care|have a good day)[\\s!.]*$",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern FILLER = Pattern.compile(
            "^(um|uh|hmm|well|so|like)[\\s!.]*$",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern THANKS = Pattern.compile(
            "^(thanks|thank you|thx|ty)[\\s!.]*$",
            Pattern.CASE_INSENSITIVE);

    // -------------------------------------------------------------------------
    // Configuration — all properties are optional; built-in defaults apply when absent.
    // -------------------------------------------------------------------------

    @ConfigProperty(name = "salience.enabled", defaultValue = "true")
    boolean enabled;

    @ConfigProperty(name = "salience.threshold", defaultValue = "0.3")
    double threshold;

    @ConfigProperty(name = "salience.min-length", defaultValue = "10")
    int minLength;

    @ConfigProperty(name = "salience.pattern.greeting-enabled", defaultValue = "true")
    boolean greetingEnabled;

    @ConfigProperty(name = "salience.pattern.acknowledgment-enabled", defaultValue = "true")
    boolean acknowledgmentEnabled;

    @ConfigProperty(name = "salience.pattern.farewell-enabled", defaultValue = "true")
    boolean farewellEnabled;

    @ConfigProperty(name = "salience.pattern.filler-enabled", defaultValue = "true")
    boolean fillerEnabled;

    @ConfigProperty(name = "salience.pattern.thanks-enabled", defaultValue = "true")
    boolean thanksEnabled;

    @ConfigProperty(name = "salience.keywords.enabled", defaultValue = "true")
    boolean keywordsEnabled;

    /**
     * Highest-priority keyword override: comma-separated list.
     * When present, overrides both {@code salience.keywords.file} and the bundled defaults.
     * Optional; absent by default.
     */
    @ConfigProperty(name = "salience.keywords.list")
    Optional<String> keywordsList;

    /**
     * Path to an external keyword file (one keyword per line, # comments, blank lines ignored).
     * When set, used instead of the bundled {@code salience/default-keywords.txt}.
     * If the file is missing or unreadable, a WARN is logged and bundled defaults are used.
     * Optional; absent by default.
     */
    @ConfigProperty(name = "salience.keywords.file")
    Optional<String> keywordsFile;

    @ConfigProperty(name = "salience.metrics.enabled", defaultValue = "true")
    boolean metricsEnabled;

    // -------------------------------------------------------------------------
    // Metrics — thread-safe counters for extraction-load measurement.
    // -------------------------------------------------------------------------

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

    // Compiled once at startup; null when the keyword list is empty.
    private volatile Pattern keywordPattern;

    // -------------------------------------------------------------------------
    // Initialisation
    // -------------------------------------------------------------------------

    @PostConstruct
    void init() {
        List<String> keywords = loadKeywords();
        keywordPattern = compileKeywordPattern(keywords);
        LOG.infof("SalienceScorer initialised: enabled=%s, threshold=%.2f, keywords=%d",
                enabled, threshold, keywords.size());
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Metrics accessors
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Internal scoring — normalizes once; callers must pass non-null, non-blank text
    // -------------------------------------------------------------------------

    // Sentinel returned by scorePatternMatch when no named pattern matched.
    private static final double NO_MATCH = -1.0;

    private double scoreInternal(String text) {
        String normalized = text.strip().toLowerCase(Locale.ROOT);

        double patternScore = scorePatternMatch(normalized);
        if (patternScore != NO_MATCH) {
            return patternScore;
        }

        // High salience: keyword match — evaluated before length check so that short
        // but important messages like "bug here" (8 chars) are not filtered by length.
        if (keywordsEnabled && matchesKeyword(normalized)) {
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
        if (greetingEnabled && GREETING.matcher(normalized).matches()) {
            return SCORE_GREETING;
        }
        if (acknowledgmentEnabled && ACKNOWLEDGMENT.matcher(normalized).matches()) {
            return SCORE_ACK;
        }
        if (farewellEnabled && FAREWELL.matcher(normalized).matches()) {
            return SCORE_FAREWELL;
        }
        if (fillerEnabled && FILLER.matcher(normalized).matches()) {
            return SCORE_FILLER;
        }
        if (thanksEnabled && THANKS.matcher(normalized).matches()) {
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

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

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
     */
    List<String> loadKeywords() {
        if (keywordsList.isPresent()) {
            List<String> result = parseKeywordLines(List.of(keywordsList.get().split(",\\s*")));
            LOG.debugf("SalienceScorer: loaded %d keywords from salience.keywords.list", result.size());
            return result;
        }

        if (keywordsFile.isPresent()) {
            String path = keywordsFile.get();
            try {
                String content = Files.readString(Path.of(path), StandardCharsets.UTF_8);
                List<String> result = parseKeywordLines(content.lines().toList());
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
     * Trims lines, lowercases (ROOT locale), skips blanks and {@code #} comments.
     * Returns an unmodifiable list.
     */
    static List<String> parseKeywordLines(List<String> lines) {
        return lines.stream()
                .map(l -> l.strip().toLowerCase(Locale.ROOT))
                .filter(l -> !l.isEmpty() && !l.startsWith("#"))
                .distinct()
                .toList();
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
