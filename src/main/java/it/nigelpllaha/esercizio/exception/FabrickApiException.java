package it.nigelpllaha.esercizio.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public class FabrickApiException extends HttpClientErrorException {

    private List<String> errorMessages;
    public FabrickApiException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }


    public FabrickApiException(HttpStatusCode statusCode, List<String> errorMessages) {
        super(statusCode, errorMessages.toString());
        this.errorMessages = errorMessages;
    }



    public List<String> getErrorMessages() {
        return errorMessages;
    }
}