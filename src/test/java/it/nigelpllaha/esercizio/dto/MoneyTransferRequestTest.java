package it.nigelpllaha.esercizio.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

import static it.nigelpllaha.esercizio.constants.ErrorMessages.FIELD_REQUIRED;
import static org.junit.jupiter.api.Assertions.*;


class MoneyTransferRequestTest {
    private Validator validator;
    private String curretDate;
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        curretDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Test
    public void testValidation_Success() {
        // Request valida
        MoneyTransferRequest request = new MoneyTransferRequest();
        request.setAccountId(14537780L);
        request.setAccountCode("IT16W0300203280825777873732");
        request.setReceiverName("John Doe");
        request.setDescription("Payment invoice 75/2017");
        request.setCurrency("EUR");
        request.setAmount("800");
        request.setExecutionDate(curretDate);

        Set<ConstraintViolation<MoneyTransferRequest>> violations = validator.validate(request);
        // Verfica che non ci sono violazioni
        assertEquals(0, violations.size());
    }

    @Test
    public void testCurrencyValidation_MissingAccountCode_ThrowsException() {
        // Request valida
        MoneyTransferRequest request = new MoneyTransferRequest();
        request.setAccountId(14537780L);
        request.setReceiverName("John Doe");
        request.setDescription("Payment invoice 75/2017");
        request.setCurrency("EUR");
        request.setAmount("800");
        request.setExecutionDate(curretDate);

        Set<ConstraintViolation<MoneyTransferRequest>> violations = validator.validate(request);

        // Verifica violaizoni
        assertEquals(1, violations.size());
        assertEquals(FIELD_REQUIRED, violations.iterator().next().getMessage());

    }

    @Test
    public void testCurrencyValidation_EmptyAccountCode_ThrowsException() {
        // Request valida
        MoneyTransferRequest request = new MoneyTransferRequest();
        request.setAccountId(14537780L);
        request.setAccountCode("");
        request.setReceiverName("John Doe");
        request.setDescription("Payment invoice 75/2017");
        request.setCurrency("EUR");
        request.setAmount("800");
        request.setExecutionDate(curretDate);

        Set<ConstraintViolation<MoneyTransferRequest>> violations = validator.validate(request);

        // Verifica che sono presenti due violazioni
        assertEquals(2, violations.size());


        Optional<String> campoObligatorio = violations.stream()
                .map(ConstraintViolation::getMessage)
                .filter(message -> message.equals(FIELD_REQUIRED))
                .findAny();


        Optional<String> dimensioneMinima = violations.stream()
                .map(ConstraintViolation::getMessage)
                .filter(message -> message.equals("Dimensione minima è 8 carateri"))
                .findAny();

        // Verifica che sono presenti le violazioni attese
        assertTrue(campoObligatorio.isPresent());
        assertTrue(dimensioneMinima.isPresent());
    }



}