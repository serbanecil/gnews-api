package de.serbanecil.gnewsapi.client;

import de.serbanecil.gnewsapi.client.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Class used to make external calls to GNews.
 */
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

    @Value("${client.baseUrl.headlines}")
    private String BASE_URL_HEADLINES;

    @Value("${client.baseUrl.search}")
    private String BASE_URL_SEARCH;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Method used to call the external service in order the retrieve the headlines.
     *
     * @param category The category of the headlines
     * @param language The language of the headlines
     * @param country The country of the headlines
     * @param max The max number of headlines to fetch
     * @param keywords The keywords to look for when searching for the headlines
     * @return The requested headlines
     */
    @Cacheable("headlines")
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
        LOGGER.info(String.format("Calling url: %s", url));
        return restTemplate.getForEntity(url, Response.class).getBody();
    }

    /**
     * Method used to call the external service in order the search for news articles.
     *
     * @param category The category of the news articles.
     * @param language The language of the news articles.
     * @param country The coutnry of the news articles.
     * @param max The max number of news articles to fetch
     * @param keywords The keywords to look for when searching for news articles
     * @param inKeyword Where to search for the given keywords.
     * @return The requested news articles
     */
    @Cacheable("news")
    public Response findNews(String category, String language, String country, int max, String keywords, String inKeyword) {
        RequestBuilder requestBuilder = new RequestBuilder(BASE_URL_SEARCH,  defaultCategory, defaultLanguage,
                defaultCountry, defaultMaxArticles)
                .addKeywords(keywords, inKeyword)
                .addCategory(category)
                .addLanguage(language)
                .addCountry(country)
                .addMaxArticles(max)
                .addApiKey(apiKey);

        String url = requestBuilder.getBasePath();
        LOGGER.info(String.format("Calling url: %s", url));
        return restTemplate.getForEntity(url, Response.class).getBody();
    }




}
