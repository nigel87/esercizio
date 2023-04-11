package it.nigelpllaha.esercizio.dto.fabrick.moneytransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountDTO {
    @JsonProperty("accountCode")
    private String accountCode;

    //  @JsonProperty("bicCode")
    //  private String bicCode;
}
