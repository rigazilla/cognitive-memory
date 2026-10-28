package io.github.rigazilla.memory.cognition.storeall;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link StoreAllService} text and role extraction logic.
 * Pure JUnit 5 — no CDI container needed.
 */
class StoreAllServiceTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private StoreAllService service;

    @BeforeEach
    void setUp() {
        service = new StoreAllService();
    }

    // -------------------------------------------------------------------------
    // extractText
    // -------------------------------------------------------------------------

    @Test
    void extractText_plainHistory_returnsText() throws Exception {
        JsonNode node = MAPPER.readTree(
                "{\"content\":[{\"role\":\"USER\",\"text\":\"hello world\"}]}");
        assertEquals("hello world", service.extractText(node));
    }

    @Test
    void extractText_aiRole_returnsText() throws Exception {
        JsonNode node = MAPPER.readTree(
                "{\"content\":[{\"role\":\"AI\",\"text\":\"I can help with that.\"}]}");
        assertEquals("I can help with that.", service.extractText(node));
    }

    @Test
    void extractText_noContent_returnsNull() throws Exception {
        JsonNode node = MAPPER.readTree("{\"conversationId\":\"abc\"}");
        assertNull(service.extractText(node));
    }

    @Test
    void extractText_emptyContentArray_returnsNull() throws Exception {
        JsonNode node = MAPPER.readTree("{\"content\":[]}");
        assertNull(service.extractText(node));
    }

    @Test
    void extractText_nullNode_returnsNull() {
        assertNull(service.extractText(null));
    }

    @Test
    void extractText_lc4jCompletedEvent_returnsAiText() throws Exception {
        JsonNode node = MAPPER.readTree(
                "{\"content\":[{\"role\":\"AI\",\"events\":["
                + "{\"eventType\":\"Completed\",\"aiMessage\":{\"text\":\"LLM response text\"}}"
                + "]}]}");
        assertEquals("LLM response text", service.extractText(node));
    }

    @Test
    void extractText_lc4jNoCompletedEvent_returnsNull() throws Exception {
        JsonNode node = MAPPER.readTree(
                "{\"content\":[{\"role\":\"AI\",\"events\":["
                + "{\"eventType\":\"PartialResponse\",\"chunk\":\"partial\"}"
                + "]}]}");
        assertNull(service.extractText(node));
    }

    // -------------------------------------------------------------------------
    // extractRole
    // -------------------------------------------------------------------------

    @Test
    void extractRole_userRole_returnsUser() throws Exception {
        JsonNode node = MAPPER.readTree("{\"content\":[{\"role\":\"USER\",\"text\":\"hi\"}]}");
        assertEquals("USER", service.extractRole(node));
    }

    @Test
    void extractRole_aiRole_returnsAi() throws Exception {
        JsonNode node = MAPPER.readTree("{\"content\":[{\"role\":\"AI\",\"text\":\"hi\"}]}");
        assertEquals("AI", service.extractRole(node));
    }

    @Test
    void extractRole_noRole_returnsNull() throws Exception {
        JsonNode node = MAPPER.readTree("{\"content\":[{\"text\":\"hi\"}]}");
        assertNull(service.extractRole(node));
    }

    @Test
    void extractRole_noContent_returnsNull() throws Exception {
        JsonNode node = MAPPER.readTree("{}");
        assertNull(service.extractRole(node));
    }

    // -------------------------------------------------------------------------
    // extractCreatedAt
    // -------------------------------------------------------------------------

    @Test
    void extractCreatedAt_camelCase_returnsValue() throws Exception {
        JsonNode node = MAPPER.readTree("{\"createdAt\":\"2024-01-15T10:30:00Z\"}");
        assertEquals("2024-01-15T10:30:00Z", service.extractCreatedAt(node));
    }

    @Test
    void extractCreatedAt_snakeCase_returnsValue() throws Exception {
        JsonNode node = MAPPER.readTree("{\"created_at\":\"2024-01-15T10:30:00Z\"}");
        assertEquals("2024-01-15T10:30:00Z", service.extractCreatedAt(node));
    }

    @Test
    void extractCreatedAt_missing_returnsFallbackInstant() throws Exception {
        JsonNode node = MAPPER.readTree("{\"conversationId\":\"abc\"}");
        String result = service.extractCreatedAt(node);
        assertNotNull(result);
        // Should be parseable as an ISO-8601 instant
        assertDoesNotThrow(() -> java.time.Instant.parse(result));
    }

    @Test
    void extractCreatedAt_nullNode_returnsFallback() {
        String result = service.extractCreatedAt(null);
        assertNotNull(result);
        assertDoesNotThrow(() -> java.time.Instant.parse(result));
    }
}
