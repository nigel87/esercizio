package it.nigelpllaha.esercizio.service;

import it.nigelpllaha.esercizio.dto.AccountBalanceDTO;
import it.nigelpllaha.esercizio.dto.AccountTransactionsDTO;
import it.nigelpllaha.esercizio.dto.MoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.MoneyTransferDTO;

import java.time.LocalDate;

public interface FabrickService {
    AccountBalanceDTO getAccountBalance(Long accountId);

    AccountTransactionsDTO getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate);

    MoneyTransferDTO createMoneyTransfer(MoneyTransferRequest request);
}

