package io.github.rigazilla.memory.cognition.storeall;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.rigazilla.memory.cognition.config.CognitionConfig;
import io.github.rigazilla.memory.cognition.extraction.MemoryCandidate;
import io.github.rigazilla.memory.cognition.grpc.ConversationOwnerResolver;
import io.github.rigazilla.memory.cognition.model.Provenance;
import io.github.rigazilla.memory.cognition.writer.MemoryWriter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Core logic for the naive store-all cognitive process.
 * Writes each entry event verbatim to memory-service — no LLM, no filtering.
 */
@ApplicationScoped
public class StoreAllService {

    private static final Logger LOG = Logger.getLogger(StoreAllService.class);
    static final String MEMORY_TYPE = "verbatim";
    static final double CONFIDENCE = 1.0;

    @Inject
    ConversationOwnerResolver ownerResolver;

    @Inject
    MemoryWriter memoryWriter;

    @Inject
    CognitionConfig cognition;

    private final AtomicLong memoriesWritten = new AtomicLong(0);
    private final AtomicLong eventsProcessed = new AtomicLong(0);

    /**
     * Handle one entry event: extract text, write one verbatim memory.
     * Called on the event hot path — failures are logged and swallowed so the
     * stream cursor keeps advancing.
     *
     * @param conversationId conversation UUID string
     * @param cursor         event cursor
     * @param entryId        entry UUID string (may be null for non-entry events)
     * @param rootNode       pre-parsed JSON root node from the event data field
     */
    public void handle(String conversationId, String cursor, String entryId, JsonNode rootNode) {
        eventsProcessed.incrementAndGet();
        try {
            String text = extractText(rootNode);
            if (text == null || text.isBlank()) {
                LOG.debugf("store-all: no text in event for conversation %s, skipping", conversationId);
                return;
            }

            String role = extractRole(rootNode);
            String content = role != null ? "[" + role + "] " + text : text;
            String observedAt = extractCreatedAt(rootNode);

            String userId = ownerResolver.resolve(conversationId);

            MemoryCandidate candidate = new MemoryCandidate(
                    MEMORY_TYPE,
                    content,
                    CONFIDENCE,
                    entryId != null ? List.of(entryId) : List.of());

            Provenance provenance = new Provenance(
                    conversationId,
                    entryId != null ? List.of(entryId) : List.of(),
                    cursor,
                    cursor,
                    "verbatim",
                    null,
                    null,
                    null,
                    cognition.runtime().id(),
                    cognition.runtime().version(),
                    Instant.now());

            memoryWriter.writeMemory(userId, candidate, provenance, observedAt);
            memoriesWritten.incrementAndGet();
            LOG.debugf("store-all: wrote verbatim memory for conversation %s entry %s",
                    conversationId, entryId);

        } catch (Exception e) {
            LOG.errorf(e, "store-all: failed to write memory for conversation %s entry %s",
                    conversationId, entryId);
        }
    }

    /**
     * Extracts the message text from the event payload's {@code content[0].text} field,
     * or from the {@code history/lc4j} events array structure.
     *
     * @return text string, or {@code null} if not found
     */
    String extractText(JsonNode rootNode) {
        if (rootNode == null) {
            return null;
        }
        JsonNode contentArray = rootNode.get("content");
        if (contentArray == null || !contentArray.isArray() || contentArray.isEmpty()) {
            return null;
        }
        JsonNode first = contentArray.get(0);
        if (first == null) {
            return null;
        }
        // Plain "history" format: content[0].text
        JsonNode textNode = first.get("text");
        if (textNode != null && !textNode.asText("").isBlank()) {
            return textNode.asText("").strip();
        }
        // "history/lc4j" format: look for Completed event with aiMessage.text
        JsonNode eventsNode = first.get("events");
        if (eventsNode != null && eventsNode.isArray()) {
            for (JsonNode ev : eventsNode) {
                JsonNode eventType = ev.get("eventType");
                if (eventType != null && "Completed".equals(eventType.asText())) {
                    JsonNode aiMessage = ev.get("aiMessage");
                    if (aiMessage != null) {
                        JsonNode aiText = aiMessage.get("text");
                        if (aiText != null && !aiText.asText("").isBlank()) {
                            return aiText.asText("").strip();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Extracts the role from {@code content[0].role}, or {@code null} if absent.
     */
    String extractRole(JsonNode rootNode) {
        if (rootNode == null) {
            return null;
        }
        JsonNode contentArray = rootNode.get("content");
        if (contentArray == null || !contentArray.isArray() || contentArray.isEmpty()) {
            return null;
        }
        JsonNode roleNode = contentArray.get(0).get("role");
        return roleNode != null ? roleNode.asText(null) : null;
    }

    /**
     * Extracts {@code createdAt} from the event payload root, falling back to
     * {@code Instant.now()} (as ISO-8601 string) with a warning.
     */
    String extractCreatedAt(JsonNode rootNode) {
        if (rootNode != null) {
            JsonNode createdAt = rootNode.get("createdAt");
            if (createdAt != null && !createdAt.asText("").isBlank()) {
                return createdAt.asText();
            }
            // also try snake_case variant
            JsonNode createdAtSnake = rootNode.get("created_at");
            if (createdAtSnake != null && !createdAtSnake.asText("").isBlank()) {
                return createdAtSnake.asText();
            }
        }
        String fallback = Instant.now().toString();
        LOG.warnf("store-all: no createdAt in event payload, using ingestion time %s as observed_at",
                fallback);
        return fallback;
    }

    /** Number of memories successfully written. */
    public long getMemoriesWritten() {
        return memoriesWritten.get();
    }

    /** Number of entry events processed (including skipped ones). */
    public long getEventsProcessed() {
        return eventsProcessed.get();
    }
}
