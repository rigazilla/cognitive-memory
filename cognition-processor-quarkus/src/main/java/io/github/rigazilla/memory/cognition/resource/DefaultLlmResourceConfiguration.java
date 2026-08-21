package io.github.rigazilla.memory.cognition.resource;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation of LlmResourceConfiguration.
 */
public record DefaultLlmResourceConfiguration(String provider, String model,
                                              Double temperature, Integer maxTokens,
                                              Duration timeout, Optional<String> apiKey,
                                              Map<String, String> customProperties)
      implements LlmResourceConfiguration {

   public DefaultLlmResourceConfiguration(
         String provider,
         String model,
         Double temperature,
         Integer maxTokens,
         Duration timeout,
         Optional<String> apiKey,
         Map<String, String> customProperties) {
      this.provider = provider;
      this.model = model;
      this.temperature = temperature;
      this.maxTokens = maxTokens;
      this.timeout = timeout;
      this.apiKey = apiKey;
      this.customProperties = Map.copyOf(customProperties);
   }

   @Override
   public String toString() {
      return "DefaultLlmResourceConfiguration{" +
             "provider='" + provider + '\'' +
             ", model='" + model + '\'' +
             ", temperature=" + String.format(Locale.ROOT, "%.2f", temperature) +
             ", maxTokens=" + maxTokens +
             ", timeout=" + timeout +
             '}';
   }
}
