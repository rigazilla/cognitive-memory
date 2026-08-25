package io.github.rigazilla.memory.cognition.event;

import io.github.rigazilla.memory.cognition.config.SalienceScorerConfig;

import java.util.List;
import java.util.Optional;

/**
 * Minimal in-process stub of {@link SalienceScorerConfig} for unit tests.
 * All defaults match the production {@code @WithDefault} values so tests that
 * do not override any field get identical behaviour to a default deployment.
 * Individual tests mutate fields as needed before calling {@code scorer.init()}.
 *
 * <p><strong>Keep in sync:</strong> the default term lists in {@link PatternStub} are
 * hand-copied from {@link SalienceScorerConfig.Pattern}'s {@code @WithDefault} strings.
 * If a default changes in the interface, update the corresponding field here too.
 * Tests that must track {@code @WithDefault} values automatically should use
 * {@link io.smallrye.config.SmallRyeConfigBuilder} instead (see
 * {@link SalienceScorerConfigBindingTest}).
 */
class SalienceScorerConfigStub implements SalienceScorerConfig {

    boolean enabled       = true;
    double  threshold     = 0.3;
    int     minLength     = 10;
    boolean metricsEnabled = true;
    PatternStub  pattern  = new PatternStub();
    KeywordsStub keywords = new KeywordsStub();

    @Override public boolean  enabled()        { return enabled; }
    @Override public double   threshold()      { return threshold; }
    @Override public int      minLength()      { return minLength; }
    @Override public boolean  metricsEnabled() { return metricsEnabled; }
    @Override public Pattern  pattern()        { return pattern; }
    @Override public Keywords keywords()       { return keywords; }

    static class PatternStub implements SalienceScorerConfig.Pattern {
        boolean greetingEnabled       = true;
        boolean acknowledgmentEnabled = true;
        boolean farewellEnabled       = true;
        boolean fillerEnabled         = true;
        boolean thanksEnabled         = true;
        List<String> greetings       = List.of("hi","hello","hey","greetings","good morning","good afternoon","good evening");
        List<String> acknowledgments  = List.of("ok","okay","sure","got it","understood","sounds good","makes sense","alright");
        List<String> farewells        = List.of("bye","goodbye","see you","talk later","take care","have a good day");
        List<String> fillers          = List.of("um","uh","hmm","well","so","like");
        List<String> thanks           = List.of("thanks","thank you","thx","ty");

        @Override public boolean      greetingEnabled()       { return greetingEnabled; }
        @Override public List<String> greetings()             { return greetings; }
        @Override public boolean      acknowledgmentEnabled() { return acknowledgmentEnabled; }
        @Override public List<String> acknowledgments()       { return acknowledgments; }
        @Override public boolean      farewellEnabled()       { return farewellEnabled; }
        @Override public List<String> farewells()             { return farewells; }
        @Override public boolean      fillerEnabled()         { return fillerEnabled; }
        @Override public List<String> fillers()               { return fillers; }
        @Override public boolean      thanksEnabled()         { return thanksEnabled; }
        @Override public List<String> thanks()                { return thanks; }
    }

    static class KeywordsStub implements SalienceScorerConfig.Keywords {
        Optional<List<String>> list = Optional.empty();
        Optional<String>       file = Optional.empty();

        @Override public Optional<List<String>> list() { return list; }
        @Override public Optional<String>       file() { return file; }
    }
}
