package it.nigelpllaha.esercizio.controller;

import it.nigelpllaha.esercizio.dto.MoneyTransferDTO;
import it.nigelpllaha.esercizio.dto.MoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.Response;
import it.nigelpllaha.esercizio.exception.FabrickApiException;
import it.nigelpllaha.esercizio.service.PaymentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentsControllerTest {
    @InjectMocks
    private PaymentsController paymentsController;

    @Mock
    private PaymentsService paymentsService =  mock(PaymentsService.class);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransferMoney_Success() {
        MoneyTransferRequest request = new MoneyTransferRequest();
        MoneyTransferDTO responsePayload = new MoneyTransferDTO();
        Response<MoneyTransferDTO> expectedResponse = new Response<>(true, responsePayload, null);
        when(paymentsService.createMoneyTransfer(any(MoneyTransferRequest.class))).thenReturn(responsePayload);

        ResponseEntity<Response<MoneyTransferDTO>> responseEntity = paymentsController.transferMoney(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getPayload(), responseEntity.getBody().getPayload());
        assertEquals(expectedResponse.getErrorMessage(), responseEntity.getBody().getErrorMessage());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());

        verify(paymentsService, times(1)).createMoneyTransfer(any(MoneyTransferRequest.class));
    }



}