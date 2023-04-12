package it.nigelpllaha.esercizio.dto;

import it.nigelpllaha.esercizio.constants.MoneyTransferDirection;
import it.nigelpllaha.esercizio.constants.MoneyTransferStatus;
import lombok.Data;


@Data
public class MoneyTransferDTO {
    private String moneyTransferId;
    private MoneyTransferStatus status;
    private MoneyTransferDirection direction;
    private String creditorName ;
    private String creditorAccountCode ;
    private String cro;
    private String uri;
    private String created;
    private String debtorDate;
    private String creditorDate; // TODO convert to LocalDate
    private String feeType;

}
