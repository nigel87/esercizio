package it.nigelpllaha.esercizio.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    private String transactionId;

    @Column
    private String operationId;

    @Column
    private String accountingDate;

    @Column
    private String valueDate;

    @Column
    private String transactionEnumeration;

    @Column
    private String transactionValue;


    @Column(scale = 15, precision = 30)
    private BigDecimal amount;

    @Column
    private String currency;

    @Column
    private String description;
}
