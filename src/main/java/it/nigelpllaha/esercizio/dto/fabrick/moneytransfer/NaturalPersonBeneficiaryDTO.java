package it.nigelpllaha.esercizio.dto.fabrick.moneytransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaturalPersonBeneficiaryDTO {
    @JsonProperty("fiscalCode1")
    private String fiscalCode1;

    @JsonProperty("fiscalCode2")
    private String fiscalCode2;

    @JsonProperty("fiscalCode3")
    private String fiscalCode3;

    @JsonProperty("fiscalCode4")
    private String fiscalCode4;

    @JsonProperty("fiscalCode5")
    private String fiscalCode5;
}
