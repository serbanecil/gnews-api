package de.serbanecil.gnewsapi.controller;

import de.serbanecil.gnewsapi.client.GNewsClient;
import de.serbanecil.gnewsapi.client.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private GNewsClient gNewsClient;

    @GetMapping("/getNews")
    public Response searchNews(
            @RequestParam(name="category", required = false) String category,
            @RequestParam(name="country", required = false) String country,
            @RequestParam(name="language", required = false) String language,
            @RequestParam(name="max", required = false, defaultValue = "10") int max,
            @RequestParam(name="keywords", required = false) String keywords) {
        return gNewsClient.findHeadLines(category, language, country, max, keywords);
    }
}
