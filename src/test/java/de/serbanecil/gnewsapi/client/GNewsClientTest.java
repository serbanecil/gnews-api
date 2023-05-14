package de.serbanecil.gnewsapi.client;

import de.serbanecil.gnewsapi.client.model.Article;
import de.serbanecil.gnewsapi.client.model.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
public class GNewsClientTest {

    @InjectMocks
    private GNewsClient subject;

    @Mock
    private RestTemplate restTemplate;


    @Test
    public void testFindHeadLinesSuccess(CapturedOutput capturedOutput) {
        ReflectionTestUtils.setField(subject, "BASE_URL_HEADLINES", "https://gnews.io/api/v4/top-headlines?");

        List<Article> articlesList = new ArrayList<>();
        Response response = new Response();
        response.setTotalArticles(1);
        response.setArticles(articlesList);
        Article article = new Article();
        article.setTitle("title");
        articlesList.add(article);

        when(restTemplate.getForEntity(anyString(), eq(Response.class))).thenReturn(ResponseEntity.ok(response));

        Response result = subject.findHeadLines("general", "de", "de", 10, "");

        assertEquals("title", result.getArticles().get(0).getTitle());
        assertTrue(capturedOutput.getOut()
                .contains("https://gnews.io/api/v4/top-headlines?category=general&language=de&country=de&max=10&apikey=null"));
    }

    @Test
    public void testFindNewsSuccess(CapturedOutput capturedOutput) {
        ReflectionTestUtils.setField(subject, "BASE_URL_SEARCH", "https://gnews.io/api/v4/search?");

        List<Article> articlesList = new ArrayList<>();
        Response response = new Response();
        response.setTotalArticles(1);
        response.setArticles(articlesList);
        Article article = new Article();
        article.setTitle("title");
        articlesList.add(article);

        when(restTemplate.getForEntity(anyString(), eq(Response.class))).thenReturn(ResponseEntity.ok(response));

        Response result = subject.findNews("general", "de", "de", 10, "test", "");

        assertEquals("title", result.getArticles().get(0).getTitle());
        assertTrue(capturedOutput.getOut()
                .contains("https://gnews.io/api/v4/search?q=\"test\"&category=general&language=de&country=de&max=10&apikey=null"));
    }
}
