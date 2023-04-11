package it.nigelpllaha.esercizio.dto.fabrick.moneytransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.util.List;

@Data
public class Payload {
    @JsonProperty("moneyTransferId")
    private String moneyTransferId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("creditor")
    private CreditorDTO creditor;

    @JsonProperty("debtor")
    private DebtorDTO debtor;

    @JsonProperty("cro")
    private String cro;

    @JsonProperty("trn")
    private String trn;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("description")
    private String description;


    @JsonProperty("createdDatetime")
    private String createdDatetime;

    @JsonProperty("accountedDatetime")
    private String accountedDatetime;

    @JsonProperty("debtorValueDate")
    private String debtorValueDate;

    @JsonProperty("creditorValueDate")
    private String creditorValueDate;

    @JsonProperty("amount")
    private AmountDTO amount;

    @JsonProperty("isUrgent")
    private boolean isUrgent;

    @JsonProperty("isInstant")
    private boolean isInstant;

    @JsonProperty("feeType")
    private String feeType;

    @JsonProperty("feeAccountID")
    private String feeAccountID;

    @JsonProperty("fees")
    private List<FeeDTO> fees;

    @JsonProperty("hasTaxRelief")
    private boolean hasTaxRelief;

}