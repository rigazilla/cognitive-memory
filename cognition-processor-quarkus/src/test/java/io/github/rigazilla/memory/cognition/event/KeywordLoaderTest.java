package io.github.rigazilla.memory.cognition.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

class KeywordLoaderTest {

    private SalienceScorerConfigStub config;
    private KeywordLoader loader;

    @BeforeEach
    void setUp() {
        config = new SalienceScorerConfigStub();
        loader = new KeywordLoader(config);
    }

    @Nested
    class Load {

        @Test
        void bundledDefaultFile_loadsAndParsesCorrectly() {
            List<String> keywords = loader.load();
            assertThat(keywords).isNotEmpty();
            assertThat(keywords).contains("error", "bug", "deploy", "test", "code",
                    "rollback", "prod",
                    "prefer", "like", "want", "need",
                    "decided", "chose", "selected",
                    "steps", "process", "workflow");
        }

        @Test
        void externalKeywordsFile_usedInsteadOfBundled(@TempDir Path tempDir) throws IOException {
            Path kwFile = tempDir.resolve("custom-keywords.txt");
            Files.writeString(kwFile, "# custom\ncrash\noutage\n\n");
            config.keywords.file = Optional.of(kwFile.toString());

            List<String> keywords = loader.load();
            assertThat(keywords).containsExactlyInAnyOrder("crash", "outage");
            assertThat(keywords).doesNotContain("error", "bug");
        }

        @Test
        void missingExternalKeywordsFile_warnsAndFallsBackToBundled(@TempDir Path tempDir) {
            Path missing = tempDir.resolve("does-not-exist.txt");
            config.keywords.file = Optional.of(missing.toString());

            Logger julLogger = Logger.getLogger(KeywordLoader.class.getName());
            WarnCapture warnCapture = new WarnCapture();
            julLogger.addHandler(warnCapture);
            julLogger.setLevel(Level.ALL);
            try {
                List<String> keywords = loader.load();
                assertThat(keywords).isNotEmpty();
                assertThat(keywords).contains("error", "bug");
                assertThat(warnCapture.sawWarn).isTrue();
            } finally {
                julLogger.removeHandler(warnCapture);
            }
        }

        @Test
        void inlineKeywordsList_overridesBundled() {
            config.keywords.list = Optional.of(List.of("crash", "outage", "incident"));

            List<String> keywords = loader.load();
            assertThat(keywords).containsExactlyInAnyOrder("crash", "outage", "incident");
            assertThat(keywords).doesNotContain("error", "bug");
        }

        @Test
        void inlineKeywordsList_overridesExternalFile(@TempDir Path tempDir) throws IOException {
            Path kwFile = tempDir.resolve("file-keywords.txt");
            Files.writeString(kwFile, "filekeyword\n");
            config.keywords.file = Optional.of(kwFile.toString());
            config.keywords.list = Optional.of(List.of("inlinekeyword"));

            List<String> keywords = loader.load();
            assertThat(keywords).containsExactly("inlinekeyword");
        }

        @Test
        void emptyExternalFile_warnsAndFallsBackToBundled(@TempDir Path tempDir) throws IOException {
            Path kwFile = tempDir.resolve("empty.txt");
            Files.writeString(kwFile, "");
            config.keywords.file = Optional.of(kwFile.toString());

            Logger julLogger = Logger.getLogger(KeywordLoader.class.getName());
            WarnCapture warnCapture = new WarnCapture();
            julLogger.addHandler(warnCapture);
            julLogger.setLevel(Level.ALL);
            try {
                List<String> keywords = loader.load();
                assertThat(keywords).contains("error", "bug", "deploy");
                assertThat(warnCapture.sawWarn).isTrue();
            } finally {
                julLogger.removeHandler(warnCapture);
            }
        }

        @Test
        void commentsOnlyExternalFile_warnsAndFallsBackToBundled(@TempDir Path tempDir) throws IOException {
            Path kwFile = tempDir.resolve("comments-only.txt");
            Files.writeString(kwFile, "# this is a comment\n\n# another comment\n   \n");
            config.keywords.file = Optional.of(kwFile.toString());

            Logger julLogger = Logger.getLogger(KeywordLoader.class.getName());
            WarnCapture warnCapture = new WarnCapture();
            julLogger.addHandler(warnCapture);
            julLogger.setLevel(Level.ALL);
            try {
                List<String> keywords = loader.load();
                assertThat(keywords).contains("error", "bug", "deploy");
                assertThat(warnCapture.sawWarn).isTrue();
            } finally {
                julLogger.removeHandler(warnCapture);
            }
        }

        // Note: the absent-bundled-resource fail-fast contract (loadBundled throws
        // IllegalStateException when the classpath resource is missing) is covered at
        // the integration level — a broken build causes @PostConstruct to throw and
        // Quarkus refuses to start. It is not directly testable here since loadBundled
        // is private and the bundled resource is always present in the test classpath.

        @Test
        void emptyInlineList_warnsAndFallsBackToBundled() {
            config.keywords.list = Optional.of(List.of());

            Logger julLogger = Logger.getLogger(KeywordLoader.class.getName());
            WarnCapture warnCapture = new WarnCapture();
            julLogger.addHandler(warnCapture);
            julLogger.setLevel(Level.ALL);
            try {
                List<String> keywords = loader.load();
                assertThat(keywords).contains("error", "bug", "deploy");
                assertThat(warnCapture.sawWarn).isTrue();
            } finally {
                julLogger.removeHandler(warnCapture);
            }
        }
    }

    @Nested
    class ParseKeywordLines {

        @Test
        void skipsCommentsAndBlanks() {
            List<String> result = KeywordLoader.parseKeywordLines(
                    List.of("# comment", "", "  Error  ", "BUG", "  "));
            assertThat(result).containsExactly("error", "bug");
        }

        @Test
        void lowercases() {
            List<String> result = KeywordLoader.parseKeywordLines(List.of("DEPLOY", "Test"));
            assertThat(result).containsExactly("deploy", "test");
        }

        @Test
        void deduplicates() {
            List<String> result = KeywordLoader.parseKeywordLines(
                    List.of("error", "Error", "ERROR"));
            assertThat(result).hasSize(1).containsExactly("error");
        }
    }

    // -------------------------------------------------------------------------
    // Helper: capture JUL WARN log records
    // -------------------------------------------------------------------------

    private static class WarnCapture extends Handler {
        boolean sawWarn = false;

        @Override
        public void publish(LogRecord record) {
            if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
                sawWarn = true;
            }
        }

        @Override public void flush() {}
        @Override public void close() {}
    }
}
