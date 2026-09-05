package io.github.rigazilla.memory.cognition.consolidation;

import com.google.protobuf.ListValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Timestamp;
import com.google.protobuf.Value;
import io.github.chirino.memory.grpc.v1.AdminMemoriesServiceGrpc;
import io.github.chirino.memory.grpc.v1.AdminMemoryItem;
import io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest;
import io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse;
import io.github.chirino.memory.grpc.v1.MemoryItem;
import io.github.rigazilla.memory.cognition.extraction.MemoryCandidate;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.quarkus.arc.ClientProxy;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ExactMatchDuplicateDetector refactored for Quarkus.
 */
@QuarkusTest
class ExactMatchDuplicateDetectorTest {

    @Inject
    ExactMatchDuplicateDetector detector;

    private AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub mockStub;

    @BeforeEach
    void setUp() {
        mockStub = mock(AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub.class);
        ExactMatchDuplicateDetector target = ClientProxy.unwrap(detector);
        target.memoriesStub = mockStub;
    }

    @Test
    void findDuplicates_NoneFound_ReturnsEmptyList() {
        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder().build());

        MemoryCandidate candidate = new MemoryCandidate(
                "fact", "User prefers Python", 0.9, List.of("E1: citation"));

        List<MemoryItem> result = detector.findDuplicates("user-1", candidate);

        assertTrue(result.isEmpty());
    }

    @Test
    void findDuplicates_ExactMatch_ReturnsItem() {
        String content = "User prefers Python";

        Struct value = Struct.newBuilder()
                .putFields("content", Value.newBuilder().setStringValue(content).build())
                .putFields("confidence", Value.newBuilder().setNumberValue(0.8).build())
                .putFields("citations", Value.newBuilder()
                        .setListValue(ListValue.newBuilder().build()).build())
                .build();

        AdminMemoryItem adminItem = AdminMemoryItem.newBuilder()
                .setKey("existing-key-1")
                .setValue(value)
                .setRevision(3)
                .setArchived(false)
                .setCreatedAt(Timestamp.newBuilder().setSeconds(1000).build())
                .build();

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder()
                        .addItems(adminItem).build());

        MemoryCandidate candidate = new MemoryCandidate(
                "fact", content, 0.9, List.of("E1: citation"));

        List<MemoryItem> result = detector.findDuplicates("user-1", candidate);

        assertEquals(1, result.size());
        assertEquals("existing-key-1", result.get(0).getKey());
        assertEquals(3, result.get(0).getRevision());
    }

    @Test
    void findDuplicates_FuzzyMatchDifferentContent_FilteredOut() {
        Struct value = Struct.newBuilder()
                .putFields("content", Value.newBuilder()
                        .setStringValue("User likes Python").build())
                .build();

        AdminMemoryItem adminItem = AdminMemoryItem.newBuilder()
                .setKey("other-key")
                .setValue(value)
                .setRevision(1)
                .setArchived(false)
                .setCreatedAt(Timestamp.newBuilder().setSeconds(1000).build())
                .build();

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder()
                        .addItems(adminItem).build());

        MemoryCandidate candidate = new MemoryCandidate(
                "fact", "User prefers Python", 0.9, List.of("E1: citation"));

        List<MemoryItem> result = detector.findDuplicates("user-1", candidate);

        assertTrue(result.isEmpty());
    }

    @Test
    void findDuplicates_SearchThrows_ReturnsEmptyList() {
        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenThrow(new RuntimeException("network error"));

        MemoryCandidate candidate = new MemoryCandidate(
                "fact", "User prefers Python", 0.9, List.of("E1: citation"));

        List<MemoryItem> result = detector.findDuplicates("user-1", candidate);

        assertTrue(result.isEmpty());
    }

    @Test
    void findDuplicates_PermissionDenied_ReturnsEmptyList() {
        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.PERMISSION_DENIED));

        MemoryCandidate candidate = new MemoryCandidate(
                "fact", "User prefers Python", 0.9, List.of("E1: citation"));

        List<MemoryItem> result = detector.findDuplicates("user-1", candidate);

        assertTrue(result.isEmpty(), "PERMISSION_DENIED must still fail-open (return empty list)");
    }

    @Test
    void findDuplicates_Unavailable_ReturnsEmptyList() {
        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.UNAVAILABLE));

        MemoryCandidate candidate = new MemoryCandidate(
                "fact", "User prefers Python", 0.9, List.of("E1: citation"));

        List<MemoryItem> result = detector.findDuplicates("user-1", candidate);

        assertTrue(result.isEmpty(), "UNAVAILABLE must still fail-open (return empty list)");
    }
}
