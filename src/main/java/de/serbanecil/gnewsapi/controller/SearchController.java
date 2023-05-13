package de.serbanecil.gnewsapi.controller;

import de.serbanecil.gnewsapi.client.GNewsClient;
import de.serbanecil.gnewsapi.client.model.Response;
import de.serbanecil.gnewsapi.exception.ErrorResponseObject;
import de.serbanecil.gnewsapi.exception.GnewsApiException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class represents the controller for the News.
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private GNewsClient gNewsClient;

    @Operation(
            description = "Returns the news articles with the given 'CATEGORY', from the given 'COUNTRY' " +
                    "with the given 'LANGUAGE' using the given 'KEYWORDS' in location limited to 'MAX' articles (maximum 10)"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "The news articles are returned to the caller",
                            content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request -- Your request is invalid.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseObject.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized -- Your API key is wrong.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseObject.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden -- You have reached your daily quota, " +
                            "the next reset is at 00:00 UTC.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseObject.class))),
                    @ApiResponse(responseCode = "429", description = "Too Many Requests -- You have made more requests " +
                            "per second than you are allowed.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseObject.class))),
                    @ApiResponse(responseCode = "500", description = "nternal Server Error -- We had a problem with our " +
                            "server. Try again later.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseObject.class))),
                    @ApiResponse(responseCode = "503", description = "Service Unavailable -- We're temporarily offline " +
                            "for maintenance. Please try again later.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseObject.class)))
            }
    )
    @GetMapping("/getNews")
    @CircuitBreaker(name = "Gnews")
    public Response searchNews(
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

            @RequestParam(name="keywords")
            @Parameter(name = "keywords", in = ParameterIn.QUERY, description = "This parameter allows you to specify your " +
                    "search keywords which allows you to narrow down the results. The keywords will be used to return " +
                    "the most relevant articles. It is possible to use logical operators with keywords, see the section " +
                    "on query syntax")
            String keywords,
            @RequestParam(name="in", required = false)
            @Parameter(name = "in", in = ParameterIn.QUERY, description = "This parameter allows you to choose in which " +
                    "attributes the keywords are searched. The attributes that can be set are title, " +
                    "description and content. It is possible to combine several attributes by separating them with " +
                    "a comma. e.g. title,description")
            String in) {
        if (StringUtils.isBlank(keywords)) {
            throw new GnewsApiException("The keywords are mandatory!");
        }
        return gNewsClient.findNews(category, language, country, max, keywords, in);
    }
}
