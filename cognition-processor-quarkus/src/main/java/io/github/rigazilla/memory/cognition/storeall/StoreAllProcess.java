package io.github.rigazilla.memory.cognition.storeall;

import io.github.rigazilla.memory.cognition.enablement.ProcessEnablementService;
import io.github.rigazilla.memory.cognition.event.GrpcAdminEventClient;
import io.github.rigazilla.memory.cognition.process.CognitiveProcess;
import io.github.rigazilla.memory.cognition.process.ManagedProcessInspection;
import io.github.rigazilla.memory.cognition.process.ManagedProcessState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Managed process adapter for the naive store-all baseline.
 *
 * <p>Stores every conversation entry verbatim into memory-service — no salience
 * filtering, no LLM extraction — so it can be used as a LOCOMO benchmark baseline
 * to compare against {@code durable-memory-extraction}.
 *
 * <p>Disabled by default so it does not interfere with normal operation.
 */
@ApplicationScoped
public class StoreAllProcess implements CognitiveProcess {

    public static final String PROCESS_ID = "store-all";

    @Inject
    ProcessEnablementService enablementService;

    @Inject
    StoreAllService storeAllService;

    @Inject
    GrpcAdminEventClient eventClient;

    @Override
    public String id() {
        return PROCESS_ID;
    }

    @Override
    public String displayName() {
        return "Store-All Baseline";
    }

    @Override
    public String description() {
        return "Naive baseline: stores every conversation entry verbatim, no extraction. "
                + "Use for LOCOMO benchmark comparison against durable-memory-extraction.";
    }

    @Override
    public boolean supportsStart() {
        return false;
    }

    @Override
    public boolean supportsEnable() {
        return true;
    }

    @Override
    public boolean supportsDisable() {
        return true;
    }

    /**
     * Disabled by default — must be explicitly enabled before use.
     */
    @Override
    public boolean defaultEnabled() {
        return false;
    }

    @Override
    public ManagedProcessState state() {
        return enablementService.isEnabled(id())
                ? ManagedProcessState.ENABLED : ManagedProcessState.DISABLED;
    }

    @Override
    public void enable() {
        enablementService.enable(id());
    }

    @Override
    public void disable() {
        enablementService.disable(id());
    }

    @Override
    public ManagedProcessInspection inspect() {
        Map<String, Object> details = new LinkedHashMap<>();
        // Stream-level health (shared with durable-memory-extraction)
        details.put("eventStreamConnected", eventClient.isConnected());
        details.put("eventsAccepted", eventClient.getEventCount());
        // Store-all specific counters
        details.put("eventsProcessed", storeAllService.getEventsProcessed());
        details.put("memoriesWritten", storeAllService.getMemoriesWritten());
        details.put("memoriesSkipped",
                storeAllService.getEventsProcessed() - storeAllService.getMemoriesWritten());
        // No LLM resources — verbatim storage only
        details.put("llmCalls", 0);
        details.put("memoryType", StoreAllService.MEMORY_TYPE);
        return new ManagedProcessInspection(
                id(),
                displayName(),
                description(),
                state(),
                details);
    }
}
