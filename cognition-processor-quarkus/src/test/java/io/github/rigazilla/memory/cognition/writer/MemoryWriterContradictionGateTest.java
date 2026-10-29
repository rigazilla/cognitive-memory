package io.github.rigazilla.memory.cognition.writer;

import com.google.protobuf.ByteString;
import io.github.chirino.memory.grpc.v1.AdminMemoriesServiceGrpc;
import io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest;
import io.github.chirino.memory.grpc.v1.MemoryWriteResult;
import io.github.rigazilla.memory.cognition.contradiction.ContradictionOnInsertService;
import io.github.rigazilla.memory.cognition.contradiction.ContradictionResolutionProcess;
import io.github.rigazilla.memory.cognition.enablement.ProcessEnablementService;
import io.github.rigazilla.memory.cognition.extraction.MemoryCandidate;
import io.github.rigazilla.memory.cognition.model.Provenance;
import io.grpc.ManagedChannel;
import io.quarkus.arc.ClientProxy;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Verifies that {@link MemoryWriter} gates on-insert contradiction detection
 * against the {@link ContradictionResolutionProcess} enablement flag.
 */
@QuarkusTest
class MemoryWriterContradictionGateTest {

    private static final String TEST_OBSERVED_AT = "2025-01-15T10:00:00Z";

    @Inject
    MemoryWriter writer;

    @Inject
    ProcessEnablementService enablementService;

    @InjectMock
    ContradictionOnInsertService contradictionOnInsertService;

    private MemoryWriter realWriter;
    private AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub mockMemoriesStub;

    @BeforeEach
    void setUp() {
        mockMemoriesStub = mock(AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub.class);
        ManagedChannel mockChannel = mock(ManagedChannel.class);

        realWriter = (MemoryWriter) ((ClientProxy) writer).arc_contextualInstance();
        realWriter.memoriesStub = mockMemoriesStub;
        realWriter.channel = mockChannel;

        // Reset enablement overrides so each test starts clean
        ProcessEnablementService real = (ProcessEnablementService)
                ((ClientProxy) enablementService).arc_contextualInstance();
        real.resetOverrides();

        // Default stub response
        MemoryWriteResult result = MemoryWriteResult.newBuilder()
                .setId(uuidToBytes(UUID.randomUUID().toString())).build();
        when(mockMemoriesStub.putMemory(any(AdminPutMemoryRequest.class))).thenReturn(result);
    }

    @Test
    void checkOnInsertIsCalledWhenContradictionResolutionEnabled() {
        // contradiction-resolution defaults to enabled
        MemoryCandidate candidate = new MemoryCandidate("fact", "User prefers dark mode", 0.9, List.of("e1"));
        Provenance provenance = createProvenance("conv-1", List.of("e1"));

        writer.writeMemory("user-1", candidate, provenance, TEST_OBSERVED_AT);

        verify(contradictionOnInsertService).checkOnInsert(
                eq("user-1"), eq("fact"), any(), any(), any(), anyDouble());
    }

    @Test
    void checkOnInsertIsSkippedWhenContradictionResolutionDisabled() {
        enablementService.disable(ContradictionResolutionProcess.PROCESS_ID);

        MemoryCandidate candidate = new MemoryCandidate("fact", "User prefers dark mode", 0.9, List.of("e1"));
        Provenance provenance = createProvenance("conv-1", List.of("e1"));

        writer.writeMemory("user-1", candidate, provenance, TEST_OBSERVED_AT);

        verify(contradictionOnInsertService, never()).checkOnInsert(
                any(), any(), any(), any(), any(), anyDouble());
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private Provenance createProvenance(String conversationId, List<String> entryIds) {
        return new Provenance(
                conversationId, entryIds,
                "cursor-first", "cursor-latest",
                "test-trigger", null, null, null,
                "runtime-test", "1.0", Instant.now());
    }

    private ByteString uuidToBytes(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return ByteString.copyFrom(buffer.array());
    }
}
