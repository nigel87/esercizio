package it.nigelpllaha.esercizio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties ("esercizio")
public record EsercizioConfigProperties(String apiUrl, String version, String fabrickApiUrl, String fabrickApiKey  ) {
}
