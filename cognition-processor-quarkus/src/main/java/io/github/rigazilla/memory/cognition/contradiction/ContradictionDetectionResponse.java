package io.github.rigazilla.memory.cognition.contradiction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Structured response from the LLM contradiction detector.
 *
 * <p>The LLM is asked to compare two memory contents and report whether they
 * contradict each other, the type of contradiction, the recommended resolution
 * strategy, and a brief rationale.
 *
 * <p>When returned from {@link ContradictionDetector#detectBatch}, the {@code index}
 * field identifies which candidate (0-based) this result belongs to.  For the
 * single-pair {@link ContradictionDetector#detect} method the index is always -1.
 *
 * <p>Implemented as a plain class (not a record) so that LangChain4j's
 * {@code PojoCollectionOutputParser} can instantiate it via the default no-arg
 * constructor when deserialising {@code List<ContradictionDetectionResponse>}.
 */
public class ContradictionDetectionResponse {

    /**
     * 0-based position of the candidate in the batch list, or {@code -1} for
     * single-pair detections.
     */
    private int index;

    /** {@code true} when the two memories express contradictory claims. */
    private boolean contradicts;

    /** High-level category of the contradiction, or {@link ContradictionType#NONE}. */
    private ContradictionType contradictionType;

    /** Recommended resolution strategy. */
    private ResolutionStrategy recommendedStrategy;

    /** One-sentence rationale explaining the contradiction (or why there is none). */
    private String rationale;

    /** No-arg constructor required by LangChain4j's collection output parser. */
    public ContradictionDetectionResponse() {
        this.index = -1;
        this.contradictionType = ContradictionType.NONE;
        this.recommendedStrategy = ResolutionStrategy.RECENCY;
        this.rationale = "";
    }

    @JsonCreator
    public static ContradictionDetectionResponse create(
            @JsonProperty("index") Integer index,
            @JsonProperty("contradicts") Boolean contradicts,
            @JsonProperty("contradictionType") String contradictionType,
            @JsonProperty("recommendedStrategy") String recommendedStrategy,
            @JsonProperty("rationale") String rationale) {
        ContradictionDetectionResponse r = new ContradictionDetectionResponse();
        r.index = index != null ? index : -1;
        r.contradicts = contradicts != null && contradicts;
        r.contradictionType = ContradictionType.fromValue(contradictionType);
        r.recommendedStrategy = ResolutionStrategy.fromValue(recommendedStrategy);
        r.rationale = rationale != null ? rationale : "";
        return r;
    }

    /**
     * Factory used by tests and the single-pair {@link ContradictionDetector#detect} path.
     * Sets {@code index} to {@code -1}.
     */
    public static ContradictionDetectionResponse create(
            Boolean contradicts,
            String contradictionType,
            String recommendedStrategy,
            String rationale) {
        return create(null, contradicts, contradictionType, recommendedStrategy, rationale);
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public int index() {
        return index;
    }

    public boolean contradicts() {
        return contradicts;
    }

    public ContradictionType contradictionType() {
        return contradictionType;
    }

    public ResolutionStrategy recommendedStrategy() {
        return recommendedStrategy;
    }

    public String rationale() {
        return rationale;
    }

    // Setters required for Jackson deserialization (used by the collection parser)

    @JsonProperty("index")
    public void setIndex(int index) {
        this.index = index;
    }

    @JsonProperty("contradicts")
    public void setContradicts(boolean contradicts) {
        this.contradicts = contradicts;
    }

    @JsonProperty("contradictionType")
    public void setContradictionType(String contradictionType) {
        this.contradictionType = ContradictionType.fromValue(contradictionType);
    }

    @JsonProperty("recommendedStrategy")
    public void setRecommendedStrategy(String recommendedStrategy) {
        this.recommendedStrategy = ResolutionStrategy.fromValue(recommendedStrategy);
    }

    @JsonProperty("rationale")
    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    /** Convenience: returns {@code true} when the LLM found a coexistence case. */
    public boolean isCoexistence() {
        return contradicts && recommendedStrategy == ResolutionStrategy.COEXISTENCE;
    }
}
