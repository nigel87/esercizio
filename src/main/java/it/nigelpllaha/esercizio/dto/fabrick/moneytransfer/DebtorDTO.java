package it.nigelpllaha.esercizio.dto.fabrick.moneytransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DebtorDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("account")
    private AccountDTO account;
}
