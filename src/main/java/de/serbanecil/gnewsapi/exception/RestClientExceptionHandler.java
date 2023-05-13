package de.serbanecil.gnewsapi.exception;

import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Class used to handle exceptions coming from the Rest Template calls
 */
@Component
public class RestClientExceptionHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw new GnewsApiException(IOUtils.toString(response.getBody()), response.getStatusCode().value());
    }
}
