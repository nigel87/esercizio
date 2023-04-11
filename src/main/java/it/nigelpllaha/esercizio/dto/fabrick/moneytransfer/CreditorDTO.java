package it.nigelpllaha.esercizio.dto.fabrick.moneytransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreditorDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("account")
    private AccountDTO account;

    // @JsonProperty("address")
    // private AddressDTO address;

}
