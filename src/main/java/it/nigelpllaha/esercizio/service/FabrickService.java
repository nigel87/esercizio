package it.nigelpllaha.esercizio.service;

import it.nigelpllaha.esercizio.dto.AccountBalanceResponse;
import it.nigelpllaha.esercizio.dto.AccountTransactionsResponse;
import it.nigelpllaha.esercizio.dto.MoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.MoneyTransferResponse;

import java.time.LocalDate;

public interface FabrickService {
    AccountBalanceResponse getAccountBalance(Long accountId);

    AccountTransactionsResponse getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate);

    MoneyTransferResponse createMoneyTransfer(MoneyTransferRequest request);
}

