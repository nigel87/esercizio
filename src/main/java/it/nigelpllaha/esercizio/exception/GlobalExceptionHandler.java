package it.nigelpllaha.esercizio.exception;

import it.nigelpllaha.esercizio.dto.ApiErrorResponse;
import it.nigelpllaha.esercizio.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static it.nigelpllaha.esercizio.constants.ErrorMessages.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FabrickApiException.class)
    public ResponseEntity<Response<String>> handleFabrickApiException(FabrickApiException e) {
        int httpStatusCode = e.getStatusCode().value();
        Response<String> response = new Response<>(false,e.getMessage(),FABRICK_API_ERROR);
        return ResponseEntity.status(httpStatusCode).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<List<String>>> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

         List<String> errors = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .toList();

        Response<List<String>> response = new Response<>(false,errors,VALIDATION_ERROR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidAccountingDatesException.class)
    public ResponseEntity<Response<List<String>>> handleInvalidAccountingDatesException(InvalidAccountingDatesException ex) {
        Response<List<String>> response = new Response<>(false,ex.getErrorMessages(), DATE_ERROR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }




}
