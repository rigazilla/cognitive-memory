# Cognitive Memory JSON Structure

## Overview

The cognition processor extracts durable memories from conversations and stores them in memory-service with a structured JSON format. This document describes the complete JSON structure of cognitive memories, including their content, metadata, and provenance tracking.

## Memory Storage Location

Memories are stored in memory-service under the following namespace pattern:

```
["user", <userId>, "cognition.v1", <memoryType>]
```

Where `<memoryType>` is one of: `fact`, `preference`, `procedure`, `problem_solution`, `decision`, `profile_context`

**Note:** The type is stored in snake_case format (e.g., `problem_solution`), not camelCase.

**Memory Type Categories:**
- **Automatic extraction (5 types)**: `fact`, `preference`, `procedure`, `problem_solution`, `decision` - extracted from conversations
- **On-demand consolidation (1 type)**: `profile_context` - created via REST API endpoint

## Core Memory Structure

Each memory is stored as a protobuf `Struct` that serializes to JSON with the following top-level fields:

```json
{
  "content": "The extracted memory statement",
  "confidence": 0.85,
  "citations": [
    "[USER] Quote from conversation",
    "[AI] Another relevant quote"
  ],
  "provenance": {
    "conversation_id": "uuid",
    "entry_ids": ["uuid1", "uuid2"],
    "event_cursors": {
      "first": "138",
      "latest": "143"
    },
    "batch_trigger": "debounce_delay",
    "source_hash": "sha256-hash",
    "evidence_base_id": "uuid",
    "evidence_base_hash": "sha256-hash",
    "runtime_id": "cognition-processor-v1",
    "runtime_version": "1.0.0-SNAPSHOT",
    "processed_at": "2026-06-19T14:40:43.050673844Z"
  }
}
```

## Field Descriptions

### Content Fields

#### `content` (string, required)
The extracted memory statement. This is a clear, standalone statement that makes sense without additional context.

**Characteristics:**
- Self-contained and understandable without conversation context
- Includes temporal markers when present (dates, times, durations)
- Includes causal relationships (both cause and effect)
- Names specific entities (people, places, organizations)
- Preserves all specific details from the conversation

**Examples:**
```json
"content": "User started working at Acme Corp in January 2023"
"content": "User prefers dark mode because bright screens cause eye strain during late-night coding"
"content": "Build failed because JAVA_HOME pointed to JDK 11 instead of JDK 17; fixed by updating .bashrc"
```

#### `confidence` (number, required)
A confidence score between 0.0 and 1.0 indicating how certain the extraction is.

**Confidence Scale:**
- **0.9 - 1.0**: Explicit, declarative statements ("I did...", "We decided...")
- **0.7 - 0.9**: Strongly stated but not fully formalized ("I think I'll...", "We should...")
- **0.4 - 0.7**: Weakly stated or inferred from speech acts (questions implying intent, hedged suggestions)
- **0.0 - 0.4**: Highly uncertain or speculative

#### `citations` (array of strings, required)
Direct quotes from the conversation that support this memory. Citations are used to:
- Provide evidence for the extracted memory
- Enable validation of extraction accuracy
- Support memory justification and audit

**Format:**
```json
"citations": [
  "[USER] A friend of mine is vegetarian",
  "[AI] Certainly! If you're looking for a vegetarian version..."
]
```

**Rules:**
- Must be exact quotes from the conversation (no paraphrasing)
- Prefixed with role: `[USER]` or `[AI]`
- At least one citation required for valid memory
- Can include multiple citations from different entries

### Provenance Fields

The `provenance` object tracks the complete origin and processing history of the memory.

#### `conversation_id` (string, required)
UUID of the conversation this memory was extracted from.

#### `entry_ids` (array of strings, required)
List of entry UUIDs that were included in the evidence pack for this extraction. These are the specific conversation messages that led to this memory.

**Usage:**
- Enables fetching full conversation context via `/api/memories/{id}/justify`
- Supports replay and reproducibility
- Provides audit trail

#### `event_cursors` (object, required)
Event stream positions that define the batch boundaries.

```json
"event_cursors": {
  "first": "138",    // First event in this batch
  "latest": "143"    // Latest event in this batch
}
```

#### `batch_trigger` (string, required)
Reason why this batch was promoted for processing.

**Possible values:**
- `"debounce_delay"` - Batch promoted after debounce timeout
- `"max_batch_age"` - Batch promoted due to age limit
- `"max_batch_size"` - Batch promoted due to size limit
- `"conversation_ended"` - Batch promoted when conversation ended

#### `source_hash` (string, optional)
SHA-256 hash of the canonicalized evidence pack used for extraction. Enables:
- Detection of duplicate processing
- Verification of extraction inputs
- Reproducibility validation

**Note:** Currently null in Phase 3A, to be implemented in later phases.

#### `evidence_base_id` (string, optional)
UUID of the compacted evidence base if consolidation was used.

**Note:** Currently null, to be implemented when evidence compaction is added.

#### `evidence_base_hash` (string, optional)
SHA-256 hash of the compacted evidence base.

**Note:** Currently null, to be implemented when evidence compaction is added.

#### `runtime_id` (string, required)
Identifier of the cognition runtime that processed this memory.

**Example:** `"cognition-processor-v1"`

#### `runtime_version` (string, required)
Version of the cognition processor that created this memory.

**Example:** `"1.0.0-SNAPSHOT"`

#### `processed_at` (string, required)
ISO 8601 timestamp when the memory was processed and created.

**Example:** `"2026-06-19T14:40:43.050673844Z"`

## Memory Types

The cognition processor works with two categories of memories:

### Automatically Extracted Memory Types (5 types)

These are extracted automatically from conversations as they happen:

### 1. Facts (`fact`)
Objective, verifiable information about the user, their environment, or their work.

**Key characteristics:**
- Always includes temporal context when present (dates, times, seasons)
- Always includes location when mentioned
- Always names specific people involved
- Extracts every specific detail (titles, names, numbers, relationships)

**Examples:**
```json
{
  "type": "fact",
  "content": "User started working at Acme Corp in January 2023",
  "confidence": 0.95,
  "citations": ["[USER] I joined Acme Corp in January 2023"]
}
```

```json
{
  "type": "fact",
  "content": "User's dog is named Oliver",
  "confidence": 0.98,
  "citations": ["[USER] My dog Oliver loves to play fetch"]
}
```

### 2. Preferences (`preference`)
User's likes, dislikes, choices, and preferred ways of working.

**Key characteristics:**
- Includes when the preference was expressed if stated
- Includes what triggered the preference if mentioned
- Captures the reasoning behind the preference

**Examples:**
```json
{
  "type": "preference",
  "content": "User prefers dark mode because bright screens cause eye strain during late-night coding",
  "confidence": 0.85,
  "citations": [
    "[USER] I always use dark mode",
    "[USER] Bright screens hurt my eyes when I code late at night"
  ]
}
```

### 3. Procedures (`procedure`)
Step-by-step processes, workflows, or methodologies the user follows.

**Key characteristics:**
- Captures sequential steps
- Includes tools and commands used
- Preserves order and dependencies

**Examples:**
```json
{
  "type": "procedure",
  "content": "User's deployment workflow: 1) Run tests locally, 2) Create PR, 3) Wait for CI, 4) Merge to main, 5) Deploy to staging, 6) Verify, 7) Deploy to production",
  "confidence": 0.90,
  "citations": ["[USER] My deployment process is..."]
}
```

### 4. Problem Solutions (`problem_solution`)
Issues encountered and their resolutions, including troubleshooting steps.

**Key characteristics:**
- Always includes the cause of the problem
- Always includes what led to the resolution
- Captures both the problem and solution

**Examples:**
```json
{
  "type": "problem_solution",
  "content": "Build failed because JAVA_HOME variable pointed to JDK 11 instead of JDK 17; fixed by updating .bashrc to export JAVA_HOME=/usr/lib/jvm/java-17",
  "confidence": 0.92,
  "citations": [
    "[USER] My build is failing with 'unsupported class file version'",
    "[AI] This error occurs when JAVA_HOME points to an older JDK",
    "[USER] I updated .bashrc and now it works"
  ]
}
```

### 5. Decisions (`decision`)
Choices made and their rationale, including trade-offs considered.

**Key characteristics:**
- Always includes the reason or motivation behind the decision
- Includes what alternatives were considered if mentioned
- Captures trade-offs and constraints

**Examples:**
```json
{
  "type": "decision",
  "content": "Team chose PostgreSQL over MongoDB because the application requires ACID transactions for financial data, despite MongoDB's better horizontal scaling",
  "confidence": 0.88,
  "citations": [
    "[USER] We're deciding between PostgreSQL and MongoDB",
    "[USER] We need ACID guarantees for financial transactions",
    "[USER] We went with PostgreSQL even though MongoDB scales better"
  ]
}
```

### On-Demand Consolidated Memory Type (1 type)

This memory type is created on-demand via REST API and consolidates existing memories:

#### 6. Profile Context (`profile_context`)
A consolidated snapshot of the user's profile, goals, and preferences. Created by calling the profile consolidation endpoint.

**Key characteristics:**
- Not extracted from conversations - consolidates existing memories
- Created via `POST /api/consolidate/{userId}`
- Stored with key `"latest"` (overwrites previous snapshot)
- Contains 3 structured sections with provenance

**Structure:**
```json
{
  "kind": "profile_context_snapshot",
  "version": "profile_context.v1",
  "user_id": "alice",
  "generated_at": "2026-06-19T14:40:43.050673844Z",
  "content": "Full markdown-formatted profile text",
  "sections": {
    "profile_snapshot": {
      "confidence": 0.85,
      "source_memory_keys": ["key1", "key2"]
    },
    "active_goals": {
      "confidence": 0.90,
      "source_memory_keys": ["key3", "key4"]
    },
    "preferences": {
      "confidence": 0.88,
      "source_memory_keys": ["key5", "key6"]
    }
  }
}
```

**Sections:**
- **profile_snapshot**: Stable identity facts (name, role, background, expertise)
- **active_goals**: Current projects, tasks, deadlines, blockers, decisions
- **preferences**: Communication style, coding preferences, tool preferences, workflows

**Example:**
```json
{
  "type": "profile_context",
  "content": "# User Profile\n\n## Profile Snapshot\nAlice is a senior software engineer...\n\n## Active Goals\n- Complete migration to Quarkus...\n\n## Preferences\n- Prefers dark mode for coding...",
  "sections": {
    "profile_snapshot": {
      "confidence": 0.85,
      "source_memory_keys": ["fact-uuid-1", "fact-uuid-2"]
    },
    "active_goals": {
      "confidence": 0.90,
      "source_memory_keys": ["decision-uuid-1", "fact-uuid-3"]
    },
    "preferences": {
      "confidence": 0.88,
      "source_memory_keys": ["preference-uuid-1", "preference-uuid-2"]
    }
  }
}
```

**Note:** Profile context memories are stored at namespace `["user", <userId>, "cognition.v1", "profile_context"]` with key `"latest"`.

**API Usage:**
```bash
# Trigger profile consolidation for a user
curl -X POST http://localhost:8090/api/consolidate/alice

# Response:
{
  "status": "success",
  "message": "Profile consolidated successfully",
  "userId": "alice",
  "generatedAt": "2026-06-19T14:40:43.050673844Z",
  "sectionsCount": 3
}
```

## Complete Example

Here's a complete memory as stored in memory-service:

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "namespace": ["user", "alice", "cognition.v1", "decision"],
  "key": "abc-123",
  "value": {
    "content": "User decided to consider a vegetarian version of the apple pie for a friend",
    "confidence": 0.8,
    "citations": [
      "[USER] A friend of mine is vegetarian",
      "[AI] Certainly! If you're looking for a vegetarian version of an apple pie..."
    ],
    "provenance": {
      "conversation_id": "abb770b8-f1ce-41fa-8629-bea073796c0c",
      "entry_ids": [
        "f0fb3021-718b-4fb6-b1a3-d48a11af47c3",
        "acf217ee-6ffe-42cf-ada6-953f26295480"
      ],
      "event_cursors": {
        "first": "138",
        "latest": "143"
      },
      "batch_trigger": "debounce_delay",
      "source_hash": null,
      "evidence_base_id": null,
      "evidence_base_hash": null,
      "runtime_id": "cognition-processor-v1",
      "runtime_version": "1.0.0-SNAPSHOT",
      "processed_at": "2026-06-19T14:40:43.050673844Z"
    }
  },
  "createdAt": "2026-06-19T14:40:43.050673844Z"
}
```

## Validation Rules

### For Automatically Extracted Memories (5 types)

A memory candidate is considered **valid** for storage if:

1. ✅ `type` is not blank and is one of the five allowed types (`fact`, `preference`, `procedure`, `problem_solution`, `decision`)
2. ✅ `content` is not blank
3. ✅ `confidence` is greater than 0.0
4. ✅ `citations` array is not empty

Invalid candidates are filtered out during extraction and logged for debugging.

### For Profile Context Memories

Profile context memories have different validation:

1. ✅ `kind` must be `"profile_context_snapshot"`
2. ✅ `version` must be `"profile_context.v1"`
3. ✅ `content` must not be blank (contains full markdown profile)
4. ✅ `sections` must contain at least one of: `profile_snapshot`, `active_goals`, `preferences`
5. ✅ Each section must have `confidence` between 0.0 and 1.0
6. ✅ Each section must have `source_memory_keys` array (can be empty)

## Memory Justification

To retrieve the complete context behind a memory (including full conversation entries), use the Memory Justification API:

```bash
GET /api/memories/{memoryId}/justify
```

This expands the `entry_ids` from provenance into full entry content, showing exactly what conversation led to the memory extraction.

See [Memory Justification API](api/memory-justification.md) for details.

## Implementation Details

### Java Models

- **MemoryCandidate**: `io.github.rigazilla.memory.cognition.extraction.MemoryCandidate`
- **Provenance**: `io.github.rigazilla.memory.cognition.model.Provenance`
- **DurableExtractionResponse**: `io.github.rigazilla.memory.cognition.extraction.DurableExtractionResponse`

### Storage

Memories are written via gRPC using `AdminMemoriesServiceGrpc.putMemory()` with:
- Namespace-based access control
- On-behalf-of authorization (`RequestActor.onBehalfOfUserId`)
- Protobuf Struct serialization

### Extraction

Memories are extracted using LLM-based analysis with:
- System prompt: `prompts/durable-extractor-system.md`
- Verification: `prompts/durable-verifier-system.md`
- Batched extraction of all 5 types in single LLM call

## Related Documentation

- [Memory Cognition Architecture](https://github.com/chirino/memory-service/blob/main/docs/memory-cognition.md)
- [Enhancement 099: Quarkus Cognition Processor](https://github.com/chirino/memory-service/blob/main/docs/enhancements/099-quarkus-cognition-processor.md)
- [Memory Justification API](api/memory-justification.md)
- [Provenance Tracking Design](../DONE/006-provenance-tracking.md)
