package de.serbanecil.gnewsapi.exception;

/**
 * Class representing an exception that occurs if the requests are not correct.
 */
public class InvalidParamException extends RuntimeException {

    public InvalidParamException(String message) {
        super(message);
    }

}
