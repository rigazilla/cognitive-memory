package io.github.rigazilla.memory.cognition.contradiction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 * Wrapper returned by {@link ContradictionDetector#detectBatch}.
 *
 * <p>LangChain4j's {@code PojoCollectionOutputParser} (used when the AI-service return type is
 * {@code List<T>}) calls {@code formatInstructions()} which unconditionally throws
 * {@code IllegalStateException} in older quarkus-langchain4j versions, breaking the build-time
 * augmentation step.  Wrapping the list in a plain record avoids the collection parser entirely —
 * {@code PojoOutputParser} is used instead, which handles records correctly.
 */
public record ContradictionBatchResult(

        /**
         * One entry per candidate, in the same order as the input list sent to the LLM.
         * May be {@code null} if the model returned malformed JSON; callers should treat
         * {@code null} the same as an empty list.
         */
        List<ContradictionDetectionResponse> results
) {

    @JsonCreator
    public static ContradictionBatchResult create(
            @JsonProperty("results") List<ContradictionDetectionResponse> results) {
        return new ContradictionBatchResult(results != null ? results : Collections.emptyList());
    }
}
