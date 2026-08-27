package io.github.rigazilla.memory.cognition.event;

import io.github.rigazilla.memory.cognition.config.SalienceScorerConfig;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * Resolves the salience keyword list from the configured priority sources:
 * <ol>
 *   <li>{@code salience.keywords.list} — inline comma-separated config (highest priority)</li>
 *   <li>{@code salience.keywords.file} — path to an external file</li>
 *   <li>Bundled classpath resource {@code salience/default-keywords.txt}</li>
 * </ol>
 *
 * <p>When an external source is present but parses to zero effective keywords,
 * a WARN is logged and the service falls back to the bundled defaults.
 * An empty bundled resource aborts startup with {@link IllegalStateException}.
 */
@ApplicationScoped
public class KeywordLoader {

    private static final Logger LOG = Logger.getLogger(KeywordLoader.class);
    private static final String BUNDLED_KEYWORDS = "salience/default-keywords.txt";

    private final SalienceScorerConfig config;

    public KeywordLoader(SalienceScorerConfig config) {
        this.config = config;
    }

    /**
     * Returns the effective keyword list according to the configured priority order.
     */
    public List<String> load() {
        if (config.keywords().list().isPresent()) {
            List<String> result = parseKeywordLines(config.keywords().list().get());
            if (result.isEmpty()) {
                LOG.warnf("KeywordLoader: salience.keywords.list parsed to zero keywords"
                        + " (empty or comments-only) — falling back to bundled defaults");
                return loadBundled(BUNDLED_KEYWORDS);
            }
            LOG.debugf("KeywordLoader: loaded %d keywords from salience.keywords.list", result.size());
            return result;
        }

        if (config.keywords().file().isPresent()) {
            String path = config.keywords().file().get();
            try {
                String content = Files.readString(Path.of(path), StandardCharsets.UTF_8);
                List<String> result = parseKeywordLines(content.lines().toList());
                if (result.isEmpty()) {
                    LOG.warnf("KeywordLoader: salience.keywords.file '%s' parsed to zero keywords"
                            + " (empty or comments-only) — falling back to bundled defaults", path);
                    return loadBundled(BUNDLED_KEYWORDS);
                }
                LOG.infof("KeywordLoader: loaded %d keywords from file: %s", result.size(), path);
                return result;
            } catch (Exception e) {
                LOG.warnf("KeywordLoader: could not read salience.keywords.file '%s' (%s)"
                        + " — falling back to bundled defaults", path, e.getMessage());
                return loadBundled(BUNDLED_KEYWORDS);
            }
        }

        return loadBundled(BUNDLED_KEYWORDS);
    }

    /**
     * Loads keywords from the given classpath resource path.
     * Throws {@link IllegalStateException} on any failure — absent resource, read error,
     * or empty result — because a running service with no keywords silently violates
     * the high-recall acceptance criterion.
     *
     * <p>{@code catch (Exception)} is intentionally broad: it ensures every failure path
     * carries the same diagnostic message rather than escaping unwrapped.
     */
    private List<String> loadBundled(String resourcePath) {
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
            LOG.debugf("KeywordLoader: loaded %d keywords from bundled defaults", result.size());
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
     * trims, removes blank lines and {@code #} comments, and deduplicates.
     * Returns an unmodifiable list.
     */
    public static List<String> parseKeywordLines(List<String> lines) {
        return lines.stream()
                .map(l -> l.strip().toLowerCase(Locale.ROOT))
                .filter(l -> !l.isEmpty() && !l.startsWith("#"))
                .distinct()
                .toList();
    }
}
