package it.nigelpllaha.esercizio.dto;


import it.nigelpllaha.esercizio.validation.constraints.FutureOrCurrentDate;
import it.nigelpllaha.esercizio.validation.constraints.PositiveBigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static it.nigelpllaha.esercizio.constants.ErrorMessages.*;


@Data
public class MoneyTransferRequest {
    @NotNull (message = FIELD_REQUIRED)
    private Long accountId ;

    @NotBlank (message = FIELD_REQUIRED)
    @Size(min = 8, message = MINIMUM_SIZE)
    private String accountCode; // Iban or Swift

    @NotBlank (message = FIELD_REQUIRED)
    @Size(max = 70,  message = MAXIMUM_SIZE)
    private String receiverName; //  beneficiario del bonifico;

    @NotBlank (message = FIELD_REQUIRED)
    @Size(max = 140, message = MAXIMUM_SIZE)
    private String description; //  descrizione del bonifico

    @NotBlank (message = FIELD_REQUIRED)
    @Size(
            min=3,
            max = 3,
            message = FIELD_LENGTH_THREE
    )
    private String currency;

    @NotBlank (message = FIELD_REQUIRED)
    @PositiveBigDecimal (message = INVALID_AMOUNT)
    private String amount;

    @NotBlank (message = FIELD_REQUIRED)
    @FutureOrCurrentDate(message =  FUTURE_OR_CURRENT_DATE)
    private String executionDate; // data in formato YYYY-MM-DD

}
