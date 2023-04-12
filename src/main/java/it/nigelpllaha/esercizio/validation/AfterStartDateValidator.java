package it.nigelpllaha.esercizio.validation;


import it.nigelpllaha.esercizio.dto.AccountTransactionsRequest;
import it.nigelpllaha.esercizio.validation.constraints.AfterStartDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;



public class AfterStartDateValidator implements ConstraintValidator<AfterStartDate, AccountTransactionsRequest> {

    @Override
    public void initialize(AfterStartDate constraintAnnotation) {
    }


    @Override
    public boolean isValid(AccountTransactionsRequest request, ConstraintValidatorContext context) {
        LocalDate fromAccountingDate = request.getFromAccountingDate();
        LocalDate toAccountingDate = request.getToAccountingDate();

        if (fromAccountingDate == null || toAccountingDate == null) {
            return true;
        }

        return !fromAccountingDate.isAfter(toAccountingDate);
    }
}