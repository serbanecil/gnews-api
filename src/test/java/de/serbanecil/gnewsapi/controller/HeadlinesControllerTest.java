package de.serbanecil.gnewsapi.controller;

import de.serbanecil.gnewsapi.client.GNewsClient;
import de.serbanecil.gnewsapi.client.model.Response;
import de.serbanecil.gnewsapi.model.HeadlinesRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeadlinesControllerTest {

    @InjectMocks
    private HeadlinesController subject;

    @Mock
    private GNewsClient client;

    @Test
    public void testGetTop10Germany() {

        when(client.findHeadLines(anyString(), anyString(), anyString(), anyInt(), anyString())).thenReturn(new Response());

        ResponseEntity<Response> result = subject.getTop10Germany();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGetHeadlines() {
        when(client.findHeadLines(anyString(), anyString(), anyString(), anyInt(), anyString())).thenReturn(new Response());

        ResponseEntity<Response> result = subject.getHeadlines("", "", "", 10, "");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGetHeadlinesPost() {
        when(client.findHeadLines(anyString(), anyString(), anyString(), anyInt(), anyString())).thenReturn(new Response());

        ResponseEntity<Response> result = subject.getHeadlinesPost(new HeadlinesRequest("general", "de", "de", 2, ""));
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
