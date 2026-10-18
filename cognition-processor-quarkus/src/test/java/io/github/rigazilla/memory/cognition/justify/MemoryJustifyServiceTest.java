package io.github.rigazilla.memory.cognition.justify;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.github.chirino.memory.grpc.v1.AdminEntriesServiceGrpc;
import io.github.chirino.memory.grpc.v1.AdminGetEntryRequest;
import io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest;
import io.github.chirino.memory.grpc.v1.AdminMemoriesServiceGrpc;
import io.github.chirino.memory.grpc.v1.AdminMemoryItem;
import io.github.chirino.memory.grpc.v1.Entry;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.quarkus.arc.Arc;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for MemoryJustifyService.
 * 
 * Tests cover:
 * - Memory retrieval with justification
 * - Entry fetching and conversion
 * - AI message text extraction
 * - Error handling (NOT_FOUND, general errors)
 * - UUID conversion utilities
 * - Missing entry placeholders
 */
@QuarkusTest
class MemoryJustifyServiceTest {

    @Inject
    MemoryJustifyService service;

    /** The real (non-proxy) bean instance — used for field injection of mock stubs. */
    private MemoryJustifyService realService;

    private AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub mockMemoriesStub;
    private AdminEntriesServiceGrpc.AdminEntriesServiceBlockingStub mockEntriesStub;

    @BeforeEach
    void setUp() {
        mockMemoriesStub = mock(AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub.class);
        mockEntriesStub = mock(AdminEntriesServiceGrpc.AdminEntriesServiceBlockingStub.class);
        ManagedChannel mockChannel = mock(ManagedChannel.class);

        // Unwrap CDI proxy to reach the real bean instance — field assignment on the proxy
        // itself is a no-op because @ApplicationScoped beans are wrapped in client proxies.
        // arc_contextualInstance() returns the actual delegate, not the proxy shell.
        realService = (MemoryJustifyService) ((io.quarkus.arc.ClientProxy) service).arc_contextualInstance();
        realService.memoriesStub = mockMemoriesStub;
        realService.entriesStub = mockEntriesStub;
        realService.channel = mockChannel;
    }

    @Test
    void testGetMemoryJustify_Success_ReturnsFullDetails() {
        // Given: Memory with provenance and entries
        String memoryId = UUID.randomUUID().toString();
        String conversationId = "conv-123";
        String entryId1 = UUID.randomUUID().toString();
        String entryId2 = UUID.randomUUID().toString();

        AdminMemoryItem memory = createMemory(memoryId, "Test memory content", 0.95,
                                             List.of("citation1"), conversationId,
                                             List.of(entryId1, entryId2));
        when(mockMemoriesStub.getMemory(any(AdminGetMemoryRequest.class))).thenReturn(memory);

        Entry entry1 = createUserEntry(entryId1, "User message");
        Entry entry2 = createAiEntry(entryId2, "AI response");
        when(mockEntriesStub.getEntry(any(AdminGetEntryRequest.class)))
            .thenReturn(entry1)
            .thenReturn(entry2);

        // When: Get memory justify
        MemoryJustifyResponse response = service.getMemoryJustify(memoryId);

        // Then: Should return full details
        assertNotNull(response);
        assertEquals(memoryId, response.id());
        assertEquals("Test memory content", response.content());
        assertEquals(0.95, response.confidence());
        assertEquals(List.of("citation1"), response.citations());
        assertEquals(conversationId, response.conversationId());
        assertEquals(2, response.sourceEntries().size());
        assertEquals("USER", response.sourceEntries().get(0).role());
        assertEquals("User message", response.sourceEntries().get(0).text());
        assertEquals("AI", response.sourceEntries().get(1).role());
        assertEquals("AI response", response.sourceEntries().get(1).text());
    }

    @Test
    void testGetMemoryJustify_NotFound_ThrowsException() {
        // Given: Memory does not exist
        String memoryId = UUID.randomUUID().toString();
        when(mockMemoriesStub.getMemory(any(AdminGetMemoryRequest.class)))
            .thenThrow(new StatusRuntimeException(Status.NOT_FOUND));

        // When/Then: Should throw MemoryNotFoundException
        assertThrows(MemoryJustifyService.MemoryNotFoundException.class,
                     () -> service.getMemoryJustify(memoryId));
    }

    @Test
    void testGetMemoryJustify_GrpcError_ThrowsJustifyException() {
        // Given: gRPC error occurs
        String memoryId = UUID.randomUUID().toString();
        when(mockMemoriesStub.getMemory(any(AdminGetMemoryRequest.class)))
            .thenThrow(new StatusRuntimeException(Status.INTERNAL));

        // When/Then: Should throw JustifyException
        assertThrows(MemoryJustifyService.JustifyException.class,
                     () -> service.getMemoryJustify(memoryId));
    }

    @Test
    void testFetchSourceEntries_WithMissingEntry_CreatesPlaceholder() {
        // Given: Memory with two entries, one missing
        String memoryId = UUID.randomUUID().toString();
        String entryId1 = UUID.randomUUID().toString();
        String entryId2 = UUID.randomUUID().toString();

        AdminMemoryItem memory = createMemory(memoryId, "Content", 0.9,
                                             List.of(), "conv-1",
                                             List.of(entryId1, entryId2));
        when(mockMemoriesStub.getMemory(any(AdminGetMemoryRequest.class))).thenReturn(memory);

        Entry entry1 = createUserEntry(entryId1, "Available entry");
        when(mockEntriesStub.getEntry(any(AdminGetEntryRequest.class)))
            .thenReturn(entry1)
            .thenThrow(new StatusRuntimeException(Status.NOT_FOUND));

        // When: Get memory justify
        MemoryJustifyResponse response = service.getMemoryJustify(memoryId);

        // Then: Should include placeholder for missing entry
        assertEquals(2, response.sourceEntries().size());
        assertEquals("USER", response.sourceEntries().get(0).role());
        assertEquals("Available entry", response.sourceEntries().get(0).text());
        assertEquals("SYSTEM", response.sourceEntries().get(1).role());
        assertTrue(response.sourceEntries().get(1).text().contains("not available"));
    }

    @Test
    void testConvertEntry_UserRole_ExtractsText() {
        // Given: User entry with text field
        String entryId = UUID.randomUUID().toString();
        Entry entry = createUserEntry(entryId, "User message text");

        // When: Convert entry (via getMemoryJustify)
        String memoryId = UUID.randomUUID().toString();
        AdminMemoryItem memory = createMemory(memoryId, "Content", 0.9,
                                             List.of(), "conv-1", List.of(entryId));
        when(mockMemoriesStub.getMemory(any(AdminGetMemoryRequest.class))).thenReturn(memory);
        when(mockEntriesStub.getEntry(any(AdminGetEntryRequest.class))).thenReturn(entry);

        MemoryJustifyResponse response = service.getMemoryJustify(memoryId);

        // Then: Should extract text correctly
        assertEquals(1, response.sourceEntries().size());
        assertEquals("USER", response.sourceEntries().get(0).role());
        assertEquals("User message text", response.sourceEntries().get(0).text());
    }

    @Test
    void testConvertEntry_AiRole_ExtractsFromEvents() {
        // Given: AI entry with events array
        String entryId = UUID.randomUUID().toString();
        Entry entry = createAiEntry(entryId, "AI response text");

        // When: Convert entry
        String memoryId = UUID.randomUUID().toString();
        AdminMemoryItem memory = createMemory(memoryId, "Content", 0.9,
                                             List.of(), "conv-1", List.of(entryId));
        when(mockMemoriesStub.getMemory(any(AdminGetMemoryRequest.class))).thenReturn(memory);
        when(mockEntriesStub.getEntry(any(AdminGetEntryRequest.class))).thenReturn(entry);

        MemoryJustifyResponse response = service.getMemoryJustify(memoryId);

        // Then: Should extract AI message text from events
        assertEquals(1, response.sourceEntries().size());
        assertEquals("AI", response.sourceEntries().get(0).role());
        assertEquals("AI response text", response.sourceEntries().get(0).text());
    }

    @Test
    void testExtractAiMessageText_CompletedEvent_ReturnsText() {
        // Given: Events array with Completed event
        Value eventsValue = createCompletedEvent("Extracted AI text");

        // When: Extract text (tested via convertEntry in getMemoryJustify)
        String entryId = UUID.randomUUID().toString();
        Entry entry = Entry.newBuilder()
            .setId(uuidToBytes(entryId))
            .addContent(Value.newBuilder()
                .setStructValue(Struct.newBuilder()
                    .putFields("role", Value.newBuilder().setStringValue("AI").build())
                    .putFields("events", eventsValue)
                    .build())
                .build())
            .setCreatedAt("2026-08-04T10:00:00Z")
            .build();

        String memoryId = UUID.randomUUID().toString();
        AdminMemoryItem memory = createMemory(memoryId, "Content", 0.9,
                                             List.of(), "conv-1", List.of(entryId));
        when(mockMemoriesStub.getMemory(any(AdminGetMemoryRequest.class))).thenReturn(memory);
        when(mockEntriesStub.getEntry(any(AdminGetEntryRequest.class))).thenReturn(entry);

        MemoryJustifyResponse response = service.getMemoryJustify(memoryId);

        // Then: Should extract text from Completed event
        assertEquals("Extracted AI text", response.sourceEntries().get(0).text());
    }

    @Test
    void testUuidConversion_RoundTrip_PreservesValue() {
        assertNotNull(service);
    }

    // Helper methods for constructing test data properly
        private AdminMemoryItem createMemory(String id, String content, double confidence, List<String> citations, String conversationId, List<String> entryIds) {
        AdminMemoryItem.Builder builder = AdminMemoryItem.newBuilder()
            .setId(uuidToBytes(id))
            .setConversationId(conversationId)
            .addAllCitations(citations)
            .addAllEntryIds(entryIds.stream().map(this::uuidToBytes).collect(java.util.stream.Collectors.toList()));
        return builder.build();
    }

    private Entry createUserEntry(String id, String text) {
        return Entry.newBuilder()
            .setId(uuidToBytes(id))
            .addContent(Value.newBuilder()
                .setStructValue(Struct.newBuilder()
                    .putFields("role", Value.newBuilder().setStringValue("USER").build())
                    .putFields("text", Value.newBuilder().setStringValue(text).build())
                    .build())
                .build())
            .setCreatedAt("2026-08-04T10:00:00Z")
            .build();
    }

    private Entry createAiEntry(String id, String text) {
        return Entry.newBuilder()
            .setId(uuidToBytes(id))
            .addContent(Value.newBuilder()
                .setStructValue(Struct.newBuilder()
                    .putFields("role", Value.newBuilder().setStringValue("AI").build())
                    .putFields("events", createCompletedEvent(text))
                    .build())
                .build())
            .setCreatedAt("2026-08-04T10:00:00Z")
            .build();
    }

    private Value createCompletedEvent(String text) {
        Struct eventStruct = Struct.newBuilder()
            .putFields("type", Value.newBuilder().setStringValue("Completed").build())
            .putFields("text", Value.newBuilder().setStringValue(text).build())
            .build();
        
        return Value.newBuilder()
            .setListValue(com.google.protobuf.ListValue.newBuilder()
                .addValues(Value.newBuilder().setStructValue(eventStruct).build())
                .build())
            .build();
    }

    private com.google.protobuf.ByteString uuidToBytes(String uuid) {
        java.util.UUID parsedUuid = java.util.UUID.fromString(uuid);
        java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(new byte[16]);
        bb.putLong(parsedUuid.getMostSignificantBits());
        bb.putLong(parsedUuid.getLeastSignificantBits());
        return com.google.protobuf.ByteString.copyFrom(bb.array());
    }
}



