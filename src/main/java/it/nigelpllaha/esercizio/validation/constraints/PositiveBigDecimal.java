package it.nigelpllaha.esercizio.validation.constraints;


import it.nigelpllaha.esercizio.validation.PositiveBigDecimalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PositiveBigDecimalValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveBigDecimal {

    String message() default "Invalid positive BigDecimal value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}