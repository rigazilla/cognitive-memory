# 028 - Contradiction On-Insert Process

**Status**: Complete  
**Date**: 2026-11-10  
**Branch**: `feature/contradiction-on-insert-process`

## Overview

Converted `ContradictionOnInsertService` from a passive supporting service into a fully managed `CognitiveProcess` with independent enablement, comprehensive metrics, and REST API visibility.

## Problem Statement

`ContradictionOnInsertService` was a regular `@ApplicationScoped` service that performed real-time contradiction detection when memories were written. However:

1. **No independent enablement** - Coupled to `ContradictionResolutionProcess.PROCESS_ID` for enable/disable checks
2. **No operational metrics** - Only token metrics were tracked automatically; no visibility into checks performed, contradictions found, errors, etc.
3. **Not discoverable** - Not registered as a `CognitiveProcess`, so not visible in `/api/processes` or process management APIs
4. **Inconsistent with architecture** - Other cognitive capabilities (extraction, consolidation, etc.) were managed processes, but on-insert detection was not

## Solution

### 1. Added Operational Metrics to ContradictionOnInsertService

Added seven `AtomicLong` metrics with public getters:

- `checksPerformed` - Total number of on-insert checks triggered
- `contradictionsFound` - Total contradictions detected
- `contradictionsResolved` - Total contradictions successfully resolved
- `errors` - Total errors during processing
- `batchCallsSucceeded` - Successful batch LLM calls
- `batchCallsFailed` - Failed batch LLM calls (with fallback)
- `fallbackCallsUsed` - Single-pair fallback calls due to truncation

Metrics are incremented at appropriate points in the service logic.

### 2. Created ContradictionOnInsertProcess

New `@ApplicationScoped` class implementing `CognitiveProcess`:

```java
@ApplicationScoped
public class ContradictionOnInsertProcess implements CognitiveProcess {
    public static final String PROCESS_ID = "contradiction-on-insert";
    
    @Inject ContradictionOnInsertService contradictionOnInsertService;
    @Inject ProcessEnablementService enablementService;
    @Inject CognitionConfig cognition;
    @Inject TokenTracker tokenTracker;
    
    // CognitiveProcess implementation
}
```

**Capabilities**:
- `supportsStart()` → `false` (event-driven, triggered by memory writes)
- `supportsEnable()` → `true`
- `supportsDisable()` → `true`
- `defaultEnabled()` → `true` (preserves current behavior)

**Inspection Details**:
- Mode: `event_driven`
- Trigger point: `memory_write`
- Configuration: `neighboursChecked`
- Operational metrics: all 7 metrics from service
- LLM call statistics: batch/fallback breakdown
- Resource types: shared LLM config with batch process
- Token usage: per-resource and process-level snapshots

### 3. Updated Integration Points

**MemoryWriter**:
```java
// Before: if (enablementService.isEnabled(ContradictionResolutionProcess.PROCESS_ID))
// After:  if (enablementService.isEnabled(ContradictionOnInsertProcess.PROCESS_ID))
```

**Token Tracking** (ContradictionOnInsertService):
```java
// Before: TokenTrackingContext.call(ContradictionResolutionProcess.PROCESS_ID, ...)
// After:  TokenTrackingContext.call(ContradictionOnInsertProcess.PROCESS_ID, ...)
```

This ensures token metrics are tracked under the correct process ID.

### 4. Testing

All existing tests pass without modification (14/14 in `ContradictionOnInsertServiceTest`). The service behavior is unchanged; only the management layer and metrics were added.

## Architecture

### Process Hierarchy

```
CognitiveProcess (interface)
├── ContradictionResolutionProcess (batch, manual backfill)
└── ContradictionOnInsertProcess (real-time, event-driven)
    └── ContradictionOnInsertService (business logic)
```

Both processes share:
- Same `ContradictionDetector` service
- Same LLM configuration (`cognition.contradiction.llm`)
- Same resolution strategies (recency, confidence, coexistence)

But can be enabled/disabled independently.

### Metrics Architecture

**Operational Metrics** (manual tracking):
- Tracked in `ContradictionOnInsertService` as `AtomicLong` fields
- Exposed via public getters
- Included in process inspection API response
- **Not** exposed to Prometheus (process-specific, not system-wide)

**Token Metrics** (automatic tracking):
- Tracked by `TokenTracker` via `TokenTrackingContext.call()`
- Exposed in both process inspection API and Prometheus `/q/metrics`
- Includes input/output tokens, request counts, costs

## REST API

Process is now discoverable at:

```
GET /api/processes/contradiction-on-insert
```

Response includes:
- Process identity (id, displayName, description)
- Current state (ENABLED/DISABLED)
- Operational metrics
- LLM call statistics
- Resource configuration
- Token usage

Enable/disable via:
```
POST /api/processes/contradiction-on-insert/enable
POST /api/processes/contradiction-on-insert/disable
```

## Configuration

No new configuration properties. Process uses existing:

```yaml
cognition:
  contradiction:
    neighbours: 5  # Number of semantic neighbours to check
    llm:
      provider: ollama
      model: llama3.2:latest
      temperature: 0.0
      max-tokens: 2048
      timeout: 30s
```

## Migration Notes

**Breaking Changes**: None. Default enabled preserves current behavior.

**Enablement Check Migration**:
- Old: `enablementService.isEnabled(ContradictionResolutionProcess.PROCESS_ID)`
- New: `enablementService.isEnabled(ContradictionOnInsertProcess.PROCESS_ID)`

Users who previously disabled the batch process to disable on-insert detection must now disable both processes independently.

## Future Work

1. **Metrics Dashboard** - Add Grafana dashboard for operational metrics
2. **Adaptive Neighbours** - Dynamically adjust `k` based on contradiction rate
3. **Backpressure** - Skip on-insert checks when system is overloaded
4. **Conflict Resolution UI** - Allow users to review/override resolutions

## References

- Implementation Plan: `TODO/contradiction-on-insert-process.md`
- Service: `ContradictionOnInsertService.java`
- Process: `ContradictionOnInsertProcess.java`
- Integration: `MemoryWriter.java`
- Tests: `ContradictionOnInsertServiceTest.java`
