package it.nigelpllaha.esercizio.dto.fabrick.moneytransfer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FabrickMoneyTransferRequest {

    @JsonProperty("creditor")
    private CreditorDTO creditor;

    @JsonProperty("executionDate")
    private String executionDate;

   // @JsonProperty("uri")
    //private String uri;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("isUrgent")
    private boolean isUrgent;

    @JsonProperty("isInstant")
    private boolean isInstant;

  //  @JsonProperty("feeType")
    //private String feeType;

    // @JsonProperty("feeAccountId")
    // private String feeAccountId;

    // @JsonProperty("taxRelief")
    // private TaxReliefDTO taxRelief;
}
