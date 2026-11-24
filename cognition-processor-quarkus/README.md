# Cognition Processor - Quarkus Implementation

A Quarkus-based implementation of the Memory Service cognition layer that processes conversation events to extract and organize memories.

## What is this?

This project implements the **cognition layer** of a two-layer memory architecture:
- **Substrate layer** ([memory-service](https://github.com/chirino/memory-service)) - stores raw conversation data and manages access control
- **Cognition layer** (this project) - processes events to extract topics, facts, preferences, and other derived memories

Think of it as the "intelligence" that turns raw conversation transcripts into structured, searchable memories.

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.9+
- Memory Service running - see [../memory-service](../memory-service) for local setup instructions

### Run It

1. **Start the cognition processor**:
   ```bash
   ./mvnw quarkus:dev
   ```

2. **Verify it's running**:
   - Check logs for "Started AdminEventClient - connected to memory-service"
   - Health checks:
     - Liveness: http://localhost:8090/q/health/live
     - Readiness: http://localhost:8090/q/health/ready (includes gRPC connection status)
     - Full health: http://localhost:8090/q/health

The processor will automatically:
- Subscribe to memory-service events
- Process new conversations
- Extract and verify memories
- Write them back to memory-service

## How It Works

```
Memory Service Events → Debounce → Evidence Packs → 
Extract (LLM) → Verify → Write Memories
```

See [AGENTS.md](./AGENTS.md) for architecture details and [Enhancement 099](https://github.com/chirino/memory-service/blob/main/docs/enhancements/099-quarkus-cognition-processor.md) for full specification.

## Configuration

Default configuration works with local development setup. Key settings in `src/main/resources/application.properties`:
- Memory Service connection: `memory-service.grpc.host` / `memory-service.grpc.port`
- LLM provider: `quarkus.langchain4j.*.chat-model.provider` (default: Ollama)
- Models: `memory` model for extraction/verification, `topic-summary` for summaries

Environment variable examples:
- `env.example` for local host-based runs
- `env.example` for both local and Docker-based runs

### Authentication to memory-service

The processor authenticates its gRPC calls with either a fixed API key (default, quickstart) or an
OIDC client-credentials token (for example from Keycloak) that is renewed automatically.
`memory-service.client-id` (default `cognition_processor`) is the client ID in both modes and must
equal `cognition.worker.id`; startup fails otherwise.

| Property | Environment variable | Meaning |
|---|---|---|
| `memory-service.auth.method` | `MEMORY_SERVICE_AUTH_METHOD` | `auto` (default), `api-key` or `oidc` |
| `memory-service.api-key` | `MEMORY_SERVICE_API_KEY` | API key (api-key mode) |
| `memory-service.auth.oidc.client-secret` | `MEMORY_SERVICE_OIDC_CLIENT_SECRET` | client secret (required in oidc mode) |
| `memory-service.auth.oidc.discovery-url` | `MEMORY_SERVICE_OIDC_DISCOVERY_URL` | issuer base URL, e.g. `http://keycloak:8080/realms/memory-service`; `/.well-known/openid-configuration` is appended |
| `memory-service.auth.oidc.token-url` | `MEMORY_SERVICE_OIDC_TOKEN_URL` | absolute token endpoint; exclusive with `discovery-url` |
| `memory-service.auth.oidc.audience` | `MEMORY_SERVICE_OIDC_AUDIENCE` | optional `audience` request parameter |
| `memory-service.auth.oidc.scope` | `MEMORY_SERVICE_OIDC_SCOPE` | optional, comma-separated scopes |
| `memory-service.auth.oidc.allow-insecure` | `MEMORY_SERVICE_OIDC_ALLOW_INSECURE` | development only: allow plain `http://` to a non-loopback host |
| `memory-service.auth.oidc.tls-configuration-name` | `MEMORY_SERVICE_OIDC_TLS_CONFIGURATION_NAME` | optional `quarkus.tls.<name>` entry (custom CA) for the token requests |

Mode selection:
- `auto`: if **any** `memory-service.auth.oidc.*` value is set, OIDC is used and fully validated
  (secret, exactly one of `discovery-url` / `token-url`, https unless loopback or `allow-insecure`).
  A partial OIDC configuration **fails startup**; it never falls back to the API key.
  Otherwise the API key is used and no OIDC client is created.
- `api-key`: always the API key; the OIDC settings are ignored (rollback path).
- `oidc`: always OIDC; the API key is not used.

Wire behaviour: in oidc mode only `Authorization: Bearer <token>` is sent. In api-key mode only
`x-api-key` (plus `x-client-id`) is sent. The first token is fetched at startup (a failure aborts
startup); it is then renewed shortly before it expires, with exponential back-off on failures
(100 ms up to 30 s) while the cached token keeps being used. A call rejected with `UNAUTHENTICATED`
invalidates the token and is replayed once with a fresh one.

Notes:
- The gRPC channel to memory-service is **plaintext** (`usePlaintext()`), so in oidc mode the bearer
  token travels unencrypted unless the network is trusted (TLS: issue #71).
- memory-service must accept the token's client: with its OIDC mode enabled, list the client in
  `MEMORY_SERVICE_OIDC_ALLOWED_CLIENTS` (and grant the admin role as for the API-key client).
- Secret from a mounted file (for example a Kubernetes secret): put a file named
  `memory-service.auth.oidc.client-secret` in a directory and set
  `SMALLRYE_CONFIG_SOURCE_FILE_LOCATIONS=/path/to/dir`.
- Environment variables use the `MEMORY_SERVICE_OIDC_*` names above (not
  `MEMORY_SERVICE_AUTH_OIDC_*`); `application.properties` maps them explicitly.

### Salience filtering

Low-salience events (greetings, filler words, etc.) are scored and dropped before the extraction
pipeline at two points: when the event arrives (`handleEvent`) and again at extraction time
(`filterEvidenceForBatch`) to catch context entries the first gate never saw.

All keys live under the `salience.*` prefix.

| Key | Default | Description |
|-----|---------|-------------|
| `salience.enabled` | `true` | Enable/disable the filter entirely. |
| `salience.threshold` | `0.3` | Events scoring at or below this value are dropped. |
| `salience.min-length` | `10` | Events shorter than this (chars) score 0.1 and are dropped. |
| `salience.metrics.enabled` | `true` | Expose `eventsFiltered` / `eventsKept` counters. |
| `salience.pattern.greeting-enabled` | `true` | Drop events that match the greeting pattern. |
| `salience.pattern.acknowledgment-enabled` | `true` | Drop events that match the acknowledgment pattern. |
| `salience.pattern.farewell-enabled` | `true` | Drop events that match the farewell pattern. |
| `salience.pattern.filler-enabled` | `true` | Drop events that match the filler pattern. |
| `salience.pattern.thanks-enabled` | `true` | Drop events that match the thanks pattern. |
| `salience.pattern.greetings` | `hi,hello,hey,…` | Comma-separated greeting terms. Override to localise. |
| `salience.pattern.acknowledgments` | `ok,understood,…` | Comma-separated acknowledgment terms. |
| `salience.pattern.farewells` | `bye,goodbye,…` | Comma-separated farewell terms. |
| `salience.pattern.fillers` | `um,hmm,…` | Comma-separated filler terms. |
| `salience.pattern.thanks` | `thanks,thank you,…` | Comma-separated thanks terms. |
| `salience.keywords.list` | _(absent)_ | Inline comma-separated keywords. Highest priority — overrides file and bundled defaults. |
| `salience.keywords.file` | _(absent)_ | Path to an external keyword file (one per line, `#` comments ignored). Overrides bundled defaults. |

#### Keyword source priority and fallback

Keywords are loaded from the first source that is configured, in priority order:

1. `salience.keywords.list` — inline list in config (highest priority)
2. `salience.keywords.file` — path to an external file
3. Bundled classpath resource `salience/default-keywords.txt` (ships with the jar)

If an external source (`list` or `file`) is configured but parses to zero keywords (empty, or
contains only blank lines and `#` comments), a WARN is logged and the service falls back to the
bundled defaults. This preserves recall while signalling the misconfiguration. A missing or empty
bundled resource aborts startup.

The bundled file ships 17 keywords across four categories (technical, preference, decision,
procedure). To inspect or replace them: `src/main/resources/salience/default-keywords.txt`.

To localise pattern terms (e.g. Spanish), supply overrides in `application.properties` or via
a separate file pointed to by `smallrye.config.locations`:

```properties
# application.properties — Spanish override example
salience.pattern.greetings=hola,buenos días,buenas tardes
salience.pattern.acknowledgments=vale,entendido
salience.pattern.farewells=adiós,hasta luego
salience.pattern.fillers=pues,bueno
salience.pattern.thanks=gracias,muchas gracias
```

## Docker Dev/Test

A JVM-based Docker setup is included for local integration testing.

### Prerequisites
- Docker
- Memory Service running on the host at `http://localhost:8082`
- Optional Ollama running on the host at `http://localhost:11434` if you use Ollama-backed models

### Build the application
```bash
./mvnw package
```

### Configure Docker runtime
```bash
cp env.example .env
```

Then edit `.env.docker` as needed:
- set `OPENAI_API_KEY` if using the default OpenAI-compatible memory model configuration
- or switch the memory model provider to Ollama by editing `env.example`

### Start the container
```bash
docker compose up --build
```

The containerized cognition processor will be available on:
- `http://localhost:8090`

### Logs
Container logs:
```bash
docker compose logs -f cognition-processor
```

Persisted Quarkus log file:
```bash
tail -f logs/quarkus.log
```

### Notes
- The compose file maps `host.docker.internal` so the container can reach host services on Linux
- The container expects Memory Service on `host.docker.internal:8082`
- The compose file mounts `./logs` to `/deployments/logs` so file logging remains available

## Project Status

**Currently Implemented** (see [DONE/](./DONE/) folder for details):
- ✅ gRPC event stream client
- ✅ Debounce windows and batching
- ✅ Job processing pipeline
- ✅ Memory extraction (5 types: fact, preference, procedure, problem_solution, decision)
- ✅ Citation verification
- ✅ Provenance tracking

**Not Yet Implemented** (see [TODO/](./TODO/) folder):
- ❌ Memory consolidation (deduplication, merging)
- ❌ Topic summary extraction
- ❌ Cache-only notes (bridge, topic)
- ❌ Prompt caching optimization

## For New Contributors

- 📖 Start with [AGENTS.md](./AGENTS.md) for project context
- 📝 Browse [TODO/](./TODO/) folder for work items
- ✅ Check [DONE/](./DONE/) folder to understand what's completed

## Learn More

### About This Project
- [AGENTS.md](./AGENTS.md) - Project overview and guidelines for AI assistants
- [Enhancement 099](https://github.com/chirino/memory-service/blob/main/docs/enhancements/099-quarkus-cognition-processor.md) - Implementation specification
- [TODO/gap-analysis-model-backed-extraction.md](./TODO/gap-analysis-model-backed-extraction.md) - Current vs spec comparison

### About Memory Service
- [Memory Service Repository](https://github.com/chirino/memory-service)
- [Core Concepts](https://chirino.github.io/memory-service/docs/concepts/) - Conversations, entries, memories, access control
- [Quarkus Guide](https://chirino.github.io/memory-service/docs/quarkus/) - Quarkus integration patterns
- [Memory Cognition Architecture](https://github.com/chirino/memory-service/blob/main/docs/memory-cognition.md) - Two-layer design

### Technologies
- [Quarkus](https://quarkus.io/) - Application framework
- [LangChain4j](https://docs.langchain4j.dev/) - LLM integration
- [Ollama](https://ollama.ai) - Local LLM runtime

## License

Apache License 2.0