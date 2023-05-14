package de.serbanecil.gnewsapi.controller;

import de.serbanecil.gnewsapi.client.GNewsClient;
import de.serbanecil.gnewsapi.client.model.Response;
import de.serbanecil.gnewsapi.exception.GnewsApiException;
import de.serbanecil.gnewsapi.model.NewsSearchRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchControllerTest {

    @InjectMocks
    private SearchController subject;

    @Mock
    private GNewsClient client;

    @Test
    public void testSearchNewsException() {

        Exception exception = assertThrows(GnewsApiException.class, () -> subject.searchNews("", "", "", 10, "", ""));

        String expectedMessage = "The keywords are mandatory!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSearchNews() {
        when(client.findNews(anyString(), anyString(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(new Response());
        ResponseEntity<Response> response = subject.searchNews("", "", "", 10, "keywords", "");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSearchNewsPost() {
        when(client.findNews(anyString(), anyString(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(new Response());
        ResponseEntity<Response> response = subject.searchNewsPost(new NewsSearchRequest("", "", "", 2, "test", ""));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
