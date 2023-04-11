package it.nigelpllaha.esercizio.validation.constraints;
import it.nigelpllaha.esercizio.validation.FutureOrCurrentDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = FutureOrCurrentDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureOrCurrentDate {
    String message() default "Value must be a future date in the format yyyy-MM-dd. Date cannot be in the past";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}