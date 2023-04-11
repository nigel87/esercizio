package it.nigelpllaha.esercizio.exception;

import java.util.List;

public class InvalidAccountingDatesException extends RuntimeException {
    private List<String> errorMessages;

    public InvalidAccountingDatesException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}