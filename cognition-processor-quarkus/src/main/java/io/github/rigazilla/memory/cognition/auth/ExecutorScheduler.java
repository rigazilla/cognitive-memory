package io.github.rigazilla.memory.cognition.auth;

import java.time.Duration;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** {@link Scheduler} backed by two daemon threads (one renewal at a time, plus wait time-outs). */
final class ExecutorScheduler implements Scheduler {

    private final ScheduledThreadPoolExecutor executor;

    ExecutorScheduler(String threadName) {
        executor = new ScheduledThreadPoolExecutor(2, runnable -> {
            Thread thread = new Thread(runnable, threadName);
            thread.setDaemon(true);
            return thread;
        });
        executor.setRemoveOnCancelPolicy(true);
    }

    @Override
    public void schedule(Runnable task, Duration delay) {
        try {
            executor.schedule(task, Math.max(0, delay.toMillis()), TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            // closed: the provider is shutting down, nothing left to schedule
        }
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }
}
