# OIDC client-credentials authentication: design and test plan

Issue: https://github.com/rigazilla/cognitive-memory/issues/69
(design decisions: see the issue comment of 2026-11-23; updated after chirino's review of
2026-11-23)

Behavioural reference: memory-service `internal/cmd/process/runtime/auth.go` and `auth_test.go`
(chirino/memory-service #769/#770, merged). Property names are **not** copied from it.

This document supersedes `oauth2-client-credentials-implementation.md` and
`github-issue-oauth2-implementation.md` in this directory (older naming and approach).

## 1. Goal

Let the processor authenticate to memory-service gRPC with an OIDC client-credentials access
token (Keycloak), with background renewal, as an alternative to the fixed API key. API-key mode
keeps working unchanged.

## 2. Current state (relevant facts)

- `GrpcChannelFactory.create(host, port, apiKey[, clientId])` is static, plaintext, and adds
  `x-api-key` (+ `x-client-id`) through `AuthInterceptor`.
- 13 call sites build their own channel: 12 services (`JobProcessor`, `MemoryWriter`,
  `ContradictionOnInsertService`, `ContradictionResolutionService`, `ConversationOwnerResolver`,
  `TemporalMetadataEnrichmentService`, `ProfileContextService`, `MetadataEnrichmentService`,
  `MemoryJustifyService`, `TranscriptLoader`, `CheckpointService`, `ExactMatchDuplicateDetector`)
  plus `GrpcAdminEventClient`.
- 7 of them call the 3-argument overload (no `x-client-id`), 6 the 4-argument one.
- `GrpcAdminEventClient.subscribeToEvents` additionally builds its own `CallCredentials`
  (`x-api-key` + `x-client-id`), duplicating the channel interceptor.
- `GrpcAdminEventClient.onShutdown` disconnects, then saves a final checkpoint (needs auth).
- `MemoryJustifyResource` triggers memory-service calls from HTTP threads, so the token cache
  must be shared and thread-safe.
- `MemoryServiceConfig.apiKey()` is a required `String`; `application.properties` line 13 is
  `memory-service.api-key=${MEMORY_SERVICE_API_KEY}` (fails if the env var is unset).
- The module JaCoCo gate (`pom.xml`) is BUNDLE-wide: 15% line / 10% branch. Quarkus 3.37.1.
  `quarkus-oidc-client` is not a dependency yet.

## 3. Configuration

| Property | Env var | Notes |
|---|---|---|
| `memory-service.auth.method` | `MEMORY_SERVICE_AUTH_METHOD` | `auto` (default) \| `api-key` \| `oidc` |
| `memory-service.api-key` | `MEMORY_SERVICE_API_KEY` | now optional |
| `memory-service.client-id` | `MEMORY_SERVICE_CLIENT_ID` | unchanged, default `cognition_processor`; OIDC `client_id` in `oidc` mode |
| `memory-service.auth.oidc.client-secret` | `MEMORY_SERVICE_OIDC_CLIENT_SECRET` | |
| `memory-service.auth.oidc.discovery-url` | `MEMORY_SERVICE_OIDC_DISCOVERY_URL` | exclusive with token-url |
| `memory-service.auth.oidc.token-url` | `MEMORY_SERVICE_OIDC_TOKEN_URL` | exclusive with discovery-url |
| `memory-service.auth.oidc.audience` | `MEMORY_SERVICE_OIDC_AUDIENCE` | optional |
| `memory-service.auth.oidc.scope` | `MEMORY_SERVICE_OIDC_SCOPE` | optional, comma separated |
| `memory-service.auth.oidc.allow-insecure` | `MEMORY_SERVICE_OIDC_ALLOW_INSECURE` | dev opt-in for non-loopback `http://` |
| `memory-service.auth.oidc.tls-configuration-name` | `MEMORY_SERVICE_OIDC_TLS_CONFIGURATION_NAME` | optional, names a `quarkus.tls.<name>` entry |

CA, TLS skip-verify and secret-from-file have no dedicated properties: they use a named
`quarkus.tls.<name>` entry (referenced by `tls-configuration-name`) and a mounted config
directory (see "Quarkus OIDC client usage").
There is deliberately no `issuer` property: the discovered issuer is not validated.

`MemoryServiceConfig` gains `auth()` (`method()`, `oidc()` with all-`Optional` fields) and
`apiKey()` becomes `Optional<String>`. `application.properties` line 13 becomes
`memory-service.api-key=${MEMORY_SERVICE_API_KEY:}` (empty = unset).

### Mode resolution (`AuthModeResolver`, pure logic)

"OIDC property" = **any** `memory-service.auth.oidc.*` value (secret, discovery-url, token-url,
audience, scope, allow-insecure, tls-configuration-name). `memory-service.client-id` has a
default, so it is not a signal. Blank values count as unset; `allow-insecure` is an
`Optional<Boolean>` so that "set" can be detected.

| `method` | OIDC properties | API key | Result |
|---|---|---|---|
| `auto` | none | set | `api-key` |
| `auto` | none | unset | startup failure (neither configured) |
| `auto` | any | any | `oidc`, fully validated; partial config fails; API key ignored (warning if set) |
| `api-key` | any | set | `api-key`; OIDC settings ignored (log line) |
| `api-key` | any | unset | startup failure |
| `oidc` | none/partial | any | startup failure |
| `oidc` | complete | any | `oidc`; API key ignored |

Validation in `oidc` mode: secret required; exactly one of discovery-url / token-url;
URL guard (below).

Validation in **both** modes: `cognition.worker.id` must equal `memory-service.client-id`. The
server's checkpoint check (`grpcAllowCheckpointClient`) rejects any mismatch between the
checkpoint `client_id` and the authenticated client, API-key callers included.

Error messages name the offending property and the inferred mode, and never contain the secret.
The active mode is logged once at startup.

URL guard (`OidcUrlValidator`, mirrors `validateOIDCURL`): absolute http(s) URL, non-empty host,
no user info, no fragment; `https` always allowed; `http` only for `localhost` / loopback IPs
unless `allow-insecure=true`. Only the configured URLs can be checked (not the discovered
`token_endpoint`).

## 4. Components

New package `io.github.rigazilla.memory.cognition.auth` (own package so coverage can be gated
independently, see section 7).

| Class | Responsibility |
|---|---|
| `AuthMode` | enum `API_KEY`, `OIDC` |
| `AuthModeResolver` | table above; returns resolved mode + validated settings or throws |
| `OidcUrlValidator` | URL guard |
| `TokenSource` | interface: `Token fetch()` (`accessToken`, `expiresAt`); one fetch, no retry |
| `QuarkusOidcTokenSource` | wraps a programmatically created `OidcClient` (`OidcClients.newClient(...)`) |
| `OidcTokenProvider` | cache, single background renewal, back-off, invalidate, shutdown |
| `MemoryServiceAuth` | `@ApplicationScoped` facade: early-priority startup observer (resolves mode, fetches the first token, aborts startup on failure), exposes the channel `ClientInterceptor` |
| `ApiKeyInterceptor` | today's `AuthInterceptor` (moved/renamed) |
| `TokenFetchException` | wraps client errors; carries only the HTTP status or an error category |
| `BearerCallCredentials` | per-call `CallCredentials` applying `authorization: Bearer <token>`; records the token it used |
| `UnauthenticatedRetryInterceptor` | on `UNAUTHENTICATED` invalidate the token the call used and replay the call once (see "Reactive refresh") |

`TokenSource` isolates the Quarkus extension. The spike (section 8, step 0) found no blocker, so
the extension is used; the interface keeps a small JDK `HttpClient` implementation possible if a
later problem appears (e.g. with a real Keycloak).

### Startup ordering and fail-fast

Problem: `GrpcAdminEventClient.onStart(@Observes StartupEvent)` calls `connect()`, which loads
the checkpoint (an authenticated gRPC call) and whose whole body is wrapped in
`catch (Exception e) { … scheduleReconnect(); }`. In `oidc` mode this has two failure modes:
(1) if the token provider is not ready when `connect()` runs, the first call fails; (2) if the
first token cannot be obtained, the failure is swallowed by that catch-all and becomes a
reconnect loop (2^n seconds, capped at 60 s): the application starts, stays up and processes
nothing.

Design:

- `MemoryServiceAuth` owns one `StartupEvent` observer with an early `@Priority` (lower than
  the default and than `GrpcAdminEventClient.onStart`, which keeps the default priority).
- In that observer it resolves the mode (`AuthModeResolver`, including the
  `worker.id == client-id` check and the OIDC URL guard) and, in `oidc` mode, fetches the first
  token synchronously through `OidcTokenProvider`.
- Any exception from that observer (configuration error, failed fetch, missing or non-positive
  `expires_in`) aborts application startup. It never goes through
  `GrpcAdminEventClient.connect()`, so it can never turn into the reconnect loop.
- `api-key` mode runs the same observer for validation only (no network access).
- `GrpcAdminEventClient` itself is not changed for this; the ordering and the abort are
  verified by the `@QuarkusTest` startup tests (section 7, item 7).

### Token provider behaviour (mirrors the Go reference)

- The first token is fetched at startup as described above, and a failed first fetch is fatal.
  Later fetch failures are never fatal (back-off below).
- Background work runs on `ExecutorScheduler`: two daemon threads (one for renewal and fetches,
  one for wait time-outs) behind the `Scheduler` interface; the clock and scheduler are
  injectable. Renewal is scheduled at `expiresAt - min(30s, 10% of the remaining
  lifetime)`.
- While a renewal is in flight the still-valid cached token keeps being served.
- Renewal failure: retry with back-off 100 ms doubling to 30 s, forever, until shutdown; the
  cached token keeps being served until it expires. Back-off resets after a success.
- Only an expired (or invalidated) token makes callers wait for the next token. Waiting is
  bounded by `WAIT_TIMEOUT` (10 s); on timeout the call fails with `UNAVAILABLE`.
- `invalidate(rejectedToken)` is a no-op unless `rejectedToken` is the currently cached one
  (concurrent callers rejected with an old token must not discard a fresh token); otherwise it
  triggers one immediate renewal (single-flight). Such refetches are spaced at least 1 s after
  the last successful fetch (`MIN_INVALIDATE_REFETCH_INTERVAL`), so a server that keeps
  rejecting fresh tokens cannot hammer the token endpoint.
- Errors are sanitised: no secret, no response body, in messages or logs. Redirects are not
  followed.
- Shutdown: `close()` in `@PreDestroy` stops renewal and cancels the in-flight fetch, but the
  cached token keeps being served until it expires (then calls fail fast). The provider does not
  need to outlive `GrpcAdminEventClient.onShutdown`, so bean destruction order does not matter.

### Channel integration

- `GrpcChannelFactory` gains `create(String host, int port, ClientInterceptor auth)`; the
  apiKey overloads are removed once all call sites are migrated (static helper stays, no
  CDI change to its callers beyond one injected field).
- Each of the 13 call sites: inject `MemoryServiceAuth`, replace
  `memoryService.apiKey()[, clientId()]` by `auth.channelInterceptor()`.
- `api-key` mode: `ApiKeyInterceptor` sends `x-api-key` and always `x-client-id` (the 7
  three-argument sites did not send it before; the server ignores `x-client-id` outside test
  builds, so this is harmless and removes an inconsistency).
- `oidc` mode: the interceptor adds per-call `BearerCallCredentials` and the retry interceptor;
  **no** `x-api-key` and no `x-client-id` (identity comes from the token's `azp`).
- `GrpcAdminEventClient.subscribeToEvents`: remove its private `CallCredentials` (the channel
  already authenticates).

### Reactive refresh: retry after `UNAUTHENTICATED`

Why both proactive and reactive refresh. Proactive renewal (before the expected expiry) handles
normal expiry. The reactive path covers a token that memory-service rejects *before* the
expected expiry (revocation, key rotation, clock skew, expiry in flight). Without it every
call would fail with the cached, rejected token until the next scheduled renewal. A
reactive-only design was rejected: it would cost a failed call, a fetch and a replay at every
expiry, add server-side authentication-failure noise, and contradict the issue's acceptance
criteria (background renewal, cached token during renewal).

How it works (`oidc` mode only; not installed in `api-key` mode, where a static key cannot be
refreshed):

- `CallCredentials` attaches the token. It is a per-call instance that records the token it
  applied, and it waits without blocking when the token is expired and a renewal is in flight.
- `UnauthenticatedRetryInterceptor` wraps each `ClientCall`: it buffers the request message and
  the `request(n)` calls. When the call closes with `UNAUTHENTICATED` it **always** calls
  `invalidate(usedToken)`, and **replays once** with fresh per-call credentials only if no
  response message has reached the listener yet. Otherwise it forwards the status.
  - Unary calls: always replayable (a unary call has no message before the status).
  - `SubscribeEvents`: replayed only before the first event. After events have arrived the
    status reaches `GrpcAdminEventClient.handleError`, whose existing reconnect resumes from the
    saved cursor; the token was already invalidated, so the reconnect gets a fresh one.
  - A second `UNAUTHENTICATED` is propagated to the caller as today.
- `invalidate(rejectedToken)` only acts if `rejectedToken` is still the cached token, so
  concurrent rejections of the same old token cause exactly one refresh and never discard a
  newer token.
- Replay is safe even for non-idempotent writes: the server rejects an unauthenticated call
  before processing it.
- The 12 services need no change; the existing `StatusRuntimeException` handling still applies
  to a second rejection.

### Quarkus OIDC client usage

Created programmatically with `OidcClients.newClient(OidcClientConfig)`, only in `oidc` mode (so
`api-key` mode never contacts Keycloak): `discovery-url` → discovery enabled with
`auth-server-url`; `token-url` → discovery disabled with an absolute token path.
`quarkus-oidc-client` is added to `pom.xml`.

A client built in code does not read `quarkus.oidc-client.*`, so everything it needs is passed
explicitly from `memory-service.auth.oidc.*`. Verified by the spike on Quarkus 3.37.1:

- **Token URL**: an absolute `tokenPath` with `discoveryEnabled(false)` works, with or without
  `authServerUrl`.
- **Discovery** works and is lazy: `newClient` does not contact the provider, so an unreachable
  discovery URL shows up on the first `fetch()` (which is fatal at startup anyway). The issuer in the discovery document is not validated against
  `authServerUrl` (checked against a fake server).
- **Client authentication** is Basic (`client_secret_basic`) by default; `scope` and `audience`
  are sent as form parameters.
- **No caching**: `OidcClient.getTokens()` performs a new grant on every call, so
  `OidcTokenProvider` owns the cache.
- **Redirects**: set `followRedirects(false)` explicitly. A 302 from the token endpoint is not
  followed anyway and surfaces as a raw `NullPointerException`, so `QuarkusOidcTokenSource`
  wraps every `Throwable`, not only `OidcClientException`.
- **Timeouts**: the client's own timeout is Mutiny's `io.smallrye.mutiny.TimeoutException`, not
  `java.util.concurrent.TimeoutException`; both are categorised as "timed out".
- **Error content**: a 400 yields an `OidcClientException` whose message is the whole response
  body, and a malformed 200 yields a `DecodeException` with body content. `TokenFetchException`
  carries only an HTTP status or an error category. The client secret and the body did not appear
  in the JUL logs during the spike.
- **Missing `expires_in`**: no exception; `Tokens.getAccessTokenExpiresAt()` is `null`. The
  source rejects it (and a non-positive value): startup failure on the first fetch, a failed
  attempt (back-off) on a renewal.
- **Unreachable endpoint**: fails within milliseconds (`OIDC Server is not available`) with no
  hidden retry delay, so our own back-off governs timing.
- **Default client**: with no `quarkus.oidc-client.*` configuration `OidcClients.getClient()` is
  a disabled client and makes no network call, so `api-key` mode is safe.
- **TLS**: optional `memory-service.auth.oidc.tls-configuration-name` names a `quarkus.tls.<name>`
  entry passed to the builder (`tlsConfigurationName`). Trust store, custom CA and trust-all for
  development are configured in that entry; there are no CA or skip-verify properties and the
  builder has no `verification` setter. Without the entry a self-signed endpoint fails with the
  generic `OIDC Server is not available`.
- **Dev services**: with the extension on the classpath and no `auth-server-url`, Quarkus tries
  to start a Keycloak container in dev and test mode. `quarkus.keycloak.devservices.enabled=false`
  is **mandatory** in `src/main/resources/application.properties` and in the test configuration.
- **Secret from a file**: no `*_FILE` property. `smallrye.config.source.file.locations` does not
  work out of the box; it needs the dependency `io.smallrye.config:smallrye-config-source-file-system`
  (managed by the Quarkus BOM). A file named `memory-service.auth.oidc.client-secret` in the
  mounted directory then supplies the property. `quarkus.config.locations=<properties file>` also
  works without the extra dependency.

## 5. Security notes

- The gRPC channel to memory-service is plaintext today (`usePlaintext()`); in `oidc` mode the
  bearer token also travels in clear unless the network is trusted. TLS to memory-service is out
  of scope for #69 (follow-up issue) but must be called out in the README.
- OIDC client exceptions can carry the provider's response body: they are wrapped in a
  `TokenFetchException` that exposes only the HTTP status or an error category.
- The secret is never logged; config `toString`/error paths must not include it.
- API key and bearer are never sent together (the API key would set the effective client
  identity).

## 6. Keycloak / compose (dev environment)

- Add a confidential service-account client `cognition_processor` to
  `deploy/keycloak/memory-service-realm.json` of memory-service: `serviceAccountsEnabled: true`,
  standard flow and direct grants off, `fullScopeAllowed: false`,
  `defaultClientScopes: ["profile"]`, `oidc-audience-mapper` for `memory-service`.
- No memory-service server change: `cognition_processor` is already in
  `MEMORY_SERVICE_ROLES_ADMIN_CLIENTS`.
- Do not reuse `memory-service-client` (shared by the sample apps).
- Keycloak's `--import-realm` skips an existing realm: recreate the Keycloak DB, or add the
  client through the admin API.
- Compose env for the `cognition-processor` service (OIDC run): `MEMORY_SERVICE_CLIENT_ID` and
  `COGNITION_WORKER_ID` = `cognition_processor`, `MEMORY_SERVICE_OIDC_CLIENT_SECRET`,
  `MEMORY_SERVICE_OIDC_DISCOVERY_URL=http://keycloak:8080/realms/memory-service` (or
  `..._TOKEN_URL`), no `MEMORY_SERVICE_API_KEY`.
- `http://keycloak:8080` is not loopback, so the compose service also needs
  `MEMORY_SERVICE_OIDC_ALLOW_INSECURE=true`.
- If `MEMORY_SERVICE_OIDC_ALLOWED_CLIENTS` is set on the memory-service server, it must include
  `cognition_processor` (document this in the README).
- The realm file and the `cognition-processor` compose service live in chirino/memory-service,
  so these changes are a PR to that repository. It is raised only after the processor PR works
  end to end (manual runs use a local realm client and compose override until then).

## 7. Test plan

Unit tests are plain JUnit (no Quarkus boot) unless stated; new code goes under
`src/test/java/.../auth/`.

1. **`AuthModeResolverTest`** (table): every row of the mode table; blank values; both URLs;
   neither URL; missing secret; secret only; audience, scope, allow-insecure or
   tls-configuration-name alone (switch `auto` to `oidc`, then fail validation); `method=oidc`
   with nothing; `cognition.worker.id` / `memory-service.client-id` mismatch rejected in both
   modes; error text names the property and never the secret.
2. **`OidcUrlValidatorTest`**: https ok; http with `localhost`, `127.0.0.1`, `[::1]` ok; http
   remote rejected, accepted with `allow-insecure`; relative URL, non-http scheme, empty host,
   user info, fragment rejected.
3. **`OidcTokenProviderTest`** with a fake `TokenSource`, manual clock and fake scheduler
   (deterministic, no sleeping):
   - first fetch failure or `expires_in <= 0` → startup exception;
   - renewal time = expiry − min(30 s, 10% remaining) (1 h, 100 s and very short lifetimes);
   - cached token served while a renewal is blocked in flight;
   - expired token: caller waits for the renewal, times out with `UNAVAILABLE`;
   - renewal failures: back-off 100 ms, 200 ms, … capped at 30 s, never gives up, resets after
     success; cached token served until expiry;
   - `invalidate`: stale token ignored; current token triggers exactly one fetch for N
     concurrent callers;
   - shutdown (`close()`): renewal stopped, in-flight fetch cancelled, the valid cached token
     is still served until it expires, then calls fail fast;
   - concurrency: many threads, one fetch;
   - sanitised errors (no secret in message or log).
4. **`QuarkusOidcTokenSourceTest`** (mocked `OidcClient`: expiry validation, error categories,
   no leak of message or secret, timeout, `buildClientConfig` fields) and
   **`QuarkusOidcTokenSourceClientTest`** (`@QuarkusTest` with the real `OidcClients` bean against
   a JDK `HttpServer`; no new test dependency; equivalent of Go `httptest`): form body (`grant_type=client_credentials`,
   client credentials, scope, audience); discovery document → `token_endpoint`; 302 not
   followed; non-2xx and malformed JSON sanitised; missing `expires_in`; token-url mode makes
   no discovery request.
5. **Interceptor tests** (like `GrpcChannelFactoryTest`): `oidc` mode sends only `authorization`;
   `api-key` mode sends `x-api-key` + `x-client-id`. `UnauthenticatedRetryInterceptor`:
   - unary call rejected once: invalidates the token it was sent with, replays once with the
     new token, caller sees the response;
   - rejected twice: second `UNAUTHENTICATED` propagated, no third attempt;
   - server-streaming call rejected before any message: replayed once;
   - server-streaming call rejected after a message: not replayed, status propagated, token
     still invalidated;
   - the replay carries the buffered request message and `request(n)` calls;
   - N concurrent calls rejected with the same old token: exactly one refresh, all replay with
     the new token;
   - a call rejected with an old token while a newer one is already cached does not discard
     the newer token;
   - other status codes: no invalidation, no replay;
   - not installed in `api-key` mode.
6. **`GrpcAdminEventClientTest` additions**: a stream rejected after events were delivered
   reaches `handleError` and reconnects from the saved cursor with the refreshed token; the
   final checkpoint save in `onShutdown` still succeeds after the provider was closed (cached
   token served until expiry).
7. **Startup and wiring**. Delivered as `MemoryServiceAuthTest` (plain JUnit with mocked
   `OidcClients`: api-key mode never touches the client, a partial or failing OIDC configuration
   aborts startup and closes the client, idempotent initialisation, shutdown) and
   `UnauthenticatedRetryInterceptorTest` (a real Netty gRPC server with a bearer-checking
   interceptor, so the bearer on the wire and the refresh plus replay are covered). Booting the
   whole application with a broken OIDC profile was not added: `@QuarkusTest` cannot assert a
   failed boot cleanly. The original wish list:
   - `oidc` profile: app starts, mode is `oidc`, calls carry a bearer token; a server-side
     `UNAUTHENTICATED` triggers refresh + one replay;
   - `api-key` profile: app starts and **no request reaches the token server**;
   - partial `oidc` profile (including audience or scope alone): startup fails with the
     expected message, and does **not** end up in the `GrpcAdminEventClient` reconnect loop;
   - first token unobtainable (token endpoint down or no `expires_in`): startup fails.
8. **Manual end-to-end (docker compose, real Keycloak)**:
   - first run with the dedicated realm client; check `azp=cognition_processor` in the token;
   - short access-token lifespan (about 60 s) to observe renewal in the logs;
   - stop Keycloak: processing continues until expiry, then recovers with back-off after
     restart;
   - wrong secret: startup fails; internal discovery URL vs public issuer works (or fall back to
     `token-url`);
   - rollback: `MEMORY_SERVICE_AUTH_METHOD=api-key` with the OIDC variables still set;
   - extract a memory through the chat UI flow from AGENTS.md and confirm it is stored.

### Coverage

Measured on the new package (step 3): 96% lines, 92% branches.

The >80% JaCoCo target applies to the **new code only**: add a `PACKAGE` rule with
`includes` = `io.github.rigazilla.memory.cognition.auth` (LINE and BRANCH ≥ 0.80) to the
`check-coverage` execution in `pom.xml`. The BUNDLE rule (15% / 10%) is left unchanged. Unit
tests (items 1–6) provide the coverage; the `@QuarkusTest` items are not relied on for it.

## 8. Implementation order

0. **Spike: done** (scratch project in the git-ignored `target/oidc-spike/`, nothing in the
   module changed). Results are in "Quarkus OIDC client usage". Decision: use the Quarkus OIDC
   client behind `TokenSource`; the JDK `HttpClient` fallback is not needed. Still to verify
   with Docker running: discovery against a real Keycloak where the internal URL differs from
   the public issuer (the spike used a fake server), and the compose end-to-end run.
1. Config + `AuthModeResolver` + `OidcUrlValidator` + tests (no behaviour change in `api-key`
   mode; `apiKey()` optional, `application.properties` default). Align
   `src/test/resources/application.properties` (`memory-service.client-id=test-client` vs
   `cognition.worker.id=test-worker`) because `worker.id == client-id` is now validated in
   both modes.
2. `MemoryServiceAuth` + `ApiKeyInterceptor` + `GrpcChannelFactory` overload; migrate the 13
   call sites; remove the private credentials in `GrpcAdminEventClient`; update
   `GrpcChannelFactoryTest`. `api-key` mode must be behaviourally unchanged.
3. `TokenSource`, `TokenFetchException`, `OidcTokenProvider` (proactive renewal, early-priority
   startup observer, `@PreDestroy` close), then `BearerCallCredentials` and
   `UnauthenticatedRetryInterceptor` (reactive refresh and replay). Proactive renewal can land
   before the retry interceptor, but the feature is not complete without it.
4. `@QuarkusTest` integration tests; JaCoCo package rule.
5. README / `env.example` / `AGENTS.md` configuration docs (incl. the plaintext-gRPC note and
   the `MEMORY_SERVICE_OIDC_ALLOWED_CLIENTS` note); manual end-to-end run with a local realm
   client and compose override.
5b. After the processor PR works end to end: PR to chirino/memory-service with the realm client
   and compose changes.
6. Mark the two older TODO documents as superseded (the gRPC TLS follow-up is issue #71).

## 9. Open items and follow-ups

- Discovery with a real Keycloak (internal URL vs public issuer) and the compose run: needs
  Docker (not running on the development machine at the time of the spike).
- Item 6 (`GrpcAdminEventClientTest` additions) was not written: the no-replay-after-first-event
  rule is covered in `UnauthenticatedRetryInterceptorTest`, and the "cached token served after
  provider close" rule in `OidcTokenProviderTest`; the checkpoint-on-shutdown path itself is
  only exercised by the manual run.

Resolved: the bounded wait for an expired token is 10 s (`WAIT_TIMEOUT`); the realm and compose changes are delivered as a PR to chirino/memory-service; the #69
body matches this design; the gRPC TLS follow-up is issue #71.
