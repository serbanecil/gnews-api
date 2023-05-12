package de.serbanecil.gnewsapi.exception;

/**
 * Class representing an exception that occurs if the requests can't be properly handled
 */
public class GnewsApiException extends RuntimeException {

    public GnewsApiException(String message) {
        super(message);
    }

}
