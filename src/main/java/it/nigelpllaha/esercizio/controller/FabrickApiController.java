package it.nigelpllaha.esercizio.controller;

import it.nigelpllaha.esercizio.dto.AccountTransactionsResponse;
import it.nigelpllaha.esercizio.dto.MoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.MoneyTransferResponse;
import it.nigelpllaha.esercizio.exception.InvalidAccountingDatesException;
import it.nigelpllaha.esercizio.service.FabrickService;
import it.nigelpllaha.esercizio.dto.AccountBalanceResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping("${esercizio.api-url}")
public class FabrickApiController {

    private final FabrickService fabrickService;

    private static final String MAPPING_SALDO = "/accounts/{accountId}/balance";
    private static final String MAPPING_BONIFICO = "/accounts/transfer";
    private static final String MAPPING_LETTURA_TRANSAZIONI = "/accounts/{accountId}/transactions";

    public FabrickApiController(FabrickService fabrickService) {
        this.fabrickService = fabrickService;
    }

    @GetMapping(MAPPING_SALDO)
    public ResponseEntity<AccountBalanceResponse> getAccountBalance(@PathVariable Long accountId) {
        AccountBalanceResponse response = fabrickService.getAccountBalance(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(MAPPING_LETTURA_TRANSAZIONI)
    public ResponseEntity<AccountTransactionsResponse> getAccountTransactions(
            @PathVariable Long accountId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromAccountingDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toAccountingDate

    ) {
        List<String> errorMessages = new LinkedList<>();

        if (fromAccountingDate.isAfter(toAccountingDate)) {
            errorMessages.add("fromAccountingDate cannot be after toAccountingDate");
        }

        LocalDate currentDate = LocalDate.now();
        if (fromAccountingDate.isAfter(currentDate)) {
            errorMessages.add("fromAccountingDate cannot be a future date");
        }

        if (toAccountingDate.isAfter(currentDate)) {
            errorMessages.add("toAccountingDate cannot be a future date");
        }

        if (!errorMessages.isEmpty()) {
            throw new InvalidAccountingDatesException(errorMessages);
        }

        AccountTransactionsResponse response = fabrickService.getAccountTransactions(accountId, fromAccountingDate,toAccountingDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(MAPPING_BONIFICO)
    public ResponseEntity<MoneyTransferResponse> transferMoney(@Valid @RequestBody MoneyTransferRequest request) {
        MoneyTransferResponse response = fabrickService.createMoneyTransfer(request);
        return ResponseEntity.ok(response);
    }



}
