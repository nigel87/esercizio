package it.nigelpllaha.esercizio.dto.fabrick;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class FabrickResponse<T> {

    @JsonProperty("status")
    private String status;

    @JsonProperty("error")
    private List<String> error;

    @JsonProperty("payload")
    private T payload;


        public FabrickResponse(String status, T payload,  List<String> error) {
            this.status = status;
            this.payload = payload;
            this.error = error;
        }



}
