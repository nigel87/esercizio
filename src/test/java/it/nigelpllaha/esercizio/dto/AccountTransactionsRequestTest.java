package it.nigelpllaha.esercizio.dto;

import it.nigelpllaha.esercizio.constants.ErrorMessages;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

import static it.nigelpllaha.esercizio.constants.ErrorMessages.FIELD_REQUIRED;
import static org.junit.jupiter.api.Assertions.*;

class AccountTransactionsRequestTest {
    private Validator validator;

    private static LocalDate today;
    private static LocalDate yesterday;
    private static LocalDate tomorrow;
    private static LocalDate oneMonthAgo;
    private static LocalDate oneMonthFromNow;

    @BeforeAll
    public static void setupDates ()  {
        LocalDate  now = LocalDate.now();

        today = now;
        yesterday = now.minusDays(1);
        tomorrow = now.plusDays(1);
        oneMonthAgo = now.minusMonths(1);
        oneMonthFromNow = now.plusMonths(1);
    }
     @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
     }

    @Test
    public void testValidation_Success() {
        AccountTransactionsRequest  request = new AccountTransactionsRequest();
        request.setFromAccountingDate(oneMonthAgo);
        request.setToAccountingDate(yesterday);
        Set<ConstraintViolation<AccountTransactionsRequest>> violations = validator.validate(request);
        // Verfica che non ci sono violazioni
        assertEquals(0, violations.size());
    }

    @Test
    public void testValidationWithTodayDates_Success() {
        AccountTransactionsRequest  request = new AccountTransactionsRequest();
        request.setFromAccountingDate(today);
        request.setToAccountingDate(today);
        Set<ConstraintViolation<AccountTransactionsRequest>> violations = validator.validate(request);
        // Verfica che non ci sono violazioni
        assertEquals(0, violations.size());
    }

    @Test
    public void testValidationWithFutureToDate_Fails() {
        AccountTransactionsRequest  request = new AccountTransactionsRequest();
        request.setFromAccountingDate(yesterday);
        request.setToAccountingDate(tomorrow);
        Set<ConstraintViolation<AccountTransactionsRequest>> violations = validator.validate(request);
        // Verfica che ci sono violazioni
        assertEquals(1, violations.size());

        Optional<String> futureDate = violations.stream()
                .map(ConstraintViolation::getMessage)
                .filter(message -> message.equals(ErrorMessages.PAST_OR_CURRENT_DATE))
                .findAny();
        assertTrue(futureDate.isPresent());
    }

    @Test
    public void testValidationWithFutureDates_Fails() {
        AccountTransactionsRequest  request = new AccountTransactionsRequest();
        request.setFromAccountingDate(tomorrow);
        request.setToAccountingDate(oneMonthFromNow);
        Set<ConstraintViolation<AccountTransactionsRequest>> violations = validator.validate(request);
        // Verfica che ci sono violazioni
        assertEquals(2, violations.size());

        Optional<String> futureDate = violations.stream()
                .map(ConstraintViolation::getMessage)
                .filter(message -> message.equals(ErrorMessages.PAST_OR_CURRENT_DATE))
                .findAny();
        assertTrue(futureDate.isPresent());
    }

    @Test
    public void testValidationWithInvertedDates_Fails() {
        AccountTransactionsRequest  request = new AccountTransactionsRequest();
        request.setFromAccountingDate(yesterday);
        request.setToAccountingDate(oneMonthAgo);
        Set<ConstraintViolation<AccountTransactionsRequest>> violations = validator.validate(request);
        // Verfica che ci sono violazioni
        assertEquals(1, violations.size());

        Optional<String> invertedDates = violations.stream()
                .map(ConstraintViolation::getMessage)
                .filter(message -> message.equals(ErrorMessages.FROM_AFTER_TO_DATE))
                .findAny();
        assertTrue(invertedDates.isPresent());
    }

    @Test
    public void testValidationWithFutureAndIvertedDates_Fails() {
        AccountTransactionsRequest  request = new AccountTransactionsRequest();
        request.setFromAccountingDate(oneMonthFromNow);
        request.setToAccountingDate(tomorrow);
        Set<ConstraintViolation<AccountTransactionsRequest>> violations = validator.validate(request);
        // Verfica che ci sono violazioni
        assertEquals(3, violations.size());

        Optional<String> campoObligatorio = violations.stream()
                .map(ConstraintViolation::getMessage)
                .filter(message -> message.equals(ErrorMessages.PAST_OR_CURRENT_DATE))
                .findAny();
        assertTrue(campoObligatorio.isPresent());

        Optional<String> invertedDates = violations.stream()
                .map(ConstraintViolation::getMessage)
                .filter(message -> message.equals(ErrorMessages.FROM_AFTER_TO_DATE))
                .findAny();
        assertTrue(invertedDates.isPresent());
    }



}