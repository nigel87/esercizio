package it.nigelpllaha.esercizio.dto.fabrick.accountransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionTypeDto {

    @JsonProperty("enumeration")
    private String enumeration;

    @JsonProperty("value")
    private String value;
}