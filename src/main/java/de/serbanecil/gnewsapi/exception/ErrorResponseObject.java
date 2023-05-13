package de.serbanecil.gnewsapi.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        description = "The Error Response Object represents the response returned in case there is an error with the request"
)
public class ErrorResponseObject {


    @Schema(
            description = "The date and time the error occurred",
            example = "2023-05-11T16:30:46")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    @Schema(
            description = "The message of the error",
            example = "The request has a bad format")
    private String message;

    @Schema(
            description = "The error code",
            example = "400")
    private int code;

    public ErrorResponseObject(String message, int code) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
