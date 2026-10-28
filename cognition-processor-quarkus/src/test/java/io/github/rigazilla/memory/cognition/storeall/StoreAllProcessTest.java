package io.github.rigazilla.memory.cognition.storeall;

import io.github.rigazilla.memory.cognition.enablement.ProcessEnablementService;
import io.github.rigazilla.memory.cognition.event.GrpcAdminEventClient;
import io.github.rigazilla.memory.cognition.process.ManagedProcessState;
import io.quarkus.arc.ClientProxy;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link StoreAllProcess} enablement wiring.
 *
 * <p>Uses {@code @QuarkusTest} so the real CDI container wires config and the
 * {@link ProcessEnablementService}. The {@link StoreAllService} (which opens gRPC
 * connections) is replaced with an {@code @InjectMock}.
 */
@QuarkusTest
class StoreAllProcessTest {

    @Inject
    StoreAllProcess process;

    @Inject
    ProcessEnablementService enablementService;

    @InjectMock
    StoreAllService storeAllService;

    @InjectMock
    GrpcAdminEventClient eventClient;

    @BeforeEach
    void setUp() {
        ProcessEnablementService real = (ProcessEnablementService)
                ((ClientProxy) enablementService).arc_contextualInstance();
        real.resetOverrides();
    }

    // -------------------------------------------------------------------------
    // Capability flags
    // -------------------------------------------------------------------------

    @Test
    void supportsStartIsFalse() {
        assertFalse(process.supportsStart());
    }

    @Test
    void supportsEnableIsTrue() {
        assertTrue(process.supportsEnable());
    }

    @Test
    void supportsDisableIsTrue() {
        assertTrue(process.supportsDisable());
    }

    @Test
    void defaultEnabledIsFalse() {
        assertFalse(process.defaultEnabled());
    }

    // -------------------------------------------------------------------------
    // state() tracks the enablement service
    // -------------------------------------------------------------------------

    @Test
    void stateIsDisabledByDefault() {
        // defaultEnabled() = false → service resolves to DISABLED on first access
        assertEquals(ManagedProcessState.DISABLED, process.state());
    }

    @Test
    void stateIsEnabledAfterEnable() {
        process.enable();
        assertEquals(ManagedProcessState.ENABLED, process.state());
    }

    @Test
    void stateIsDisabledAfterDisable() {
        process.enable();
        process.disable();
        assertEquals(ManagedProcessState.DISABLED, process.state());
    }

    // -------------------------------------------------------------------------
    // enable() / disable() delegate to the service
    // -------------------------------------------------------------------------

    @Test
    void enableSetsServiceFlag() {
        process.enable();
        assertTrue(enablementService.isEnabled(StoreAllProcess.PROCESS_ID));
    }

    @Test
    void disableClearsServiceFlag() {
        process.enable();
        process.disable();
        assertFalse(enablementService.isEnabled(StoreAllProcess.PROCESS_ID));
    }

    // -------------------------------------------------------------------------
    // inspect() state field reflects toggle
    // -------------------------------------------------------------------------

    @Test
    void inspectStateMatchesProcessState() {
        assertEquals(process.state(), process.inspect().state());

        process.enable();
        assertEquals(ManagedProcessState.ENABLED, process.inspect().state());

        process.disable();
        assertEquals(ManagedProcessState.DISABLED, process.inspect().state());
    }

    @Test
    void inspectDetailsContainsAllExpectedFields() {
        var details = process.inspect().details();
        assertTrue(details.containsKey("eventStreamConnected"), "inspect must expose eventStreamConnected");
        assertTrue(details.containsKey("eventsAccepted"), "inspect must expose eventsAccepted");
        assertTrue(details.containsKey("eventsProcessed"), "inspect must expose eventsProcessed");
        assertTrue(details.containsKey("memoriesWritten"), "inspect must expose memoriesWritten");
        assertTrue(details.containsKey("memoriesSkipped"), "inspect must expose memoriesSkipped");
        assertTrue(details.containsKey("llmCalls"), "inspect must expose llmCalls");
        assertTrue(details.containsKey("memoryType"), "inspect must expose memoryType");
    }

    @Test
    void inspectLlmCallsIsAlwaysZero() {
        assertEquals(0, process.inspect().details().get("llmCalls"));
    }

    @Test
    void inspectMemoryTypeIsVerbatim() {
        assertEquals("verbatim", process.inspect().details().get("memoryType"));
    }
}
