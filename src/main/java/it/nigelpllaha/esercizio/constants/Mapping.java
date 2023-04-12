package it.nigelpllaha.esercizio.constants;



public class Mapping {
    public static final String SALDO = "/{accountId}/balance";
    public static final String BONIFICO = "/money-transfers";
    public static final String LETTURA_TRANSAZIONI = "/{accountId}/transactions";

    public static final String ACCOUNTS_ENDPOINT = "${esercizio.account-url}";

    public static final String PAYMENTS_ENDPOINT = "${esercizio.payments-url}";

    public static final String ACCOUNT_BALANCE_METHOD = "/accounts/{accountId}/balance";
    public static final String ACCOUNT_TRANSACTIONS_METHOD = "/accounts/{accountId}/transactions?fromAccountingDate={fromAccountingDate}&toAccountingDate={toAccountingDate}";
    public static final String MONEY_TRANSFER_METHOD = "/accounts/{accountId}/payments/money-transfers";






}
