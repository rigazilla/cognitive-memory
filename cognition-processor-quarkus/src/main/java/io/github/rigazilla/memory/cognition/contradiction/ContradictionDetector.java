package io.github.rigazilla.memory.cognition.contradiction;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;

/**
 * LangChain4j AI Service that decides whether memory contents contradict each other.
 *
 * <p>Uses the shared "memory" named model configured in {@code application.properties}.
 * The response is deserialized into {@link ContradictionDetectionResponse}.
 *
 * <p>Two detection modes are provided:
 * <ul>
 *   <li>{@link #detect} — single-pair comparison (original API, kept for the batch
 *       fallback path and the backfill {@link ContradictionResolutionProcess}).</li>
 *   <li>{@link #detectBatch} — evaluates all candidates in one LLM round-trip,
 *       reducing k sequential calls to a single call on the write path.</li>
 * </ul>
 */
@RegisterAiService(
        chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class,
        modelName = "memory")
public interface ContradictionDetector {

    /**
     * Analyse two memories and decide whether they contradict each other.
     *
     * @param memoryType  shared memory type (e.g. {@code "preference"}, {@code "fact"})
     * @param contentA    content of the first memory
     * @param observedAtA observed_at timestamp of memory A (ISO-8601 or empty string)
     * @param contentB    content of the second memory
     * @param observedAtB observed_at timestamp of memory B (ISO-8601 or empty string)
     * @return structured contradiction analysis
     */
    @SystemMessage(fromResource = "prompts/contradiction-detector-system.md")
    @UserMessage("""
            Analyse the following two memories and determine whether they contradict each other.

            Memory type: {{memoryType}}

            Memory A (observed_at: {{observedAtA}}):
            {{contentA}}

            Memory B (observed_at: {{observedAtB}}):
            {{contentB}}

            Return a JSON object with:
            - contradicts: boolean — true if the memories express conflicting claims
            - contradictionType: one of "preference_change", "identity_change", "state_change", \
"semantic_conflict", "none"
            - recommendedStrategy: one of "recency", "confidence", "coexistence"
            - rationale: one sentence explaining the decision
            """)
    ContradictionDetectionResponse detect(
            @V("memoryType")  String memoryType,
            @V("contentA")    String contentA,
            @V("observedAtA") String observedAtA,
            @V("contentB")    String contentB,
            @V("observedAtB") String observedAtB
    );

    /**
     * Evaluate all candidate memories against a single new memory in one LLM round-trip.
     *
     * <p>Replaces the serial per-candidate loop in the on-insert path, reducing latency
     * from up to k sequential LLM calls to a single call.
     *
     * <p>Returns a {@link ContradictionBatchResult} wrapper (rather than {@code List<...>} directly)
     * because older quarkus-langchain4j versions fail at build time when an AI service method
     * returns a raw collection — {@code PojoCollectionOutputParser.formatInstructions()} throws
     * unconditionally.  The wrapper lets {@code PojoOutputParser} handle deserialization instead.
     *
     * @param memoryType     shared memory type (e.g. {@code "preference"}, {@code "fact"})
     * @param newContent     content of the newly inserted memory
     * @param newObservedAt  observed_at timestamp of the new memory (ISO-8601 or empty string)
     * @param candidatesJson JSON array of candidate objects, each with fields
     *                       {@code index} (int), {@code content} (string),
     *                       {@code observed_at} (string)
     * @return wrapper whose {@code results} list has one entry per candidate, each carrying the
     *         originating {@code index} so results can be matched back to the correct
     *         {@link io.github.chirino.memory.grpc.v1.AdminMemoryItem}
     */
    @SystemMessage(fromResource = "prompts/contradiction-detector-batch-system.md")
    @UserMessage("""
            Memory type: {{memoryType}}

            New memory (observed_at: {{newObservedAt}}):
            {{newContent}}

            Candidates:
            {{candidatesJson}}

            Return a JSON object with a single key "results" whose value is an array with one \
object per candidate, in the same order, each with:
            - index: integer (0-based, matching the candidate list)
            - contradicts: boolean
            - contradictionType: one of "preference_change", "identity_change", "state_change", \
"semantic_conflict", "none"
            - recommendedStrategy: one of "recency", "confidence", "coexistence"
            - rationale: one sentence
            """)
    ContradictionBatchResult detectBatch(
            @V("memoryType")     String memoryType,
            @V("newContent")     String newContent,
            @V("newObservedAt")  String newObservedAt,
            @V("candidatesJson") String candidatesJson
    );
}
