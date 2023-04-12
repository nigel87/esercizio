package it.nigelpllaha.esercizio.validation.constraints;


import it.nigelpllaha.esercizio.validation.PositiveBigDecimalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import it.nigelpllaha.esercizio.constants.ErrorMessages;

@Documented
@Constraint(validatedBy = PositiveBigDecimalValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveBigDecimal {

    String message() default ErrorMessages.INVALID_AMOUNT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}