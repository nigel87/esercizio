package it.nigelpllaha.esercizio.dto.fabrick.error;

import com.fasterxml.jackson.annotation.JsonProperty;
 import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class FabrickErrorMessage {
    @JsonProperty("status")
    private String status;

    @JsonProperty("errors")
    private List<Error> errors;

    @JsonProperty("payload")
    private Map<String, Object> payload;

    @Data
    public static class Error {
        @JsonProperty("code")
        private String code;

        @JsonProperty("description")
        private String description;

        @JsonProperty("params")
        private String params;
    }
}