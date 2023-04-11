package it.nigelpllaha.esercizio.dto.fabrick.moneytransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TaxReliefDTO {
    @JsonProperty("taxReliefId")
    private String taxReliefId;

    @JsonProperty("isCondoUpgrade")
    private boolean isCondoUpgrade;

    @JsonProperty("creditorFiscalCode")
    private String creditorFiscalCode;

    @JsonProperty("beneficiaryType")
    private String beneficiaryType;

    @JsonProperty("naturalPersonBeneficiary")
    private NaturalPersonBeneficiaryDTO naturalPersonBeneficiary;

    @JsonProperty("legalPersonBeneficiary")
    private LegalPersonBeneficiaryDTO legalPersonBeneficiary;
}
