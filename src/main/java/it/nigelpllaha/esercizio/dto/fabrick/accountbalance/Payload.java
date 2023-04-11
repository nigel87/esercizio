package it.nigelpllaha.esercizio.dto.fabrick.accountbalance;

import lombok.Data;

@Data
public  class Payload {
    private String date;
    private String balance;
    private String availableBalance;
    private String currency;
}