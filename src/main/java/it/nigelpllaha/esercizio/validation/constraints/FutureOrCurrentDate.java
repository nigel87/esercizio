package it.nigelpllaha.esercizio.validation.constraints;
import it.nigelpllaha.esercizio.validation.FutureOrCurrentDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import it.nigelpllaha.esercizio.constants.ErrorMessages;

@Documented
@Constraint(validatedBy = FutureOrCurrentDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureOrCurrentDate {
    String message() default ErrorMessages.FUTURE_OR_CURRENT_DATE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}