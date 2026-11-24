package io.github.rigazilla.memory.cognition.auth;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/** A scripted {@link TokenSource}: each fetch consumes the next scripted outcome. */
final class FakeTokenSource implements TokenSource {

    private final Deque<Supplier<Token>> script = new ArrayDeque<>();
    private final AtomicInteger fetches = new AtomicInteger();
    private final Clock clock;
    private int counter;

    FakeTokenSource(Clock clock) {
        this.clock = clock;
    }

    /** Next fetch returns "token-N" valid for {@code lifetime}. */
    FakeTokenSource thenToken(Duration lifetime) {
        synchronized (script) {
            script.add(() -> new Token("token-" + (++counter), clock.instant().plus(lifetime)));
        }
        return this;
    }

    /** Next fetch fails with a {@link TokenFetchException}. */
    FakeTokenSource thenFail() {
        synchronized (script) {
            script.add(() -> {
                throw new TokenFetchException("scripted failure");
            });
        }
        return this;
    }

    /** Next fetch runs {@code action} and returns its result. */
    FakeTokenSource then(Supplier<Token> action) {
        synchronized (script) {
            script.add(action);
        }
        return this;
    }

    int fetches() {
        return fetches.get();
    }

    @Override
    public Token fetch() {
        fetches.incrementAndGet();
        Supplier<Token> next;
        synchronized (script) {
            next = script.poll();
        }
        if (next == null) {
            throw new TokenFetchException("script exhausted");
        }
        return next.get();
    }
}
