package de.serbanecil.gnewsapi.integTest;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.serbanecil.gnewsapi.client.model.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.stream.IntStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@WireMockTest(httpPort = 9876)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"client.baseUrl.headlines=http://localhost:9876/api/v4/top-headlines?",
       "client.baseUrl.search=http://localhost:9876/api/v4/search?"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationIntegTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    @Order(1)
    public void testCacheHeadlines() {

        String localhost = "http://localhost:8080/news/headlines/getHeadlines";

        stubFor(
                get(urlPathMatching("/api/v4/top-headlines?"))
                        .willReturn(ok())
        );

        IntStream.rangeClosed(1, 5).forEach( i-> {
            ResponseEntity<Response> response = restTemplate.getForEntity(localhost, Response.class);
            assertEquals(200, response.getStatusCode().value());
        });

        // Only one call made to the mock server, the other responses came from the cache
        verify(1, getRequestedFor(urlPathMatching("/api/v4/top-headlines?")));
    }

    @Test
    @Order(2)
    public void testCacheHeadlinesNegative() {
        stubFor(
                get(urlPathMatching("/api/v4/top-headlines?"))
                        .willReturn(ok())
        );

        String localhost = "http://localhost:8080/news/headlines/getHeadlines";
        IntStream.rangeClosed(1, 5).forEach( i-> {
            ResponseEntity<Response> response = restTemplate.getForEntity(localhost + "?keywords="+ i, Response.class);
            assertEquals(200, response.getStatusCode().value());
        });

        // There is no cache because the urls differ
        verify(5, getRequestedFor(urlPathMatching("/api/v4/top-headlines?")));
    }

    @Test
    @Order(3)
    public void testCircuitBreaker() {

        final String localhost = "http://localhost:8080/news/search/getNews?keywords=test";

        stubFor(
                get(urlPathMatching("/api/v4/search?"))
                        .willReturn(serverError())
        );

        // First 5 requests fail with status 500
        IntStream.rangeClosed(1, 5).forEach(i-> {
            ResponseEntity<Response> response = restTemplate.getForEntity(localhost, Response.class);
            assertEquals(500, response.getStatusCode().value());
        });

        // Next 5 requests will fail with 503 -> CallNotPermittedException
        IntStream.rangeClosed(1, 5).forEach( i-> {
            ResponseEntity<Response> response = restTemplate.getForEntity(localhost, Response.class);
            assertEquals(503, response.getStatusCode().value());
        });

        // The GNEWS MockServer was called only 5 time, the next 5 calls were not executed because of the state OPEN
        verify(5, getRequestedFor(urlPathMatching("/api/v4/search?")));
    }



}
