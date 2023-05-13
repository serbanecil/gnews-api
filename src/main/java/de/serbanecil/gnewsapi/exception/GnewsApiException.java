package de.serbanecil.gnewsapi.exception;

/**
 * Class representing an exception that occurs if the requests can't be properly handled
 */
public class GnewsApiException extends RuntimeException {

    private final String message;

    private int code = 400;

    public GnewsApiException(String message) {
        super(message);
        this.message = message;
    }

    public GnewsApiException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
