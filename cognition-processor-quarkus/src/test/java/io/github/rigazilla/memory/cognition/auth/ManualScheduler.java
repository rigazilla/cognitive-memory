package io.github.rigazilla.memory.cognition.auth;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/** A {@link Scheduler} that records tasks and runs them only when the test says so. */
final class ManualScheduler implements Scheduler {

    record Scheduled(Runnable task, Duration delay) {
    }

    final List<Scheduled> tasks = new ArrayList<>();
    boolean closed;

    @Override
    public synchronized void schedule(Runnable task, Duration delay) {
        tasks.add(new Scheduled(task, delay));
    }

    /** Runs and removes the task at {@code index}. */
    void run(int index) {
        Scheduled scheduled;
        synchronized (this) {
            scheduled = tasks.remove(index);
        }
        scheduled.task().run();
    }

    /** Runs and removes the most recently scheduled task. */
    void runLast() {
        run(size() - 1);
    }

    synchronized int size() {
        return tasks.size();
    }

    synchronized Scheduled last() {
        return tasks.get(tasks.size() - 1);
    }

    @Override
    public synchronized void close() {
        closed = true;
    }
}
