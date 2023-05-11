package de.serbanecil.gnewsapi.client.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Class representing a Reponse from GNews.
 */
@Schema(
        description = "The reponse containtn the articles retrieved from GNEWS"
)
public class Response {

    @Schema(
            description = "The total number of articles",
            example = "68211")
    private long totalArticles;

    List<Article> articles;


    public long getTotalArticles() {
        return totalArticles;
    }

    public void setTotalArticles(long totalArticles) {
        this.totalArticles = totalArticles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
