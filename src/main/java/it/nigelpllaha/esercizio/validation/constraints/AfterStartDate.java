package it.nigelpllaha.esercizio.validation.constraints;




import it.nigelpllaha.esercizio.constants.ErrorMessages;
import it.nigelpllaha.esercizio.validation.AfterStartDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = AfterStartDateValidator.class)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
 public @interface AfterStartDate {
    String message() default ErrorMessages.FROM_AFTER_TO_DATE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}