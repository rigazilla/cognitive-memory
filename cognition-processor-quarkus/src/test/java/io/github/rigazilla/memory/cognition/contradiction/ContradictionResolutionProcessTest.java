package io.github.rigazilla.memory.cognition.contradiction;

import io.github.rigazilla.memory.cognition.enablement.ProcessEnablementService;
import io.github.rigazilla.memory.cognition.process.ManagedProcessState;
import io.quarkus.arc.ClientProxy;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ContradictionResolutionProcess} enablement wiring.
 *
 * <p>Uses {@code @QuarkusTest} so the real CDI container wires config and the
 * {@link ProcessEnablementService}. Infrastructure beans that open gRPC connections
 * are replaced with {@code @InjectMock}.
 */
@QuarkusTest
class ContradictionResolutionProcessTest {

    @Inject
    ContradictionResolutionProcess process;

    @Inject
    ProcessEnablementService enablementService;

    @InjectMock
    ContradictionResolutionService resolutionService;

    @InjectMock
    ContradictionOnInsertService contradictionOnInsertService;

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
    void supportsStartIsTrue() {
        assertTrue(process.supportsStart());
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
    void defaultEnabledIsTrue() {
        assertTrue(process.defaultEnabled());
    }

    // -------------------------------------------------------------------------
    // state() tracks the enablement service
    // -------------------------------------------------------------------------

    @Test
    void stateIsEnabledByDefault() {
        // defaultEnabled() = true → service resolves to ENABLED on first access
        assertEquals(ManagedProcessState.ENABLED, process.state());
    }

    @Test
    void stateIsDisabledAfterDisable() {
        process.disable();
        assertEquals(ManagedProcessState.DISABLED, process.state());
    }

    @Test
    void stateIsEnabledAfterReEnable() {
        process.disable();
        process.enable();
        assertEquals(ManagedProcessState.ENABLED, process.state());
    }

    // -------------------------------------------------------------------------
    // enable() / disable() delegate to the service
    // -------------------------------------------------------------------------

    @Test
    void disableClearsServiceFlag() {
        process.disable();
        assertFalse(enablementService.isEnabled(ContradictionResolutionProcess.PROCESS_ID));
    }

    @Test
    void enableSetsServiceFlag() {
        process.disable();
        process.enable();
        assertTrue(enablementService.isEnabled(ContradictionResolutionProcess.PROCESS_ID));
    }

    // -------------------------------------------------------------------------
    // inspect() state field reflects toggle
    // -------------------------------------------------------------------------

    @Test
    void inspectStateMatchesProcessState() {
        assertEquals(ManagedProcessState.ENABLED, process.inspect().state());

        process.disable();
        assertEquals(ManagedProcessState.DISABLED, process.inspect().state());

        process.enable();
        assertEquals(ManagedProcessState.ENABLED, process.inspect().state());
    }
}
