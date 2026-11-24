package io.github.rigazilla.memory.cognition.auth;

import java.time.Duration;

/** Runs delayed tasks for {@link OidcTokenProvider}; replaceable by a manual one in tests. */
interface Scheduler extends AutoCloseable {

    /** Runs {@code task} once after {@code delay} (immediately if zero or negative). */
    void schedule(Runnable task, Duration delay);

    /** Stops the scheduler; pending tasks are dropped and a running one is interrupted. */
    @Override
    void close();
}
