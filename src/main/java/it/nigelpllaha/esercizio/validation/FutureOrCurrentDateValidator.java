package it.nigelpllaha.esercizio.validation;
import it.nigelpllaha.esercizio.validation.constraints.FutureOrCurrentDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class FutureOrCurrentDateValidator implements ConstraintValidator<FutureOrCurrentDate, String> {

    @Override
    public void initialize(FutureOrCurrentDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(value, formatter);
            LocalDate currentDate = LocalDate.now();
            return date.isAfter(currentDate) || date.isEqual(currentDate);
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}