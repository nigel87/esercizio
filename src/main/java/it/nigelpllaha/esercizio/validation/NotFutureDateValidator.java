package it.nigelpllaha.esercizio.validation;

import it.nigelpllaha.esercizio.validation.constraints.NotFutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class NotFutureDateValidator implements ConstraintValidator<NotFutureDate, LocalDate> {
    @Override
    public void initialize(NotFutureDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate dateToValidate, ConstraintValidatorContext context) {
        if (dateToValidate == null) {
            return true;
        }
        var currentDate = LocalDate.now();
         return !dateToValidate.isAfter(currentDate);
    }


}