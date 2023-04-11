package it.nigelpllaha.esercizio.dto.fabrick.accountbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.util.List;

@Data
public class FabrickAccountBalanceResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("error")
    private List<String> error;

    @JsonProperty("payload")
    private Payload payload;


 }

