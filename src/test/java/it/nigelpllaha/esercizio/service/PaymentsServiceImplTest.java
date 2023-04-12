package it.nigelpllaha.esercizio.service;

import it.nigelpllaha.esercizio.config.EsercizioConfigProperties;
import it.nigelpllaha.esercizio.dto.MoneyTransferDTO;
import it.nigelpllaha.esercizio.dto.MoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.FabrickMoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.FabrickMoneyTransferResponse;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.Payload;
import it.nigelpllaha.esercizio.exception.FabrickApiException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class PaymentsServiceImplTest {
    @InjectMocks
    private PaymentsServiceImpl paymentsService;

    @Mock
    private RestTemplate restTemplate;

    private MoneyTransferRequest input;
    private static EsercizioConfigProperties properties;

    @BeforeAll
    public static void init() {
        properties = new EsercizioConfigProperties("account", "payment",
                "/api/esercizio/",
                "v1.0",
                "https://sandbox.platfr.io/api/gbs/banking/v4.0",
                "12345678900");
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        input = new MoneyTransferRequest();
        input.setAccountId(12345L);
        input.setAccountCode("IT45J0300203280915493666295");
        input.setReceiverName("John Doe");
        input.setDescription("Payment invoice 75/2017");
        input.setCurrency("EUR");
        input.setAmount("100");
        input.setExecutionDate("2023-05-05");
        paymentsService = new PaymentsServiceImpl(restTemplate, properties);

    }


    @Test
    public void testCreateMoneyTransfer_Success() {

        FabrickMoneyTransferResponse fabrickResponse = new FabrickMoneyTransferResponse();
        fabrickResponse.setPayload(new Payload());
        ResponseEntity<FabrickMoneyTransferResponse> responseEntity = new ResponseEntity<>(fabrickResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(FabrickMoneyTransferResponse.class))).thenReturn(responseEntity);
        MoneyTransferDTO expectedResponse = new MoneyTransferDTO();

        MoneyTransferDTO result = paymentsService.createMoneyTransfer(input);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(FabrickMoneyTransferResponse.class));
    }

    @Test
    public void testCreateMoneyTransfer_NullFabrickResponse() {
         ResponseEntity<FabrickMoneyTransferResponse> fabrickResponse = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(FabrickMoneyTransferResponse.class))).thenReturn(fabrickResponse);

        assertThrows(FabrickApiException.class, () -> paymentsService.createMoneyTransfer(input));
        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(FabrickMoneyTransferResponse.class));
    }

    @Test
    public void testCreateMoneyTransfer_HttpClientErrorException() {
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request");
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(FabrickMoneyTransferResponse.class))).thenThrow(httpClientErrorException);

        assertThrows(FabrickApiException.class, () -> paymentsService.createMoneyTransfer(input));

        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(FabrickMoneyTransferResponse.class));
    }
}