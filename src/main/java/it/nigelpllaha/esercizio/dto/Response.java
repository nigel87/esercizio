package it.nigelpllaha.esercizio.dto;

public class Response<T> {
    private boolean success;
    private T payload;
    private String errorMessage;

     public Response(boolean success, T payload, String errorMessage) {
        this.success = success;
        this.payload = payload;
        this.errorMessage = errorMessage;
    }

     public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
