package io.github.rigazilla.memory.cognition.config;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.rigazilla.memory.cognition.config.MemoryServiceConfig.Auth.Method;
import io.smallrye.config.SmallRyeConfig;
import io.smallrye.config.SmallRyeConfigBuilder;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** Binding of {@link MemoryServiceConfig} without starting Quarkus. */
class MemoryServiceConfigBindingTest {

    private static MemoryServiceConfig bind(Map<String, String> properties) {
        SmallRyeConfig config = new SmallRyeConfigBuilder()
                .withMapping(MemoryServiceConfig.class)
                .withSources(new io.smallrye.config.PropertiesConfigSource(properties, "test", 100))
                .build();
        return config.getConfigMapping(MemoryServiceConfig.class);
    }

    @Test
    void defaultsWhenNothingIsSet() {
        MemoryServiceConfig c = bind(Map.of());
        assertThat(c.clientId()).isEqualTo("cognition_processor");
        assertThat(c.apiKey()).isEmpty();
        assertThat(c.auth().method()).isEqualTo(Method.AUTO);
        assertThat(c.auth().oidc().clientSecret()).isEmpty();
        assertThat(c.auth().oidc().discoveryUrl()).isEmpty();
        assertThat(c.auth().oidc().tokenUrl()).isEmpty();
        assertThat(c.auth().oidc().audience()).isEmpty();
        assertThat(c.auth().oidc().scope()).isEmpty();
        assertThat(c.auth().oidc().allowInsecure()).isEmpty();
        assertThat(c.auth().oidc().tlsConfigurationName()).isEmpty();
    }

    @Test
    void emptyValuesFromUnsetEnvExpressionsAreTreatedAsUnset() {
        // application.properties uses ${ENV:} which resolves to an empty string when unset.
        MemoryServiceConfig c = bind(Map.of(
                "memory-service.api-key", "",
                "memory-service.auth.oidc.client-secret", "",
                "memory-service.auth.oidc.allow-insecure", "",
                "memory-service.auth.oidc.token-url", ""));
        assertThat(c.apiKey()).isEmpty();
        assertThat(c.auth().oidc().clientSecret()).isEmpty();
        assertThat(c.auth().oidc().allowInsecure()).isEmpty();
        assertThat(c.auth().oidc().tokenUrl()).isEmpty();
    }

    @Test
    void bindsAllValues() {
        MemoryServiceConfig c = bind(Map.ofEntries(
                Map.entry("memory-service.api-key", "k"),
                Map.entry("memory-service.client-id", "cid"),
                Map.entry("memory-service.auth.method", "api-key"),
                Map.entry("memory-service.auth.oidc.client-secret", "s"),
                Map.entry("memory-service.auth.oidc.discovery-url", "https://idp/realms/x"),
                Map.entry("memory-service.auth.oidc.token-url", "https://idp/token"),
                Map.entry("memory-service.auth.oidc.audience", "aud"),
                Map.entry("memory-service.auth.oidc.scope", "a,b"),
                Map.entry("memory-service.auth.oidc.allow-insecure", "true"),
                Map.entry("memory-service.auth.oidc.tls-configuration-name", "idp")));
        assertThat(c.apiKey()).contains("k");
        assertThat(c.clientId()).isEqualTo("cid");
        assertThat(c.auth().method()).isEqualTo(Method.API_KEY);
        assertThat(c.auth().oidc().clientSecret()).contains("s");
        assertThat(c.auth().oidc().discoveryUrl()).contains("https://idp/realms/x");
        assertThat(c.auth().oidc().tokenUrl()).contains("https://idp/token");
        assertThat(c.auth().oidc().audience()).contains("aud");
        assertThat(c.auth().oidc().scope()).contains("a,b");
        assertThat(c.auth().oidc().allowInsecure()).contains(true);
        assertThat(c.auth().oidc().tlsConfigurationName()).contains("idp");
    }

    @Test
    void methodNamesAreKebabCase() {
        assertThat(bind(Map.of("memory-service.auth.method", "oidc")).auth().method()).isEqualTo(Method.OIDC);
        assertThat(bind(Map.of("memory-service.auth.method", "auto")).auth().method()).isEqualTo(Method.AUTO);
    }
}
