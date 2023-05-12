package de.serbanecil.gnewsapi.error;

import de.serbanecil.gnewsapi.exception.GnewsApiException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(value = {GnewsApiException.class})
    protected ResponseEntity<ErrorResponseObject> handleInvalidParamException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseObject(ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CallNotPermittedException.class})
    protected ResponseEntity<ErrorResponseObject> handleCallNotPermittedException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseObject(ex.getMessage(), 503), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
