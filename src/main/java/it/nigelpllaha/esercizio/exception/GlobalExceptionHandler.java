package it.nigelpllaha.esercizio.exception;

import it.nigelpllaha.esercizio.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FabrickApiException.class)
    public ResponseEntity<ApiErrorResponse> handleFabrickApiException(FabrickApiException e) {
        int httpStatusCode = e.getStatusCode().value();

        ApiErrorResponse errorResponse = new ApiErrorResponse();
         errorResponse.setMessage(e.getMessage());
        if(e.getErrorMessages()!=null && !e.getErrorMessages().isEmpty()) {
            errorResponse.setErrors(e.getErrorMessages());
        }
        return ResponseEntity.status(httpStatusCode).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setMessage("Validation failed");
        List<String> errors = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .toList();
        errorResponse.setErrors(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidAccountingDatesException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidAccountingDatesException(InvalidAccountingDatesException ex) {

        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setMessage("Validation failed for AccountingDates");
        errorResponse.setErrors(ex.getErrorMessages());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }




}
