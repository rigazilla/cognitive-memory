# 023 - Entity and Topic Metadata Enrichment

## Summary

Implemented an async background `CognitiveProcess` that traverses all existing
durable memories stored in memory-service and enriches each with two new
structured metadata fields: `entities` (named entities with types) and `topics`
(hierarchical semantic topics). Extraction is performed by an LLM using the
existing `"memory"` named model.

## Motivation

Memory metadata previously lacked structured entity and topic information,
limiting the ability to query memories by entity or topic and preventing
knowledge-graph construction over stored memories.

## Design

### Process Registration

`MetadataEnrichmentProcess` is annotated `@ApplicationScoped` and implements
`CognitiveProcess`. It is auto-discovered by `CognitiveProcessRegistry` via
CDI `Instance<CognitiveProcess>` injection, requiring no manual registration.

- **ID**: `metadata-enrichment`
- **`start()`**: triggers one enrichment pass
- **`enable()` / `disable()`**: not supported — no persistent on/off state

### Enrichment Flow

1. `MetadataEnrichmentService.startEnrichmentAsync()` submits `runEnrichment()`
   to `CompletableFuture.runAsync()` and returns immediately (non-blocking).
2. `runEnrichment()` calls `listNamespaces(namespace_prefix=["user"], max_depth=4)`
   to discover all `["user", userId, "cognition.v1", memoryType]` namespaces.
   `profile_context` namespaces are skipped.
3. For each namespace, `enrichNamespace()` pages through all memories using the
   `after_cursor` pattern (`limit=50` per page).
4. For each `AdminMemoryItem`, `enrichMemory()`:
   - Skips memories that already have an `entities` field (idempotent).
   - Skips memories with blank `content`.
   - Activates the CDI request context on the fork-join pool thread.
   - Calls `MetadataExtractor.extract(memoryType, content)`.
   - Writes back the updated `Struct` via `AdminPutMemoryRequest` with
     `expected_revision` for optimistic locking.

### CDI Request Context Pattern

LangChain4j AI services are `@RequestScoped` beans. `CompletableFuture.runAsync()`
runs on the JVM common fork-join pool where no CDI request context is active.
The fix follows the same pattern as `JobProcessor.processJob()`:

```java
ManagedContext requestContext = Arc.container().requestContext();
if (!requestContext.isActive()) {
    requestContext.activate();
}
try {
    // LLM call
} finally {
    if (requestContext.isActive()) {
        requestContext.terminate();
    }
}
```

### Metadata Format

**`entities`** — protobuf `ListValue` of `Struct` objects:
```json
[{"name": "Python", "type": "technology"}, {"name": "AWS", "type": "technology"}]
```

**`topics`** — protobuf `ListValue` of strings:
```json
["programming", "cloud/aws", "deployment"]
```

## Implementation Details

### LLM Extraction

`MetadataExtractor` is a LangChain4j `@RegisterAiService` using the `"memory"`
named model. It accepts the memory type and content, and returns a typed
`MetadataExtractionResponse` record containing `List<ExtractedEntity>` and
`List<String>` topics.

The system prompt (`prompts/metadata-extractor-system.md`) constrains the LLM to:
- Extract only explicitly mentioned named entities (no inference)
- Use six valid entity types: `technology`, `organization`, `person`, `location`, `product`, `concept`
- Return hierarchical topics using slash notation (e.g. `"cloud/aws"`, `"programming/scripting"`)
- Limit output to 5 entities and 5 topics per memory
- Return empty arrays when nothing is found — never hallucinate

### Progress Tracking

`MetadataEnrichmentService` exposes live counters via `AtomicInteger`:
- `processed` — total memories visited
- `enriched` — memories successfully enriched
- `errors` — per-memory failures (logged and isolated, do not abort the run)

These are surfaced through `MetadataEnrichmentProcess.inspect()` alongside the
current run `status` and `lastRunTime`.

### SpotBugs

Added `SIC_INNER_SHOULD_BE_STATIC_ANON` exclusion to `spotbugs-exclude.xml` for
the inline `DefaultExtractorLlmConfig` class in `MetadataEnrichmentProcess`. This
follows the same pre-existing pattern used by `DurableMemoryExtractionProcess`.

## Validation

Compilation validated with:

```bash
./mvnw clean compile
```

Compilation completed successfully with exit code `0`.

## Files Added or Updated

### Added

- `src/main/java/io/github/rigazilla/memory/cognition/metadata/ExtractedEntity.java`
- `src/main/java/io/github/rigazilla/memory/cognition/metadata/MetadataExtractionResponse.java`
- `src/main/java/io/github/rigazilla/memory/cognition/metadata/MetadataExtractor.java`
- `src/main/java/io/github/rigazilla/memory/cognition/metadata/MetadataEnrichmentService.java`
- `src/main/java/io/github/rigazilla/memory/cognition/process/MetadataEnrichmentProcess.java`
- `src/main/resources/prompts/metadata-extractor-system.md`

### Updated

- `spotbugs-exclude.xml` — added `SIC_INNER_SHOULD_BE_STATIC_ANON` exclusion

## Current Limitations

- There is no automatic enrichment of newly created memories. The process is a
  one-shot retroactive pass triggered manually via `POST /api/processes/metadata-enrichment/start`.
- If the LLM call fails for a memory, that memory is skipped and counted in
  `errors`, but is not retried. Re-running `start` will not re-attempt it because
  the idempotency check skips memories that already have an `entities` field.
  Memories that were skipped due to errors will be retried on the next run since
  the field was never written.
- `enable` and `disable` are not implemented — there is no persistent background
  scheduling.

## Future Work

- Incremental enrichment: automatically enrich new memories as they are written
  by hooking into the durable memory extraction pipeline.
- Retry strategy: add configurable back-off for transient LLM failures.
- Nested entity filtering: expose `entities.type` and `entities.name` as
  queryable filter paths once the episodic store indexes nested struct fields.
