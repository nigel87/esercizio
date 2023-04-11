package it.nigelpllaha.esercizio.controller;

import it.nigelpllaha.esercizio.dto.AccountTransactionsResponse;
import it.nigelpllaha.esercizio.exception.InvalidAccountingDatesException;
import it.nigelpllaha.esercizio.service.FabrickService;
import org.junit.jupiter.api.BeforeAll;
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

public class FabrickApiControllerTest {

    @Mock
    private FabrickService fabrickService;

    @InjectMocks
    private FabrickApiController fabrickApiController;


    private static final Long accountId = 1L;

    private static LocalDate today;
    private static LocalDate yesterday;
    private static LocalDate tomorrow;
    private static LocalDate oneMonthAgo;
    private static LocalDate oneMonthFromNow;

    @BeforeAll
    public static void setupDates ()  {
        LocalDate  now = LocalDate.now();

        today = now;
        yesterday = now.minusDays(1);
        tomorrow = now.plusDays(1);
        oneMonthAgo = now.minusMonths(1);
        oneMonthFromNow = now.plusMonths(1);
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
     }

    @Test
    public void testGetAccountTransactions_ValidDates_ReturnsResponseEntityWithHttpStatusOK() {
         AccountTransactionsResponse expectedResponse = new AccountTransactionsResponse();
        when(fabrickService.getAccountTransactions(accountId, oneMonthAgo, yesterday))
                .thenReturn(expectedResponse);

        ResponseEntity<AccountTransactionsResponse> response = fabrickApiController
                .getAccountTransactions(accountId, oneMonthAgo, yesterday);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(fabrickService, times(1))
                .getAccountTransactions(accountId, oneMonthAgo, yesterday);
    }

    @Test
    public void testGetAccountTransactions_TodayDate_ReturnsResponseEntityWithHttpStatusOK() {
        AccountTransactionsResponse expectedResponse = new AccountTransactionsResponse();
        when(fabrickService.getAccountTransactions(accountId, today, today))
                .thenReturn(expectedResponse);

        ResponseEntity<AccountTransactionsResponse> response = fabrickApiController
                .getAccountTransactions(accountId, today, today);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(fabrickService, times(1))
                .getAccountTransactions(accountId, today, today);
    }

    @Test
    public void testGetAccountTransactions_InvertedDates_ThrowsInvalidAccountingDatesException() {

        assertThrows(InvalidAccountingDatesException.class, () -> fabrickApiController
                .getAccountTransactions(accountId, yesterday, oneMonthAgo));
        verify(fabrickService, never())
                .getAccountTransactions(anyLong(), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void testGetAccountTransactions_FutureFromAccountingDate_ThrowsInvalidAccountingDatesException() {


        assertThrows(InvalidAccountingDatesException.class, () -> fabrickApiController
                .getAccountTransactions(accountId, yesterday, tomorrow));
        verify(fabrickService, never())
                .getAccountTransactions(anyLong(), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void testGetAccountTransactions_FutureFromAccountingDateAndFutureToAccountingDate_ThrowsInvalidAccountingDatesException() {

        assertThrows(InvalidAccountingDatesException.class, () -> fabrickApiController
                .getAccountTransactions(accountId, tomorrow, oneMonthFromNow));
        verify(fabrickService, never())
                .getAccountTransactions(anyLong(), any(LocalDate.class), any(LocalDate.class));
    }


}