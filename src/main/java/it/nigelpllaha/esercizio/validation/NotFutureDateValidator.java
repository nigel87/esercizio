package it.nigelpllaha.esercizio.validation;

import it.nigelpllaha.esercizio.validation.constraints.NotFutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NotFutureDateValidator implements ConstraintValidator<NotFutureDate, Date> {
    @Override
    public void initialize(NotFutureDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }
        var currentDate = LocalDate.now();
        LocalDate dateToValidate = convertToLocalDate(date);
        return !dateToValidate.isAfter(currentDate);
    }

    private  LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}