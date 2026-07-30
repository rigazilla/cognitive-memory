# 022 — Static Analysis Checks (Issue #21)

## Summary

Added Checkstyle and SpotBugs static analysis to the Maven build and GitHub Actions CI/CD
pipeline. Both tools run in warn-only mode, reporting violations without blocking the build.

## What Was Added

### Maven Plugins (`pom.xml`)

| Plugin | Version | Phase | Mode |
|---|---|---|---|
| `maven-checkstyle-plugin` | 3.6.0 | `verify` | warn-only |
| `spotbugs-maven-plugin` | 4.10.3.0 | `verify` | warn-only |

Run locally with:
```bash
./mvnw verify -DskipTests
```

### Checkstyle Configuration (`checkstyle.xml`)

Based on Google Java Style Guide. Key rules enforced:
- No tab characters (spaces only)
- Line length ≤ 100 characters
- No star imports, no unused imports
- Google naming conventions (types, methods, variables, constants)
- Consistent whitespace and brace style
- Modifier ordering

Relaxed rules (not enforced):
- Javadoc on methods, types, and variables

Note: 136 lines in the existing codebase exceed the 100-char limit. These are surfaced as
warnings but do not block the build in the current warn-only configuration.

### GitHub Actions (`.github/workflows/quarkus-build-and-test.yml`)

Two new steps added, scoped to `ubuntu-latest` only (analysis output is OS-independent):

1. **Run static analysis** — `./mvnw verify -DskipTests`
2. **Upload static analysis reports** — uploads `checkstyle-result.xml` and `spotbugsXml.xml`
   as a downloadable workflow artifact named `static-analysis-reports`

Both `ci.yml` (PR) and `cd.yml` (push to main) delegate to this reusable workflow, so
static analysis runs on every PR and every push to main with no further changes needed.

## How to Tighten Enforcement Later

When ready to block merges on violations:

1. **Checkstyle**: Set `<failsOnError>true</failsOnError>` in `pom.xml`. Fix or suppress
   existing violations first (see the artifact report for a full list).

2. **SpotBugs**: Set `<failOnError>true</failOnError>` in `pom.xml`. Review the
   `spotbugsXml.xml` artifact and add a `spotbugs-exclude.xml` filter for any
   false positives before enabling hard failures.

3. **Line length**: The 136 existing violations can be addressed incrementally. Once cleaned
   up, change `<failsOnError>` to `true` for Checkstyle.
