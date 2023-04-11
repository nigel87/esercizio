package it.nigelpllaha.esercizio.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountTransactionsResponse {


    private List<TransactionDto> list;


    @Data
    public static class TransactionDto {

        private String transactionId;

        private String operationId;

        private String accountingDate;

        private String valueDate;

        private TransactionTypeDto type;

        private BigDecimal amount;

        private String currency;

        private String description;


    }

    @Data
    public static class TransactionTypeDto {

        private String enumeration;
        private String value;
    }
}
