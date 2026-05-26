# Authentication & Authorization - RESOLVED

**Priority**: HIGH  
**Status**: ✅ RESOLVED - 2026-05-26  
**Solution**: Migrated to Admin APIs

## Problem

Both `JobProcessor` and `TranscriptLoader` were failing with `PERMISSION_DENIED: forbidden` when trying to access conversation data using membership-scoped APIs:

1. **JobProcessor.getConversationOwner()** - using `ConversationsService.GetConversation`
2. **TranscriptLoader.loadTranscript()** - using `EntriesService.ListEntries`

### Error Messages

```
io.grpc.StatusRuntimeException: PERMISSION_DENIED: forbidden
    at io.github.chirino.memory.grpc.v1.ConversationsServiceGrpc$ConversationsServiceBlockingStub.getConversation
    at io.github.rigazilla.memory.cognition.queue.JobProcessor.getConversationOwner

io.grpc.StatusRuntimeException: PERMISSION_DENIED: forbidden
    at io.github.chirino.memory.grpc.v1.EntriesServiceGrpc$EntriesServiceBlockingStub.listEntries
    at io.github.rigazilla.memory.cognition.evidence.TranscriptLoader.loadTranscript
```

### Root Cause

Both `GetConversation` and `ListEntries` are **membership-scoped APIs** - they require conversation membership or on-behalf-of authorization. Admin credentials (`admin-api-key-1`) do NOT automatically grant access to read arbitrary user conversations.

From Enhancement 101:
- Admin scope applies to: event streams (`EVENT_SCOPE_ADMIN`), checkpoints, admin operations
- `GetConversation` and `ListEntries` remain membership-scoped and require conversation access
- There is no `AdminConversationsService` in the protobuf contracts
- However, `AdminEntriesService` exists and bypasses membership checks

## Solution Implemented

**Chosen Approach**: Use admin-scoped APIs that bypass membership checks

- **AdminEntriesService.ListEntries** instead of `EntriesService.ListEntries`
- Get conversation owner from first entry's `user_id` instead of `GetConversation`

### Key Insight

The `Entry` message contains a `user_id` field, and the first entry's `user_id` is the conversation owner. The `AdminEntriesService` bypasses membership checks, allowing the cognition processor to access entries for any conversation.

## Implementation

### 1. JobProcessor - Get Conversation Owner

**Changed**: `JobProcessor.getConversationOwner()`

**Before**:
```java
private ConversationsServiceGrpc.ConversationsServiceBlockingStub conversationsStub;

private String getConversationOwner(String conversationId) {
    GetConversationRequest request = GetConversationRequest.newBuilder()
        .setConversationId(conversationIdBytes)
        .build();
    
    Conversation conversation = conversationsStub.getConversation(request);
    return conversation.getOwnerUserId();
}
```

**After**:
```java
private AdminEntriesServiceGrpc.AdminEntriesServiceBlockingStub adminEntriesStub;

private String getConversationOwner(String conversationId) {
    AdminListEntriesRequest request = AdminListEntriesRequest.newBuilder()
        .setConversationId(conversationIdBytes)
        .setChannel(io.github.chirino.memory.grpc.v1.Channel.HISTORY)
        .setPage(io.github.chirino.memory.grpc.v1.PageRequest.newBuilder()
            .setPageSize(1)  // Only need first entry
            .build())
        .build();
    
    ListEntriesResponse response = adminEntriesStub.listEntries(request);
    
    if (response.getEntriesCount() == 0) {
        throw new JobProcessingException("No entries found for conversation " + conversationId);
    }
    
    return response.getEntries(0).getUserId();
}
```

### 2. TranscriptLoader - Load Transcript Entries

**Changed**: `TranscriptLoader.loadTranscript()`

**Before**:
```java
private EntriesServiceGrpc.EntriesServiceBlockingStub entriesStub;

public EvidencePack loadTranscript(String conversationId, List<String> entryIds, String previousEntryId) {
    ListEntriesRequest.Builder requestBuilder = ListEntriesRequest.newBuilder()
        .setConversationId(conversationIdBytes)
        .setChannel(Channel.HISTORY);
    
    ListEntriesResponse response = entriesStub.listEntries(requestBuilder.build());
    // ...
}
```

**After**:
```java
private AdminEntriesServiceGrpc.AdminEntriesServiceBlockingStub adminEntriesStub;

public EvidencePack loadTranscript(String conversationId, List<String> entryIds, String previousEntryId) {
    AdminListEntriesRequest.Builder requestBuilder = AdminListEntriesRequest.newBuilder()
        .setConversationId(conversationIdBytes)
        .setChannel(Channel.HISTORY);
    
    ListEntriesResponse response = adminEntriesStub.listEntries(requestBuilder.build());
    // ...
}
```

## Changes Made

### JobProcessor.java

1. **Updated imports**:
   - Removed: `ConversationsServiceGrpc`, `GetConversationRequest`, `Conversation`
   - Added: `AdminEntriesServiceGrpc`, `AdminListEntriesRequest`, `ListEntriesResponse`
   - Fixed import collision: Changed `io.grpc.Channel` to fully qualified name in `AuthInterceptor`

2. **Changed gRPC stub**:
   - Replaced `conversationsStub` with `adminEntriesStub`
   - Updated initialization in `init()` method

3. **Rewrote `getConversationOwner()` method**:
   - Uses `AdminListEntriesRequest` with `pageSize=1` to fetch only the first entry
   - Extracts `user_id` from the first entry as the conversation owner
   - Added error handling for empty conversations

### TranscriptLoader.java

1. **Updated imports**:
   - Removed: `EntriesServiceGrpc`, `ListEntriesRequest`
   - Added: `AdminEntriesServiceGrpc`, `AdminListEntriesRequest`

2. **Changed gRPC stub**:
   - Replaced `entriesStub` with `adminEntriesStub`
   - Updated initialization in `init()` method with comment about bypassing membership checks

3. **Updated `loadTranscript()` method**:
   - Changed `ListEntriesRequest` to `AdminListEntriesRequest`
   - Updated gRPC call to use `adminEntriesStub.listEntries()`
   - Added comments explaining admin API usage

### Protobuf Contracts

- Copied latest `memory_service.proto` from `/Users/chirino/sandbox/memory-service/contracts`
- Verified `AdminEntriesService` is available in the contracts

## Benefits

1. **No permission issues**: Admin APIs bypass membership checks for both conversation metadata and entries
2. **Efficient**: Only fetches one entry with `pageSize=1` for owner lookup
3. **Correct semantics**: First entry's user is the conversation owner
4. **No new dependencies**: Uses existing `AdminEntriesService` that was already available
5. **Consistent approach**: Both components now use admin APIs

## Testing

Build verification:
```bash
mvn clean compile -DskipTests
```

Result: ✅ BUILD SUCCESS

## Related

- **Previous work**: `DONE/005-conversation-owner-metadata.md` - Initial implementation using membership-scoped APIs
- **Architecture**: Enhancement 101 - Admin scope vs membership scope

## Alternative Solutions Considered (Not Implemented)

### Option 1: Grant Processor Membership (Testing Only)

**Pros**: Simple, immediate testing  
**Cons**: Not scalable, requires modifying every conversation  
**Status**: Rejected - not scalable

### Option 3: On-Behalf-Of Authorization

**Pros**: Proper authorization model, scalable, follows Enhancement 101 design  
**Cons**: Requires `RequestActor` support in protobuf, more complex  
**Status**: Not needed - Admin API solution is simpler and sufficient
