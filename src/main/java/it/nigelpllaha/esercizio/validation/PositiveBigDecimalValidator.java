package it.nigelpllaha.esercizio.validation;


import it.nigelpllaha.esercizio.validation.constraints.PositiveBigDecimal;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class PositiveBigDecimalValidator implements ConstraintValidator<PositiveBigDecimal, String> {

    @Override
    public void initialize(PositiveBigDecimal constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        try {
            BigDecimal bigDecimalValue = new BigDecimal(value);
            return bigDecimalValue.compareTo(BigDecimal.ZERO) > 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}