package it.nigelpllaha.esercizio.dto.fabrick.accountbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public  class PayloadAccountBalance {
    @JsonProperty("date")
    private String date;

    @JsonProperty("balance")
    private String balance;

    @JsonProperty("availableBalance")
    private String availableBalance;

    @JsonProperty("currency")
    private String currency;
}