package it.nigelpllaha.esercizio.controller;

import it.nigelpllaha.esercizio.dto.AccountBalanceDTO;
import it.nigelpllaha.esercizio.dto.AccountTransactionsDTO;
import it.nigelpllaha.esercizio.dto.AccountTransactionsRequest;
import it.nigelpllaha.esercizio.dto.Response;
import it.nigelpllaha.esercizio.service.AccountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountsControllerTest {
    @InjectMocks
    private AccountsController accountsController;

    @Mock
    private AccountsService accountsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
        @Test
        public void testGetAccountTransactions_Success() {
            Long accountId = 1L;
            AccountTransactionsRequest request = new AccountTransactionsRequest();
            request.setFromAccountingDate(LocalDate.now().minusDays(1));
            request.setToAccountingDate(LocalDate.now());
            AccountTransactionsDTO responsePayload = new AccountTransactionsDTO();
            Response<AccountTransactionsDTO> expectedResponse = new Response<>(true, responsePayload, null);
            when(accountsService.getAccountTransactions(anyLong(), any(), any())).thenReturn(responsePayload);

            ResponseEntity<Response<AccountTransactionsDTO>> responseEntity = accountsController.getAccountTransactions(accountId, request);

            assertNotNull(responseEntity);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(expectedResponse.getPayload(), responseEntity.getBody().getPayload());
            assertEquals(expectedResponse.getErrorMessage(), responseEntity.getBody().getErrorMessage());
            assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());

            verify(accountsService, times(1)).getAccountTransactions(anyLong(), any(), any());
        }

    @Test
    public void testGetAccountBalance_Success() {
        Long accountId = 1L;
        AccountBalanceDTO responsePayload = new AccountBalanceDTO();
        Response<AccountBalanceDTO> expectedResponse = new Response<>(true, responsePayload, null);
        when(accountsService.getAccountBalance(anyLong())).thenReturn(responsePayload);

        ResponseEntity<Response<AccountBalanceDTO>> responseEntity = accountsController.getAccountBalance(accountId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getPayload(), responseEntity.getBody().getPayload());
        assertEquals(expectedResponse.getErrorMessage(), responseEntity.getBody().getErrorMessage());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());

        verify(accountsService, times(1)).getAccountBalance(anyLong());
    }

    }

