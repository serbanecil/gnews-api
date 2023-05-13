package de.serbanecil.gnewsapi.config;

import de.serbanecil.gnewsapi.exception.RestClientExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestClientExceptionHandler());
        return restTemplate;
    }
}
