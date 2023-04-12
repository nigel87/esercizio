package it.nigelpllaha.esercizio.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
@Configuration
public class EsercizioConfig {
    private static final String HEADER_AUTH_SCHEMA = "Auth-Schema";
    private static final String HEADER_AUTH_SCHEMA_VALUE = "S2S";
    private static final String HEADER_API_KEY = "Api-Key";
    private  final String  API_KEY;
    private static final Long CONNECTION_TIMEOUT_SECONDS  = 10L;
    private static final Long READ_TIMEOUT_SECONDS  = 10L;

    public EsercizioConfig (EsercizioConfigProperties properties){
        this.API_KEY = properties.fabrickApiKey();
     }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Aggiungi API KEY
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add(HEADER_AUTH_SCHEMA, HEADER_AUTH_SCHEMA_VALUE);
            request.getHeaders().add(HEADER_API_KEY,API_KEY );
            return execution.execute(request, body);
        };

        return builder
                .additionalInterceptors(interceptor)
                .setConnectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT_SECONDS))
                .setReadTimeout(Duration.ofSeconds(READ_TIMEOUT_SECONDS))
                .build();
    }



}
