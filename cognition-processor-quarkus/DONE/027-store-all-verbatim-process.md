# 027 — Store-All Verbatim Cognitive Process

**Issue**: https://github.com/rigazilla/cognitive-memory/issues/58  
**Branch**: `feature/issue-58-store-all-process`  
**Status**: Complete

## Summary

Implements a `store-all` cognitive process that stores every conversation entry verbatim into memory-service. It serves as a LOCOMO benchmark baseline: run it alongside (or instead of) `durable-memory-extraction` to compare LLM-extracted memories against raw transcript storage.

The process is **disabled by default** and is purely event-driven — it produces one memory per entry event, with no LLM calls, no salience filtering, and no debounce window.

## Design

### New classes

| Class | Package | Role |
|---|---|---|
| `ConversationOwnerResolver` | `grpc` | Shared `@ApplicationScoped` bean. Resolves a conversation's `userId` via `AdminConversationsService` gRPC `getConversation()`. Reused by both cognitive processes. |
| `StoreAllService` | `storeall` | Core logic. Extracts entry text from the event JSON payload (`content[0].text` for plain history; `Completed` event structure for `history/lc4j`). Builds a `MemoryCandidate` of type `"verbatim"` and writes it via `MemoryWriter`. |
| `StoreAllProcess` | `storeall` | `CognitiveProcess` adapter. `defaultEnabled() → false`, `supportsStart() → false`. Injects `GrpcAdminEventClient` to expose live stream stats in `inspect()`. |

### Memory shape

```
type:        "verbatim"
content:     "[ROLE] <entry text>"   (role prefix: USER / ASSISTANT)
confidence:  1.0
citations:   [ entryId ]
observed_at: createdAt from event payload, fallback Instant.now() with WARN log
userId:      resolved via ConversationOwnerResolver
```

### Event gate wiring

`GrpcAdminEventClient.handleEvent()` already had the durable-memory gate block. The store-all gate is appended immediately after it, following the same pattern:

```java
// store-all gate
if (enablementService.isEnabled(StoreAllProcess.PROCESS_ID)) {
    storeAllService.processEvent(event);
}
```

Both gates are independent — they can run simultaneously if both processes are enabled, though the intended benchmark use-case is to enable exactly one at a time.

### inspect() details

`StoreAllProcess.inspect()` surfaces:

| Key | Source |
|---|---|
| `eventStreamConnected` | `GrpcAdminEventClient.isConnected()` |
| `eventsAccepted` | `GrpcAdminEventClient.getEventCount()` |
| `eventsProcessed` | `StoreAllService.getEventsProcessed()` |
| `memoriesWritten` | `StoreAllService.getMemoriesWritten()` |
| `memoriesSkipped` | `eventsProcessed - memoriesWritten` |
| `llmCalls` | Always `0` (no LLM) |
| `memoryType` | Always `"verbatim"` |

Key name `eventsAccepted` matches `DurableMemoryExtractionProcess` and `TemporalMetadataEnrichmentProcess` for comparable benchmark observability.

## Bug fixes discovered during first run

### `ContextNotActiveException` in `ContradictionOnInsertService`

`ContradictionDetector` is a `@RequestScoped` LangChain4j AI service. `checkOnInsert()` is called from the gRPC executor thread which has no CDI request context. The store-all process exposed this by writing memories at much higher throughput than the durable extractor, triggering more concurrent contradiction checks.

**Fix**: added `@ActivateRequestContext` to `ContradictionOnInsertService.checkOnInsert()`.

### Contradiction detection running on verbatim memories

`MemoryWriter.write()` unconditionally called `checkOnInsert()` after every write — including verbatim memories. Raw transcripts cannot contradict each other and the store-all process is intentionally LLM-free.

**Fix**: `ContradictionResolutionProcess` now exposes a full enable/disable knob (`defaultEnabled() = true`, wired via `ProcessEnablementService`). `MemoryWriter` guards the `checkOnInsert()` call behind `enablementService.isEnabled("contradiction-resolution")`. Disabling contradiction-resolution makes store-all completely LLM-free.

## Testing

| Test class | Type | Cases |
|---|---|---|
| `StoreAllServiceTest` | Pure JUnit 5 | 15 — role prefixing, text extraction for both event structures, `observed_at` fallback, `MemoryWriter` argument capture |
| `StoreAllProcessTest` | `@QuarkusTest` | 11 — registration, enablement state, `inspect()` field assertions for every key |
| `StoreAllEnablementGate` (nested in `GrpcAdminEventClientTest`) | `@QuarkusTest` | 5 — gate fires only when enabled, does not fire when disabled, independent of durable gate |
| `ContradictionResolutionProcessTest` | `@QuarkusTest` | 10 — capability flags, state transitions, inspect() state reflection |
| `MemoryWriterContradictionGateTest` | `@QuarkusTest` | 2 — checkOnInsert called when enabled, skipped when disabled |

**Total: 549 tests, 0 checkstyle violations, 0 SpotBugs violations.**

## Benchmark usage

For a fully LLM-free verbatim baseline run, disable both LLM-dependent processes:

```
POST /api/processes/durable-memory-extraction/disable
POST /api/processes/contradiction-resolution/disable
POST /api/processes/store-all/enable
```

To switch back to the durable extraction mode:

```
POST /api/processes/store-all/disable
POST /api/processes/contradiction-resolution/enable
POST /api/processes/durable-memory-extraction/enable
```

Both processes appear in the process registry at `GET /api/processes`.
