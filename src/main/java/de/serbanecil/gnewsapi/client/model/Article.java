package de.serbanecil.gnewsapi.client.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Class representing an article from GNews
 */
@Schema(
        description = "The article represents a news article retrieved from GNEWS"
)
public class Article {

    @Schema(
            description = "The main title of the article.",
            example = "Tears Of The Kingdom Run On Switch?")
    private String title;

    @Schema(
            description = "The small paragraph under the title.",
            example = "Digital Foundry’s analysis and other reviews applaud Nintendo’s technical achievement")
    private String description;

    @Schema(
            description = "All the content of the article. The content is truncated.",
            example = "here have been a lot of buggy blockbuster launches recently,..")
    private String content;

    @Schema(
            description = "The URL of the article.",
            example = "https://kotaku.com/zelda-tears-kingdom-switch-handheld-performance-bugs-1850428335")
    private String url;

    @Schema(
            description = "The main image of the article.",
            example = "https://thehill.com/wp-content/uploads/sites/2/2014/08/peanuts_082914_1_0.jpg?w=1280")
    private String image;

    @Schema(
            description = "The date of publication of the article. The date is always in the UTC time zone.",
            example = "2023-05-11T13:21:00")
    private LocalDateTime publishedAt;

    private Source source;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
