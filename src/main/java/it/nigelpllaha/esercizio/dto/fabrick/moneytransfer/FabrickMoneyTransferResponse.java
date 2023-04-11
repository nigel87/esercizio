package it.nigelpllaha.esercizio.dto.fabrick.moneytransfer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class FabrickMoneyTransferResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("error")
    private List<String> error;

    @JsonProperty("payload")
    private Payload payload;
}