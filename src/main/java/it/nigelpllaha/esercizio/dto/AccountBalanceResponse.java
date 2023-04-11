package it.nigelpllaha.esercizio.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBalanceResponse {
    private String date; //TODO convert to Date
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private String currency;
 }
