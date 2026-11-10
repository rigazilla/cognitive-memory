package io.github.rigazilla.memory.cognition.contradiction;

import io.github.rigazilla.memory.cognition.config.CognitionConfig;
import io.github.rigazilla.memory.cognition.enablement.ProcessEnablementService;
import io.github.rigazilla.memory.cognition.process.CognitiveProcess;
import io.github.rigazilla.memory.cognition.process.ManagedProcessInspection;
import io.github.rigazilla.memory.cognition.process.ManagedProcessState;
import io.github.rigazilla.memory.cognition.resource.DefaultLlmResourceConfiguration;
import io.github.rigazilla.memory.cognition.resource.ResourceRequirements;
import io.github.rigazilla.memory.cognition.token.TokenTracker;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Managed {@link CognitiveProcess} adapter for real-time contradiction detection
 * triggered at memory-insertion time.
 *
 * <p>Registered automatically via CDI and discoverable at
 * {@code GET /api/processes/contradiction-on-insert}.
 *
 * <p>This process is event-driven (triggered by memory writes) and cannot be started
 * manually. It can be enabled/disabled independently from the batch
 * {@link ContradictionResolutionProcess}.
 *
 * <h2>Relationship with ContradictionResolutionProcess</h2>
 * <ul>
 *   <li><b>On-Insert</b> (this process): Real-time detection during memory writes</li>
 *   <li><b>Batch</b> ({@link ContradictionResolutionProcess}): Manual backfill/cleanup</li>
 * </ul>
 *
 * <p>Both processes share the same LLM configuration, detector service, and resolution
 * strategies, but can be enabled/disabled independently.
 */
@ApplicationScoped
public class ContradictionOnInsertProcess implements CognitiveProcess {

    private static final Logger LOG = Logger.getLogger(ContradictionOnInsertProcess.class);
    public static final String PROCESS_ID = "contradiction-on-insert";

    @Inject
    ContradictionOnInsertService contradictionOnInsertService;

    @Inject
    ProcessEnablementService enablementService;

    @Inject
    CognitionConfig cognition;

    @Inject
    TokenTracker tokenTracker;

    private String detectorPrompt;

    @PostConstruct
    void init() {
        this.detectorPrompt = loadPrompt("prompts/contradiction-detector-batch-system.md");
    }

    private String loadPrompt(String promptPath) {
        try (var stream = getClass().getClassLoader().getResourceAsStream(promptPath)) {
            if (stream == null) {
                LOG.warnf("Prompt resource not found on classpath: %s", promptPath);
                return "Prompt resource not found: " + promptPath;
            }
            return new String(stream.readAllBytes());
        } catch (Exception e) {
            LOG.errorf(e, "Failed to load prompt from classpath resource: %s", promptPath);
            return "Error loading prompt: " + promptPath;
        }
    }

    // -------------------------------------------------------------------------
    // CognitiveProcess identity
    // -------------------------------------------------------------------------

    @Override
    public String id() {
        return PROCESS_ID;
    }

    @Override
    public String displayName() {
        return "Contradiction Detection (On-Insert)";
    }

    @Override
    public String description() {
        return "Real-time contradiction detection triggered automatically when memories are written. "
                + "Checks new memories against semantically similar existing memories and resolves "
                + "conflicts immediately.";
    }

    // -------------------------------------------------------------------------
    // Capability flags
    // -------------------------------------------------------------------------

    @Override
    public boolean supportsStart() {
        return false;  // Event-driven, triggered by memory writes
    }

    @Override
    public boolean supportsEnable() {
        return true;
    }

    @Override
    public boolean supportsDisable() {
        return true;
    }

    @Override
    public boolean defaultEnabled() {
        return true;  // Enabled by default (preserves current behavior)
    }

    @Override
    public ManagedProcessState state() {
        return enablementService.isEnabled(PROCESS_ID)
                ? ManagedProcessState.ENABLED
                : ManagedProcessState.DISABLED;
    }

    @Override
    public void enable() {
        enablementService.enable(PROCESS_ID);
    }

    @Override
    public void disable() {
        enablementService.disable(PROCESS_ID);
    }

    // -------------------------------------------------------------------------
    // Inspect
    // -------------------------------------------------------------------------

    @Override
    public ManagedProcessInspection inspect() {
        Map<String, Object> details = new LinkedHashMap<>();

        // Operational mode
        details.put("mode", "event_driven");
        details.put("triggerPoint", "memory_write");

        // Configuration
        details.put("neighboursChecked", cognition.contradiction().neighbours());

        // Operational metrics
        details.put("checksPerformed", contradictionOnInsertService.getChecksPerformed());
        details.put("contradictionsFound", contradictionOnInsertService.getContradictionsFound());
        details.put("contradictionsResolved", contradictionOnInsertService.getContradictionsResolved());
        details.put("errors", contradictionOnInsertService.getErrors());

        // LLM call statistics
        Map<String, Object> llmStats = new LinkedHashMap<>();
        llmStats.put("batchCallsSucceeded", contradictionOnInsertService.getBatchCallsSucceeded());
        llmStats.put("batchCallsFailed", contradictionOnInsertService.getBatchCallsFailed());
        llmStats.put("fallbackCallsUsed", contradictionOnInsertService.getFallbackCallsUsed());
        details.put("llmCallStatistics", llmStats);

        // Resource types (shared with ContradictionResolutionProcess)
        ResourceRequirements requirements = getResourceRequirements();
        if (requirements != null) {
            Map<String, Map<String, Object>> resourceTypes = new LinkedHashMap<>();
            requirements.getAllResources().forEach((name, resourceConfig) -> {
                Map<String, Object> resourceInfo = new LinkedHashMap<>();
                resourceInfo.put("type", resourceConfig.getType().name());
                resourceInfo.put("prompt", detectorPrompt);
                resourceInfo.put("tokenUsage", tokenTracker.snapshot(id(), name));
                resourceTypes.put(name, resourceInfo);
            });
            details.put("resourceTypes", resourceTypes);
        }
        details.put("tokenUsage", tokenTracker.processSnapshot(id()));

        return new ManagedProcessInspection(id(), displayName(), description(), state(), details);
    }

    // -------------------------------------------------------------------------
    // Resource requirements
    // -------------------------------------------------------------------------

    @Override
    public ResourceRequirements getResourceRequirements() {
        // Share the same LLM configuration as ContradictionResolutionProcess
        CognitionConfig.Contradiction.LlmConfig llmCfg = cognition.contradiction().llm();
        return ResourceRequirements.builder()
                .llm("detector", new DefaultLlmResourceConfiguration(
                        llmCfg.provider(),
                        llmCfg.model(),
                        llmCfg.temperature(),
                        llmCfg.maxTokens(),
                        llmCfg.timeout(),
                        Optional.empty(),
                        Map.of()))
                .build();
    }
}
