package de.serbanecil.gnewsapi.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomControllerAdviceTest {

    private final CustomControllerAdvice subject = new CustomControllerAdvice();

    @Test
    public void testHandleGNewsApiException() {
        ResponseEntity<ErrorResponseObject> result = subject.handleGNewsApiException(new GnewsApiException("test", 404));
        assertEquals("test", result.getBody().getMessage());
        assertEquals(404, result.getBody().getCode());
        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void testHndleCallNotPermittedException() {
        ResponseEntity<ErrorResponseObject> result = subject.handleCallNotPermittedException(new GnewsApiException("test", 404));
        assertEquals("test", result.getBody().getMessage());
        assertEquals(503, result.getBody().getCode());
        assertEquals(503, result.getStatusCode().value());
    }

}
