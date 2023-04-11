package it.nigelpllaha.esercizio.validation.constraints;
import it.nigelpllaha.esercizio.validation.NotFutureDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotFutureDateValidator.class)
public @interface NotFutureDate {
    String message() default "Date must not be a future date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}