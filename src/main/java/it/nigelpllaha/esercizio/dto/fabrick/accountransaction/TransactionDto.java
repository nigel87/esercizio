package it.nigelpllaha.esercizio.dto.fabrick.accountransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionDto {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("operationId")
    private String operationId;

    @JsonProperty("accountingDate")
    private String accountingDate;

    @JsonProperty("valueDate")
    private String valueDate;

    @JsonProperty("type")
    private TransactionTypeDto type;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("description")
    private String description;



}