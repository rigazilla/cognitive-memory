package io.github.rigazilla.memory.cognition.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

import java.time.Duration;

/**
 * Typed configuration for the cognition processor.
 * <p>
 * Replaces scattered {@code @ConfigProperty(name = "cognition.*")} fields with a
 * single injectable interface.  Prefer injecting {@code CognitionConfig} over
 * individual {@code @ConfigProperty} fields in all new code.
 *
 * <p>Example usage:
 * <pre>{@code
 * @Inject
 * CognitionConfig cognition;
 *
 * String runtimeId = cognition.runtime().id();
 * Duration debounce = cognition.scheduler().debounceDelay();
 * }</pre>
 *
 * <p>Corresponding {@code application.properties} key prefixes:
 * <ul>
 *   <li>{@code cognition.runtime.*}</li>
 *   <li>{@code cognition.worker.*}</li>
 *   <li>{@code cognition.checkpoint.*}</li>
 *   <li>{@code cognition.scheduler.*}</li>
 *   <li>{@code cognition.llm.*}</li>
 *   <li>{@code cognition.secrets.*}</li>
 * </ul>
 */
@ConfigMapping(prefix = "cognition")
public interface CognitionConfig {

    /** Runtime identity settings. */
    Runtime runtime();

    /** Worker identity settings. */
    Worker worker();

    /** Checkpoint settings. */
    Checkpoint checkpoint();

    /** Debounce scheduler settings. */
    Scheduler scheduler();

    /** LLM call settings. */
    Llm llm();

    /** Secrets / credential resolution settings. */
    Secrets secrets();

    /** Runtime identity sub-group. */
    interface Runtime {
        /** Stable identifier for this cognition runtime implementation. */
        @WithDefault("cognition-processor-v1")
        String id();

        /** Semantic version of the runtime implementation. */
        @WithDefault("1.0.0-SNAPSHOT")
        String version();
    }

    /** Worker identity sub-group. */
    interface Worker {
        /** Worker identifier used to scope checkpoints. */
        @WithDefault("cognition_processor")
        String id();
    }

    /** Checkpoint settings sub-group. */
    interface Checkpoint {
        /**
         * When {@code true} the checkpoint is reset to {@code "start"} on application
         * startup, causing all events to be replayed from the beginning.
         */
        @WithName("reset-on-startup")
        @WithDefault("false")
        boolean resetOnStartup();
    }

    /** Debounce scheduler settings sub-group. */
    interface Scheduler {
        /**
         * Quiet-period after the last event before a dirty window is promoted to a job.
         * ISO-8601 duration format, e.g. {@code PT1M}.
         */
        @WithName("debounce-delay")
        @WithDefault("PT1M")
        Duration debounceDelay();

        /**
         * Maximum age of a dirty window before it is promoted regardless of quiet-period.
         * ISO-8601 duration format, e.g. {@code PT5M}.
         */
        @WithName("max-batch-age")
        @WithDefault("PT5M")
        Duration maxBatchAge();

        /**
         * Maximum number of conversation entries in a single processing batch.
         * When this limit is reached the window is promoted immediately.
         */
        @WithName("max-batch-entries")
        @WithDefault("24")
        int maxBatchEntries();

        /**
         * Maximum number of open dirty windows kept in memory between checkpoints.
         * When the limit is reached, the oldest due window is promoted to make room.
         */
        @WithName("max-checkpoint-windows")
        @WithDefault("1000")
        int maxCheckpointWindows();

        /**
         * Maximum number of conversations processed concurrently (Phase 1: reserved, not yet enforced).
         */
        @WithName("max-concurrent-jobs")
        @WithDefault("8")
        int maxConcurrentJobs();
    }

    /** LLM call settings sub-group. */
    interface Llm {
        /** LLM retry settings. */
        Retry retry();

        /** LLM backfill settings. */
        Backfill backfill();

        /** Retry / back-off settings for transient LLM failures. */
        interface Retry {
            /**
             * Maximum number of attempts for a single LLM call.
             * {@code 1} disables retry; {@code 3} means two retries after the first failure.
             */
            @WithName("max-attempts")
            @WithDefault("3")
            int maxAttempts();

            /**
             * Initial back-off delay in milliseconds before the first retry.
             * Doubles on each subsequent attempt.
             */
            @WithName("initial-delay-ms")
            @WithDefault("1000")
            long initialDelayMs();

            /**
             * Upper bound for exponential back-off delay in milliseconds.
             * Prevents the delay from growing without bound.
             */
            @WithName("max-delay-ms")
            @WithDefault("30000")
            long maxDelayMs();
        }

        /** Backfill pass settings. */
        interface Backfill {
            /**
             * Throttle delay between successive LLM calls in backfill loops, in milliseconds.
             * Use {@code 0} for local Ollama; use {@code 200–500} for rate-limited endpoints.
             */
            @WithName("inter-call-delay-ms")
            @WithDefault("0")
            long interCallDelayMs();
        }
    }

    /** Credential resolution settings sub-group. */
    interface Secrets {
        /**
         * Credential provider to use when resolving secret references.
         * Supported values: {@code env}, {@code vault}, {@code aws-secrets-manager}.
         */
        @WithDefault("env")
        String provider();
    }
}
