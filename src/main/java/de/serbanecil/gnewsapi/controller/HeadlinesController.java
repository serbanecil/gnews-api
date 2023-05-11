package de.serbanecil.gnewsapi.controller;

import de.serbanecil.gnewsapi.client.GNewsClient;
import de.serbanecil.gnewsapi.client.model.Response;
import de.serbanecil.gnewsapi.error.ErrorResponseObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/headlines")
public class HeadlinesController {
    @Autowired
    private GNewsClient gNewsClient;

    @Operation(
            description = "Returns the top 10 headlines in the category 'GENERAL' with the language 'DE' and country 'DE'"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "The headlines are returned to the caller",
                            content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "400", description = "The request was incorrect",
                            content = @Content(schema = @Schema(implementation = ErrorResponseObject.class)))
            }
    )
    @GetMapping("/top10Germany")
    public Response getTop10Germany() {
        return gNewsClient.findHeadLines("general", "de", "de",
                10, "");
    }

    @Operation(
            description = "Returns the headlines with the given 'CATEGORY', from the given 'COUNTRY' " +
                    "with the given 'LANGUAGE' using the given 'KEYWORDS' limited to 'MAX' news"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "The headlines are returned to the caller",
                            content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "400", description = "The request was incorrect",
                    content = @Content(schema = @Schema(implementation = ErrorResponseObject.class)))
            }
    )
    @GetMapping("/getHeadlines")
    public Response getHeadlines(
            @RequestParam(name="category", required = false)
            @Parameter(name = "category", in = ParameterIn.QUERY, description = "This parameter allows you to change " +
                    "the category for the request. The available categories are : general, world, nation, business, technology, " +
                    "entertainment, sports, science and health.")
            String category,
            @RequestParam(name="country", required = false)
            @Parameter(name = "country", in = ParameterIn.QUERY, description = "This parameter allows you to specify " +
                    "the country where the news articles returned by the API were published, the contents of the " +
                    "articles are not necessarily related to the specified country. You have to set as value the 2 " +
                    "letters code of the country you want to filter.")
            String country,
            @RequestParam(name="language", required = false)
            @Parameter(name = "language", in = ParameterIn.QUERY, description = "This parameter allows you to specify " +
                    "the language of the news articles returned by the API. You have to set as value the 2 letters " +
                    "code of the language you want to filter.")
            String language,
            @RequestParam(name="max", required = false, defaultValue = "10")
            @Parameter(name = "max", in = ParameterIn.QUERY, description = "This parameter allows you to specify the " +
                    "number of news articles returned by the API. The minimum value of this parameter is 1 and the " +
                    "maximum value is 100. The value you can set depends on your subscription.")
            int max,
            @RequestParam(name="keywords", required = false)
            @Parameter(name = "keywords", in = ParameterIn.QUERY, description = "This parameter allows you to specify your " +
                    "search keywords which allows you to narrow down the results. The keywords will be used to return " +
                    "the most relevant articles. It is possible to use logical operators with keywords, see the section " +
                    "on query syntax")
            String keywords) {
        return gNewsClient.findHeadLines(category, language, country, max, keywords);
    }

}

