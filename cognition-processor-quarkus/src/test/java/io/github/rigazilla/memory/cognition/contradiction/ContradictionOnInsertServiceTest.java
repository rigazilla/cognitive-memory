package io.github.rigazilla.memory.cognition.contradiction;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.github.chirino.memory.grpc.v1.AdminMemoriesServiceGrpc;
import io.github.chirino.memory.grpc.v1.AdminMemoryItem;
import io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest;
import io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest;
import io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse;
import io.github.rigazilla.memory.cognition.resource.LlmRetryHelper;
import io.grpc.ManagedChannel;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ContradictionOnInsertService}.
 *
 * <p>The gRPC stub, LLM detector, and retry helper are replaced with Mockito mocks so no
 * real server or LLM is required.
 *
 * <p>The primary detection path is now {@link ContradictionDetector#detectBatch}, which
 * sends all candidates in a single LLM call.  Per-pair {@link ContradictionDetector#detect}
 * is used only as a fallback when a batch result entry is missing (e.g. model truncation).
 */
@QuarkusTest
class ContradictionOnInsertServiceTest {

    @Inject
    ContradictionOnInsertService service;

    /** Unwrapped CDI bean — needed because @ApplicationScoped beans are client-proxy wrapped. */
    private ContradictionOnInsertService realService;

    private AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub mockStub;
    private ContradictionDetector mockDetector;

    @BeforeEach
    void setUp() {
        mockStub     = mock(AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub.class);
        mockDetector = mock(ContradictionDetector.class);

        realService = (ContradictionOnInsertService)
                ((io.quarkus.arc.ClientProxy) service).arc_contextualInstance();

        realService.memoriesStub = mockStub;
        realService.channel      = mock(ManagedChannel.class);
        realService.detector     = mockDetector;
        realService.llmRetryHelper = LlmRetryHelper.forTesting(1, 0, 0);
    }

    // -------------------------------------------------------------------------
    // Helper builders
    // -------------------------------------------------------------------------

    private static AdminMemoryItem buildNeighbour(String key, String content, String observedAt) {
        Struct value = Struct.newBuilder()
                .putFields("content",     Value.newBuilder().setStringValue(content).build())
                .putFields("observed_at", Value.newBuilder().setStringValue(observedAt).build())
                .putFields("confidence",  Value.newBuilder().setNumberValue(0.8).build())
                .build();
        return AdminMemoryItem.newBuilder()
                .setKey(key)
                .setValue(value)
                .addNamespace("user").addNamespace("u1").addNamespace("cognition.v1").addNamespace("preference")
                .setRevision(2)
                .build();
    }

    /** Single batch result wrapper containing results. */
    private static ContradictionBatchResult batchResult(ContradictionDetectionResponse... responses) {
        return new ContradictionBatchResult(List.of(responses));
    }

    /** Single batch result indicating no contradiction for the given 0-based index. */
    private static ContradictionDetectionResponse batchNoContradiction(int index) {
        return ContradictionDetectionResponse.create(index, false, "none", "recency", "no conflict");
    }

    /** Single batch result indicating a contradiction for the given 0-based index. */
    private static ContradictionDetectionResponse batchContradiction(int index, String strategy) {
        return ContradictionDetectionResponse.create(
                index, true, "preference_change", strategy, "they conflict");
    }

    /** Single-pair (non-batch) response helpers — used in fallback path tests. */
    private static ContradictionDetectionResponse noContradiction() {
        return ContradictionDetectionResponse.create(false, "none", "recency", "no conflict");
    }

    private static ContradictionDetectionResponse contradiction(String strategy) {
        return ContradictionDetectionResponse.create(
                true, "preference_change", strategy, "they conflict");
    }

    // -------------------------------------------------------------------------
    // Tests — happy-path batch flow
    // -------------------------------------------------------------------------

    @Test
    void noNeighboursReturned_noLlmCallMade() {
        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder().build());

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        verify(mockDetector, never()).detectBatch(any(), any(), any(), any());
        verify(mockDetector, never()).detect(any(), any(), any(), any(), any());
        verify(mockStub,     never()).putMemory(any(AdminPutMemoryRequest.class));
    }

    @Test
    void newMemoryItself_filteredOut() {
        // Search returns only the new memory — should be excluded; no LLM call.
        AdminMemoryItem selfItem = buildNeighbour("new-key", "I prefer tea", "2024-01-02T00:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder().addItems(selfItem).build());

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        verify(mockDetector, never()).detectBatch(any(), any(), any(), any());
        verify(mockDetector, never()).detect(any(), any(), any(), any(), any());
    }

    @Test
    void supersededNeighbour_filteredOut() {
        // Neighbours with status=superseded must be ignored.
        Struct supersededValue = Struct.newBuilder()
                .putFields("content",     Value.newBuilder().setStringValue("I prefer coffee").build())
                .putFields("status",      Value.newBuilder().setStringValue("superseded").build())
                .putFields("observed_at", Value.newBuilder().setStringValue("2024-01-01T00:00:00Z").build())
                .putFields("confidence",  Value.newBuilder().setNumberValue(0.7).build())
                .build();
        AdminMemoryItem supersededItem = AdminMemoryItem.newBuilder()
                .setKey("old-key").setValue(supersededValue)
                .addNamespace("user").addNamespace("u1").addNamespace("cognition.v1").addNamespace("preference")
                .setRevision(1).build();

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder().addItems(supersededItem).build());

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        verify(mockDetector, never()).detectBatch(any(), any(), any(), any());
        verify(mockDetector, never()).detect(any(), any(), any(), any(), any());
    }

    @Test
    void noContradiction_noPutMemoryCalled() {
        AdminMemoryItem neighbour = buildNeighbour("old-key", "I prefer coffee", "2024-01-01T00:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder().addItems(neighbour).build());
        when(mockDetector.detectBatch(any(), any(), any(), any()))
                .thenReturn(batchResult(batchNoContradiction(0)));

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        verify(mockDetector, times(1)).detectBatch(any(), any(), any(), any());
        verify(mockDetector, never()).detect(any(), any(), any(), any(), any());
        verify(mockStub,     never()).putMemory(any(AdminPutMemoryRequest.class));
    }

    @Test
    void contradiction_recency_newerWins_olderSuperseded() {
        // new-key has a newer timestamp — it should win; old-key should be superseded.
        AdminMemoryItem neighbour = buildNeighbour("old-key", "I prefer coffee", "2024-01-01T00:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder().addItems(neighbour).build());
        when(mockDetector.detectBatch(any(), any(), any(), any()))
                .thenReturn(batchResult(batchContradiction(0, "recency")));

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        // One detectBatch call — no per-pair detect
        verify(mockDetector, times(1)).detectBatch(any(), any(), any(), any());
        verify(mockDetector, never()).detect(any(), any(), any(), any(), any());
        // Two putMemory calls: one to mark the loser superseded, one to add supersedes to winner
        verify(mockStub, times(2)).putMemory(any(AdminPutMemoryRequest.class));
    }

    @Test
    void contradiction_coexistence_noPutMemoryCalled() {
        AdminMemoryItem neighbour = buildNeighbour("old-key", "I prefer coffee", "2024-01-01T00:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder().addItems(neighbour).build());
        when(mockDetector.detectBatch(any(), any(), any(), any()))
                .thenReturn(batchResult(batchContradiction(0, "coexistence")));

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        // Coexistence: both memories remain active — no writes
        verify(mockStub, never()).putMemory(any(AdminPutMemoryRequest.class));
    }

    @Test
    void multipleNeighbours_oneContradiction_correctCallCount() {
        AdminMemoryItem n1 = buildNeighbour("key-1", "I prefer coffee",   "2024-01-01T00:00:00Z");
        AdminMemoryItem n2 = buildNeighbour("key-2", "I enjoy green tea", "2024-01-01T06:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder()
                        .addItems(n1).addItems(n2).build());

        // index 0 contradicts, index 1 does not
        when(mockDetector.detectBatch(any(), any(), any(), any()))
                .thenReturn(batchResult(
                        batchContradiction(0, "recency"),
                        batchNoContradiction(1)));

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        // Exactly one batch call, no per-pair fallback
        verify(mockDetector, times(1)).detectBatch(any(), any(), any(), any());
        verify(mockDetector, never()).detect(any(), any(), any(), any(), any());
        // One contradiction resolved → 2 putMemory calls
        verify(mockStub, times(2)).putMemory(any(AdminPutMemoryRequest.class));
    }

    // -------------------------------------------------------------------------
    // Tests — truncation fallback path
    // -------------------------------------------------------------------------

    @Test
    void batchResultMissingIndex_fallsBackToSinglePair() {
        // Two candidates; batch returns only index 0 — index 1 is missing (model truncation).
        AdminMemoryItem n1 = buildNeighbour("key-1", "I prefer coffee",   "2024-01-01T00:00:00Z");
        AdminMemoryItem n2 = buildNeighbour("key-2", "I enjoy green tea", "2024-01-01T06:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder()
                        .addItems(n1).addItems(n2).build());

        // Batch returns only the first result
        when(mockDetector.detectBatch(any(), any(), any(), any()))
                .thenReturn(batchResult(batchNoContradiction(0)));

        // Fallback single-pair call for the missing index
        when(mockDetector.detect(any(), any(), any(), any(), any()))
                .thenReturn(noContradiction());

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        // One batch call + one single-pair fallback for the missing index
        verify(mockDetector, times(1)).detectBatch(any(), any(), any(), any());
        verify(mockDetector, times(1)).detect(any(), any(), any(), any(), any());
        verify(mockStub, never()).putMemory(any(AdminPutMemoryRequest.class));
    }

    @Test
    void batchThrows_fallsBackToPerPairForAllCandidates() {
        AdminMemoryItem n1 = buildNeighbour("key-1", "I prefer coffee",   "2024-01-01T00:00:00Z");
        AdminMemoryItem n2 = buildNeighbour("key-2", "I enjoy green tea", "2024-01-01T06:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder()
                        .addItems(n1).addItems(n2).build());

        when(mockDetector.detectBatch(any(), any(), any(), any()))
                .thenThrow(new RuntimeException("LLM timeout"));

        when(mockDetector.detect(any(), any(), any(), any(), any()))
                .thenReturn(noContradiction());

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        // Batch failed — two per-pair fallbacks, no writes
        verify(mockDetector, times(1)).detectBatch(any(), any(), any(), any());
        verify(mockDetector, times(2)).detect(any(), any(), any(), any(), any());
        verify(mockStub, never()).putMemory(any(AdminPutMemoryRequest.class));
    }

    @Test
    void batchReturnsEmptyList_fallsBackToPerPairForAllCandidates() {
        AdminMemoryItem neighbour = buildNeighbour("old-key", "I prefer coffee", "2024-01-01T00:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder().addItems(neighbour).build());

        when(mockDetector.detectBatch(any(), any(), any(), any()))
                .thenReturn(batchResult()); // model returned nothing

        when(mockDetector.detect(any(), any(), any(), any(), any()))
                .thenReturn(noContradiction());

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        verify(mockDetector, times(1)).detectBatch(any(), any(), any(), any());
        verify(mockDetector, times(1)).detect(any(), any(), any(), any(), any());
        verify(mockStub, never()).putMemory(any(AdminPutMemoryRequest.class));
    }

    // -------------------------------------------------------------------------
    // Tests — error resilience
    // -------------------------------------------------------------------------

    @Test
    void searchThrows_noExceptionPropagated() {
        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenThrow(new RuntimeException("gRPC unavailable"));

        // Must not throw — checkOnInsert is best-effort
        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        verify(mockDetector, never()).detectBatch(any(), any(), any(), any());
        verify(mockDetector, never()).detect(any(), any(), any(), any(), any());
    }

    @Test
    void singlePairFallbackThrows_processingContinues_noExceptionPropagated() {
        // Two candidates; batch truncates after index 0; single-pair fallback for index 1 throws.
        AdminMemoryItem n1 = buildNeighbour("key-1", "I prefer coffee",   "2024-01-01T00:00:00Z");
        AdminMemoryItem n2 = buildNeighbour("key-2", "I enjoy green tea", "2024-01-01T06:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder()
                        .addItems(n1).addItems(n2).build());

        // Batch returns only index 0
        when(mockDetector.detectBatch(any(), any(), any(), any()))
                .thenReturn(batchResult(batchNoContradiction(0)));

        // Fallback for index 1 throws
        when(mockDetector.detect(any(), any(), any(), any(), any()))
                .thenThrow(new RuntimeException("LLM timeout"));

        // Must not throw
        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        verify(mockStub, never()).putMemory(any(AdminPutMemoryRequest.class));
    }

    // -------------------------------------------------------------------------
    // Tests — supersedes accumulation (regression for overwrite bug)
    // -------------------------------------------------------------------------

    /**
     * Verifies that when a winner memory already carries {@code supersedes: ["key-1"]}
     * in its struct (from a prior resolution), resolving a second contradiction against
     * the same winner appends rather than overwrites — resulting in
     * {@code supersedes: ["key-1", "key-2"]}.
     *
     * <p>Setup: the search returns an existing memory ("winner-key") that already has
     * {@code supersedes: ["key-1"]} plus a new contradicting neighbour ("key-2").
     * "new-key" (the inserting memory, older timestamp) loses to "winner-key".
     * The updated winner struct written by the second {@code putMemory} call must
     * contain both loser keys.
     */
    @Test
    void multipleContradictions_accumulatesSupersedes() {
        // winner-key already superseded key-1 in a previous run — its struct reflects that.
        com.google.protobuf.ListValue existingSupersedes = com.google.protobuf.ListValue.newBuilder()
                .addValues(Value.newBuilder().setStringValue("key-1").build())
                .build();
        Struct winnerValue = Struct.newBuilder()
                .putFields("content",     Value.newBuilder().setStringValue("I prefer tea").build())
                .putFields("observed_at", Value.newBuilder().setStringValue("2024-01-03T00:00:00Z").build())
                .putFields("confidence",  Value.newBuilder().setNumberValue(0.9).build())
                .putFields("supersedes",  Value.newBuilder().setListValue(existingSupersedes).build())
                .build();
        AdminMemoryItem winnerNeighbour = AdminMemoryItem.newBuilder()
                .setKey("winner-key")
                .setValue(winnerValue)
                .addNamespace("user").addNamespace("u1").addNamespace("cognition.v1").addNamespace("preference")
                .setRevision(3)
                .build();

        // key-2 is another neighbour that will be reported as a non-contradiction
        // (only "winner-key" contradicts the new memory — and wins because it is newer).
        AdminMemoryItem loserNeighbour = buildNeighbour("key-2", "I enjoy coffee", "2024-01-01T00:00:00Z");

        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder()
                        .addItems(winnerNeighbour)
                        .addItems(loserNeighbour)
                        .build());

        // index 0 (winner-key) contradicts the new memory; index 1 (key-2) does not
        when(mockDetector.detectBatch(any(), any(), any(), any()))
                .thenReturn(batchResult(
                        batchContradiction(0, "recency"),
                        batchNoContradiction(1)));

        // new-key has an older timestamp → winner-key (newer) wins; new-key is superseded
        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer green tea", "2024-01-02T00:00:00Z", 0.8);

        // Two putMemory calls: mark new-key superseded, then update winner-key's supersedes list
        ArgumentCaptor<AdminPutMemoryRequest> captor =
                ArgumentCaptor.forClass(AdminPutMemoryRequest.class);
        verify(mockStub, times(2)).putMemory(captor.capture());

        // The second call updates the winner — its supersedes list must include both keys
        AdminPutMemoryRequest winnerUpdate = captor.getAllValues().get(1);
        assertEquals("winner-key", winnerUpdate.getKey());
        List<Value> supersedesValues = winnerUpdate.getValue()
                .getFieldsOrThrow("supersedes")
                .getListValue()
                .getValuesList();
        assertEquals(2, supersedesValues.size(), "supersedes list should contain two entries");
        assertEquals("key-1", supersedesValues.get(0).getStringValue());
        assertEquals("new-key", supersedesValues.get(1).getStringValue());
    }

    @Test
    void searchRequest_limitIsNeighboursPlusOne() {
        // The service should request k+1 results to account for the new memory itself.
        when(mockStub.searchMemories(any(AdminSearchMemoriesRequest.class)))
                .thenReturn(AdminSearchMemoriesResponse.newBuilder().build());

        service.checkOnInsert("u1", "preference", "new-key",
                "I prefer tea", "2024-01-02T00:00:00Z", 0.9);

        // Capture the request to verify limit = neighbours (5 default) + 1 = 6
        org.mockito.ArgumentCaptor<AdminSearchMemoriesRequest> captor =
                org.mockito.ArgumentCaptor.forClass(AdminSearchMemoriesRequest.class);
        verify(mockStub).searchMemories(captor.capture());

        AdminSearchMemoriesRequest req = captor.getValue();
        // default neighbours=5 → limit should be 6
        org.junit.jupiter.api.Assertions.assertEquals(6, req.getLimit());
        org.junit.jupiter.api.Assertions.assertEquals("I prefer tea", req.getQuery());
        org.junit.jupiter.api.Assertions.assertEquals("u1", req.getAsUserId());
    }
}
