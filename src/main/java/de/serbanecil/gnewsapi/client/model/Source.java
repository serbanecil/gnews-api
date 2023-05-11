package de.serbanecil.gnewsapi.client.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class representing a source of a article
 */
@Schema(
        description = "The source of the article"
)
public class Source {

    @Schema(
            description = "The Hill",
            example = "Tears Of The Kingdom Run On Switch?")
    private String name;

    @Schema(
            description = "The home page of the source.",
            example = "https://thehill.com")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
