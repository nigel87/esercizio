package it.nigelpllaha.esercizio.service;

import it.nigelpllaha.esercizio.dto.MoneyTransferDTO;
import it.nigelpllaha.esercizio.dto.MoneyTransferRequest;

public interface PaymentsService {
    MoneyTransferDTO createMoneyTransfer(MoneyTransferRequest request);
}
