package it.nigelpllaha.esercizio.dto.fabrick.moneytransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FeeDTO {
    @JsonProperty("feeCode")
    private String feeCode;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private Number amount;

    @JsonProperty("currency")
    private String currency;

}
