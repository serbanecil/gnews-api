package de.serbanecil.gnewsapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Represent the request object when searching for headlines"
)
public class HeadlinesRequest {

    @Schema(
            description = "This parameter allows you to change " +
                    "the category for the request. The available categories are : general, world, nation, business, technology, " +
                    "entertainment, sports, science and health.",
            example = "world")
    private String category;

    @Schema(
            description = "This parameter allows you to specify " +
                    "the country where the news articles returned by the API were published, the contents of the " +
                    "articles are not necessarily related to the specified country. You have to set as value the 2 " +
                    "letters code of the country you want to filter.",
            example = "de")
    private String country;

    @Schema(
            description = "This parameter allows you to specify " +
                    "the language of the news articles returned by the API. You have to set as value the 2 letters " +
                    "code of the language you want to filter.",
            example = "de")
    private String language;

    @Schema(
            description = "This parameter allows you to specify the " +
                    "number of news articles returned by the API. The minimum value of this parameter is 1 and the " +
                    "maximum value is 100. The value you can set depends on your subscription.",
            example = "5")
    private int max;

    @Schema(
            description = "This parameter allows you to specify your " +
                    "search keywords which allows you to narrow down the results. The keywords will be used to return " +
                    "the most relevant articles. It is possible to use logical operators with keywords, see the section " +
                    "on query syntax",
            example = "news,article")
    private String keywords;

    public HeadlinesRequest(String category, String country, String language, int max, String keywords) {
        this.category = category;
        this.country = country;
        this.language = language;
        this.max = max;
        this.keywords = keywords;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }


}
