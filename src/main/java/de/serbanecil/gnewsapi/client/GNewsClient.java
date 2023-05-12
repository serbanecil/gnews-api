package de.serbanecil.gnewsapi.client;

import de.serbanecil.gnewsapi.client.model.Response;
import de.serbanecil.gnewsapi.exception.GnewsApiException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GNewsClient {

    final Logger LOGGER = LoggerFactory.getLogger(GNewsClient.class);

    @Value("${request.defaults.category}")
    private String defaultCategory;

    @Value("${request.defaults.language}")
    private String defaultLanguage;

    @Value("${request.defaults.country}")
    private String defaultCountry;

    @Value("${request.defaults.maxArticles}")
    private String defaultMaxArticles;

    @Value("${client.apiKey}")
    private String apiKey;

    private final String BASE_URL_HEADLINES = "https://gnews.io/api/v4/top-headlines?";
    private final String BASE_URL_SEARCH = "https://gnews.io/api/v4/search?";

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("headlines")
    @CircuitBreaker(name = "headlines")
    @Retry(name = "retryFindHeadlines", fallbackMethod = "fallbackAfterRetry")
    public Response findHeadLines(String category, String language, String country, int max, String keywords) {

        RequestBuilder requestBuilder = new RequestBuilder(BASE_URL_HEADLINES, defaultCategory, defaultLanguage,
                defaultCountry, defaultMaxArticles)
                .addCategory(category)
                .addLanguage(language)
                .addCountry(country)
                .addMaxArticles(max)
                .addKeywords(keywords)
                .addApiKey(apiKey);

        String url = requestBuilder.getBasePath();
        try {
            LOGGER.info(String.format("Calling url: %s", url));
            return restTemplate.getForEntity(url, Response.class).getBody();
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage());
            throw new GnewsApiException(e.getMessage());
        }
    }

    public Response fallbackAfterRetry(Exception ex) {
        throw new GnewsApiException("The External Service is unavailable and all retries have been exhausted!");
    }


}
