# Plan: Static Analysis PR Checks (Issue #21)

## Overview

Add Checkstyle and SpotBugs static analysis to the Maven build and GitHub Actions CI/CD pipeline.
Both tools will run in **warn-only mode** — violations are reported but do not block the build,
allowing the existing codebase to be baselined before enforcement is tightened later.

The static analysis step will be triggered on:
- Every **pull request** to `main` (via `ci.yml`)
- Every **push** to `main` (via `cd.yml`)

Both pipelines already delegate to the reusable `quarkus-build-and-test.yml` workflow,
so all changes to analysis execution live in that one workflow file and `pom.xml`.

---

## Sub-Tasks

### Sub-Task 1 — Add Checkstyle to pom.xml

**Intent:**
Configure `maven-checkstyle-plugin` in `pom.xml` to run Google Java Style checks during the
Maven `verify` phase. Set `failsOnError=false` and `violationSeverity=warning` so violations
are reported but do not break the build.

**Expected Outcomes:**
- `./mvnw verify` runs Checkstyle and prints any style violations as warnings
- Checkstyle report is generated under `target/checkstyle-result.xml`
- Build does not fail due to Checkstyle violations

**Todo List:**
1. Add `maven-checkstyle-plugin` (version 3.x) to `<build><plugins>` in `pom.xml`
2. Bind it to the `verify` phase with goal `check`
3. Point `configLocation` to `checkstyle.xml` (project-local file)
4. Set `failsOnError=false`, `violationSeverity=warning`, `logViolationsToConsole=true`

**Relevant Context:**
- File: `cognition-processor-quarkus/pom.xml`
- No existing Checkstyle config found in the repo

**Status:** `[ ] pending`

---

### Sub-Task 2 — Create Checkstyle configuration file

**Intent:**
Provide the Checkstyle ruleset file based on Google Java Style. Place it alongside `pom.xml`
so it is easy to find and modify. Start with the standard Google checks but relax rules that
commonly produce noisy warnings on existing code (e.g. Javadoc on every method).

**Expected Outcomes:**
- `cognition-processor-quarkus/checkstyle.xml` exists and is valid XML
- The file is referenced correctly from the plugin `configLocation`
- `./mvnw verify` resolves and applies the ruleset without errors

**Todo List:**
1. Create `cognition-processor-quarkus/checkstyle.xml` using Google Java Style as base
   (reference: `com/puppycrawl/tools/checkstyle/checks/coding/` module set)
2. Suppress or relax: `MissingJavadocMethod`, `MissingJavadocType`, `JavadocVariable`
3. Keep: naming conventions, import ordering, whitespace, line length (≤ 100 chars, Google default)
   Note: 136 lines in the existing codebase already exceed 100 chars — acceptable in warn-only mode

**Relevant Context:**
- File to create: `cognition-processor-quarkus/checkstyle.xml`
- Plugin `configLocation` should use a relative path from the module root

**Status:** `[ ] pending`

---

### Sub-Task 3 — Add SpotBugs to pom.xml

**Intent:**
Configure `spotbugs-maven-plugin` in `pom.xml` to run bug-pattern analysis during the
Maven `verify` phase. Set `failOnError=false` so violations are reported but do not break
the build.

**Expected Outcomes:**
- `./mvnw verify` runs SpotBugs on compiled classes and prints any detected bug patterns
- SpotBugs XML report is generated under `target/spotbugsXml.xml`
- Build does not fail due to SpotBugs findings

**Todo List:**
1. Add `spotbugs-maven-plugin` (version 4.x) to `<build><plugins>` in `pom.xml`
2. Bind it to the `verify` phase with goal `check`
3. Set `<effort>Max</effort>`, `<threshold>Medium</threshold>`
4. Set `<failOnError>false</failOnError>`

**Relevant Context:**
- File: `cognition-processor-quarkus/pom.xml`
- SpotBugs analyzes compiled `.class` files; the build must run `compile` before `check`
  (the `verify` lifecycle ordering guarantees this)

**Status:** `[ ] pending`

---

### Sub-Task 4 — Update CI workflow to run static analysis

**Intent:**
Update `quarkus-build-and-test.yml` to replace the current `./mvnw -B test` step with
`./mvnw -B verify` so that static analysis runs as part of the workflow. Since both
`ci.yml` (PR) and `cd.yml` (main push) delegate to this reusable workflow, the change
automatically applies to both triggers.

**Expected Outcomes:**
- PRs to `main` run Checkstyle + SpotBugs and surface any violations in the job logs
- Pushes to `main` run the same checks
- The workflow does not fail solely due to static analysis violations (warn-only)

**Todo List:**
1. In `.github/workflows/quarkus-build-and-test.yml`, change the "Run tests" step from
   `./mvnw -B test` to `./mvnw -B verify`, but **only for `ubuntu-latest`** (static analysis
   is OS-independent; no value in running it twice)
2. Add an `upload-artifact` step (after `verify`) to upload `target/checkstyle-result.xml`
   and `target/spotbugsXml.xml` as workflow artifacts, scoped to the ubuntu-latest matrix leg only

**Relevant Context:**
- File: `.github/workflows/quarkus-build-and-test.yml`
- `ci.yml` and `cd.yml` both call this reusable workflow — no changes needed to those files
- The matrix currently runs on `[ubuntu-latest, macos-latest]`; the `verify` step and artifact
  upload should be conditioned on `matrix.os == 'ubuntu-latest'`

**Status:** `[ ] pending`

---

### Sub-Task 5 — Create DONE document

**Intent:**
Record the completed feature following project convention (AGENTS.md requires a design
description in the `DONE/` folder for every completed feature).

**Expected Outcomes:**
- `cognition-processor-quarkus/DONE/022-static-analysis-checks.md` exists
- Document summarises what was added, the tools and versions, and how to tighten enforcement later

**Todo List:**
1. Create `cognition-processor-quarkus/DONE/022-static-analysis-checks.md`
2. Include: tools added, plugin versions, warn-only rationale, path to tighten later

**Relevant Context:**
- Convention: prefix with `NNN-`, increment from highest existing (`021-github-actions-ci-cd.md`)
- Reference: `cognition-processor-quarkus/AGENTS.md` (Progress tracking section)

**Status:** `[ ] pending`
