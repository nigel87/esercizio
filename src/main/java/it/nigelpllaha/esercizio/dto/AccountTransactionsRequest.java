package it.nigelpllaha.esercizio.dto;

import it.nigelpllaha.esercizio.constants.ErrorMessages;
import it.nigelpllaha.esercizio.validation.constraints.AfterStartDate;
import it.nigelpllaha.esercizio.validation.constraints.NotFutureDate;
import lombok.Data;

import java.time.LocalDate;


@Data
@AfterStartDate
public class AccountTransactionsRequest {
    @NotFutureDate
    LocalDate fromAccountingDate;
    @NotFutureDate
    LocalDate toAccountingDate;
}
