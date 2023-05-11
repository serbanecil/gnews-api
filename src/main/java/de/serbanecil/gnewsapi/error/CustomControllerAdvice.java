package de.serbanecil.gnewsapi.error;

import de.serbanecil.gnewsapi.exception.InvalidParamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(value = {InvalidParamException.class})
    protected ResponseEntity<ErrorResponseObject> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseObject(ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }

}
