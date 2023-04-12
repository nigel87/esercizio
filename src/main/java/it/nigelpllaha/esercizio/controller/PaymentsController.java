package it.nigelpllaha.esercizio.controller;

import it.nigelpllaha.esercizio.dto.MoneyTransferDTO;
import it.nigelpllaha.esercizio.dto.MoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.Response;
import it.nigelpllaha.esercizio.service.PaymentsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.nigelpllaha.esercizio.constants.Mapping;

@RestController
@RequestMapping("${esercizio.payments-url}")
public class PaymentsController {
    PaymentsService paymentsService;

    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @PostMapping(Mapping.BONIFICO)
    public ResponseEntity<Response<MoneyTransferDTO>> transferMoney(@Valid @RequestBody MoneyTransferRequest request) {
        MoneyTransferDTO payload = paymentsService.createMoneyTransfer(request);
        Response<MoneyTransferDTO> response = new Response<>(true,payload,null);
        return ResponseEntity.ok(response);
    }

}
