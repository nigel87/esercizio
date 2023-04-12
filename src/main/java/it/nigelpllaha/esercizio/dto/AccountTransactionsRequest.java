package it.nigelpllaha.esercizio.dto;

import it.nigelpllaha.esercizio.constants.ErrorMessages;
import it.nigelpllaha.esercizio.validation.constraints.AfterStartDate;
import it.nigelpllaha.esercizio.validation.constraints.NotFutureDate;
import lombok.Data;

import java.time.LocalDate;


@Data
@AfterStartDate (message = ErrorMessages.FROM_AFTER_TO_DATE)
public class AccountTransactionsRequest {
    @NotFutureDate (message = ErrorMessages.PAST_OR_CURRENT_DATE)
    LocalDate fromAccountingDate;
    @NotFutureDate (message = ErrorMessages.PAST_OR_CURRENT_DATE)
    LocalDate toAccountingDate;
}
