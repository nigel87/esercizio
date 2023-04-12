package it.nigelpllaha.esercizio.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;



public class FabrickApiException extends HttpClientErrorException {

     public FabrickApiException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }






}