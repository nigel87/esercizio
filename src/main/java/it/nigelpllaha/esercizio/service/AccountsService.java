package it.nigelpllaha.esercizio.service;

import it.nigelpllaha.esercizio.dto.AccountBalanceDTO;
import it.nigelpllaha.esercizio.dto.AccountTransactionsDTO;

import java.time.LocalDate;

public interface AccountsService {
    AccountBalanceDTO getAccountBalance(Long accountId);

    AccountTransactionsDTO getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate);

}

