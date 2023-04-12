package it.nigelpllaha.esercizio.controller;

import it.nigelpllaha.esercizio.dto.AccountBalanceDTO;
import it.nigelpllaha.esercizio.dto.AccountTransactionsDTO;
import it.nigelpllaha.esercizio.dto.AccountTransactionsRequest;
import it.nigelpllaha.esercizio.dto.Response;
import it.nigelpllaha.esercizio.service.AccountsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.nigelpllaha.esercizio.constants.Mapping;

import static it.nigelpllaha.esercizio.constants.Mapping.ACCOUNTS_ENDPOINT;


@RestController
@RequestMapping(ACCOUNTS_ENDPOINT)
public class AccountsController {

    AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }


    @GetMapping(Mapping.LETTURA_TRANSAZIONI)
    public ResponseEntity<Response<AccountTransactionsDTO>> getAccountTransactions(
            @PathVariable Long accountId,
            @Valid @ModelAttribute AccountTransactionsRequest accountTransaction) {
        AccountTransactionsDTO payload = accountsService.getAccountTransactions(
                accountId, accountTransaction.getFromAccountingDate(),
                accountTransaction.getToAccountingDate());
        Response<AccountTransactionsDTO> response = new Response<>(true, payload, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(Mapping.SALDO)
    public ResponseEntity<Response<AccountBalanceDTO>> getAccountBalance(@PathVariable Long accountId) {
        AccountBalanceDTO payload = accountsService.getAccountBalance(accountId);
        Response<AccountBalanceDTO> response = new Response<>(true, payload, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
