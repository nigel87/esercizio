package it.nigelpllaha.esercizio.constants;

public class Mapping {
    public static final String SALDO = "/{accountId}/balance";
    public static final String BONIFICO = "/money-transfers";
    public static final String LETTURA_TRANSAZIONI = "/{accountId}/transactions";

    public static final String ACCOUNTS_ENDPOINT =  "${esercizio.account-url}";
}
