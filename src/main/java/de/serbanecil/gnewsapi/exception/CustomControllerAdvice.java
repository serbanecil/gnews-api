package de.serbanecil.gnewsapi.exception;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Controller advice to handle the exceptions
 */
@ControllerAdvice
public class CustomControllerAdvice {

    final Logger LOGGER = LoggerFactory.getLogger(CustomControllerAdvice.class);

    /**
     * Handles the GNews API Exceptions. This exception occurs when the caller makes a bad request or
     * if the RestTemplate throws a RestClientException
     *
     * @param ex The exception thrown
     * @return A ErrorResponseObject containing the timestamp, the exception message and a status code
     */
    @ExceptionHandler(value = {GnewsApiException.class})
    protected ResponseEntity<ErrorResponseObject> handleGNewsApiException(GnewsApiException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseObject(ex.getMessage(), ex.getCode()), HttpStatus.resolve(ex.getCode()));
    }

    /**
     * Handles the case, that the circuit breaker is open. If the circuit breaker is open, a CallNotPermittedException
     * is being thrown.
     *
     * @param ex The CallNotPermittedException
     * @return A ErrorResponseObject containing the timestamp, the exception message and a status code
     */
    @ExceptionHandler(value = {CallNotPermittedException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected ResponseEntity<ErrorResponseObject> handleCallNotPermittedException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseObject(ex.getMessage(), 503), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseObject> handleMissingRequestParameter(MissingServletRequestParameterException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseObject(ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }

}
