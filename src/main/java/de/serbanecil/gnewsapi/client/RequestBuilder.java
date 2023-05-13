package de.serbanecil.gnewsapi.client;

import de.serbanecil.gnewsapi.exception.GnewsApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Helper class used to create the URI for the api calls
 */
public class RequestBuilder {

    public RequestBuilder(String basePath, String defaultCategory, String defaultLanguage, String defaultCountry,
                          String defaultMaxArticles) {
        this.basePath = basePath;
        this.defaultCategory = defaultCategory;
        this.defaultLanguage = defaultLanguage;
        this.defaultCountry = defaultCountry;
        this.defaultMaxArticles = defaultMaxArticles;
    }

    final Logger LOGGER = LoggerFactory.getLogger(RequestBuilder.class);

    private final String defaultCategory;
    private final List<String> allowedCategories = List.of("general", "world", "nation", "business", "technology",
            "entertainment", "sports", "science", "health");


    private final String defaultLanguage;
    private final List<String> allowedLanguages = List.of("ar", "zh", "nl", "en", "fr", "de", "el", "he", "hi", "it",
            "ja", "ml", "mr", "no", "pt", "ro", "ru", "es", "sv", "ta", "te", "uk");


    private final String defaultCountry;
    private final List<String> allowedCountries = List.of("ar", "br", "ca", "eg", "fr", "de", "gr", "hk", "in", "ie",
            "il", "it", "jp", "nl", "no", "pk", "pe", "ph", "pt", "ro", "ru", "sg", "es", "se", "ch", "tw", "ua", "gb", "us");

    private final String defaultMaxArticles;
    private String basePath;


    public String getBasePath() {
        return basePath.replace("?&", "?");
    }

    /**
     * Adds the given category to the basePath
     *
     * @param category The given category
     * @return The new url with category query param
     */
    public RequestBuilder addCategory(String category) {
        String CATEGORY = "&category=";
        if (StringUtils.isBlank(category)) {
            basePath = basePath + CATEGORY + defaultCategory;
            return this;
        }
        if (!allowedCategories.contains(category.toLowerCase())) {
            String errorErrorResponse = String.format("The category %s is not allowed. Please choose one of: %s",
                    category, allowedCategories);
            LOGGER.error(errorErrorResponse);
            throw new GnewsApiException(errorErrorResponse);
        }
        basePath = basePath + CATEGORY + category.toLowerCase();
        return this;
    }

    /**
     * Adds the given language to the basePath
     *
     * @param language The given language
     * @return The new url with language query param
     */
    public RequestBuilder addLanguage(String language) {
        String LANGUAGE = "&language=";
        if (StringUtils.isBlank(language)) {
            basePath = basePath + LANGUAGE + defaultLanguage;
            return this;
        }
        if (!allowedLanguages.contains(language.toLowerCase())) {
            String errorErrorResponse = String.format("The language %s is not allowed. Please choose one of: %s",
                    language, allowedLanguages);
            LOGGER.error(errorErrorResponse);
            throw new GnewsApiException(errorErrorResponse);
        }
        basePath = basePath + LANGUAGE + language.toLowerCase();
        return this;
    }

    /**
     * Adds the given country to the basePath
     *
     * @param country The given country
     * @return The new url with country query param
     */
    public RequestBuilder addCountry(String country) {
        String COUNTRY = "&country=";
        if (StringUtils.isBlank(country)) {
            basePath = basePath + COUNTRY + defaultCountry;
            return this;
        }
        if (!allowedCountries.contains(country.toLowerCase())) {
            String errorErrorResponse = String.format("The country %s is not allowed. Please choose one of: %s",
                    country, allowedCountries);
            LOGGER.error(errorErrorResponse);
            throw new GnewsApiException(errorErrorResponse);
        }
        basePath = basePath + COUNTRY + country.toLowerCase();
        return this;
    }

    /**
     * Adds the given max number of articles to the basePath
     *
     * @param maxArticles The given country
     * @return The new url with max number of articles query param
     */
    public RequestBuilder addMaxArticles(int maxArticles) {
        String MAX = "&max=";
        if (maxArticles <= 0 || maxArticles > 10) {
            basePath = basePath + MAX + defaultMaxArticles;
            return this;
        }
        basePath = basePath + MAX + maxArticles;
        return this;
    }

    /**
     * Add the given keywords to the basePath
     * @param keywords The keywords to add to the basePath
     * @return The new url with added keywords
     */
    public RequestBuilder addKeywords(String keywords) {
        if (!StringUtils.isBlank(keywords)) {
           if (!keywords.startsWith("\"")) {
                keywords = "\"" + keywords;
            }

            if (!keywords.endsWith("\"")) {
                keywords = keywords + "\"";
            }
            basePath = basePath + "&q=" + keywords;
            return this;
        }
        return this;
    }

    /**
     * Add the given keywords to the basePath and the given in-keywords
     *
     * @param keywords The keywords to add to the basePath.
     * @param in The location in which the keywords should be used (title, description or content).
     * @return The new url with added keywords and in-keywords
     */
    public RequestBuilder addKeywords(String keywords, String in) {
        addKeywords(keywords);
        if (!StringUtils.isBlank(in)) {
            StringBuilder inKeywords = new StringBuilder();
            for (String token : in.split(",")) {
                if (!List.of("title", "description", "content").contains(token.toLowerCase())) {
                    throw new GnewsApiException("Invalid value for 'in'. Only  ('title', 'description' and 'content' are allowed)");
                }
                inKeywords.append(token.toLowerCase()).append(",");
            }
            basePath = basePath + "&in=" + inKeywords;

        }
        return this;
    }


    /**
     * Adds the apiKey to the basePath
     *
     * @param apiKey The api Key to add to the basePath
     * @return The bashPath with added api Key.
     */
    public RequestBuilder addApiKey(String apiKey) {
        String API_KEY = "&apikey=";
        basePath = basePath + API_KEY + apiKey;
        return this;
    }


}
