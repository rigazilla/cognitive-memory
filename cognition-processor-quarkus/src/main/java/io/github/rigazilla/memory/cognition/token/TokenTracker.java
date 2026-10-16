package io.github.rigazilla.memory.cognition.token;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Application-scoped registry of token usage counters.
 *
 * <p>Each LLM resource within a cognitive process gets its own {@link TokenUsage} counter,
 * keyed by the combination of process ID and resource name. Counters are created lazily
 * on first access and live for the lifetime of the application.
 *
 * <p>Callers record token usage after each LLM call:
 * <pre>{@code
 *   tokenTracker.record("durable-memory-extraction", "extractor", inputTokens, outputTokens);
 * }</pre>
 *
 * <p>Process inspect() methods retrieve snapshots for inclusion in the API response:
 * <pre>{@code
 *   TokenUsage.Snapshot snap = tokenTracker.snapshot("durable-memory-extraction", "extractor");
 * }</pre>
 */
@ApplicationScoped
public class TokenTracker {

    private final ConcurrentHashMap<String, TokenUsage> counters = new ConcurrentHashMap<>();

    /**
     * Record token usage for one LLM call made by the named resource inside the given process.
     *
     * @param processId    cognitive process identifier (e.g. {@code "durable-memory-extraction"})
     * @param resourceName LLM resource name within the process (e.g. {@code "extractor"})
     * @param inputTokens  prompt tokens consumed (may be null — treated as zero)
     * @param outputTokens completion tokens produced (may be null — treated as zero)
     */
    public void record(String processId, String resourceName,
            Integer inputTokens, Integer outputTokens) {
        counters.computeIfAbsent(key(processId, resourceName), k -> new TokenUsage())
                .record(inputTokens, outputTokens);
    }

    /**
     * Return an immutable point-in-time snapshot for the named resource.
     *
     * <p>Returns a zero-valued snapshot when no calls have been recorded yet
     * so callers never receive null.
     *
     * @param processId    cognitive process identifier
     * @param resourceName LLM resource name
     * @return snapshot of current counters
     */
    public TokenUsage.Snapshot snapshot(String processId, String resourceName) {
        TokenUsage usage = counters.get(key(processId, resourceName));
        return usage != null ? usage.snapshot() : new TokenUsage().snapshot();
    }

    private static String key(String processId, String resourceName) {
        return processId + ":" + resourceName;
    }
}
