

package it.nigelpllaha.esercizio.service;

import it.nigelpllaha.esercizio.config.EsercizioConfigProperties;
import it.nigelpllaha.esercizio.constants.Mapping;
import it.nigelpllaha.esercizio.dto.AccountBalanceDTO;
import it.nigelpllaha.esercizio.dto.AccountTransactionsDTO;
import it.nigelpllaha.esercizio.dto.fabrick.FabrickResponse;
import it.nigelpllaha.esercizio.dto.fabrick.accountbalance.PayloadAccountBalance;
import it.nigelpllaha.esercizio.dto.fabrick.accountransaction.FabrickAccountTransactionResponse;
import it.nigelpllaha.esercizio.dto.fabrick.accountransaction.Payload;
import it.nigelpllaha.esercizio.dto.fabrick.accountransaction.TransactionDto;
import it.nigelpllaha.esercizio.exception.FabrickApiException;
import it.nigelpllaha.esercizio.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountsServiceImplTest {
    @InjectMocks
    private AccountsServiceImpl fabrickService;

    @Mock
    private RestTemplate restTemplate;
    @Mock
    TransactionRepository transactionRepository;

    private static EsercizioConfigProperties properties;

    private static final String DATE = "2022-04-08";
    private static final String CURRENCY = "EUR";

    private  static final Long  ACCOUNT_ID = 12345L ;


    private  static final Long  ACCOUNT_ID_BIG_AMOUNT_BALANCE = 10000L;
    private  static final String BALANCE_SMALL = "5.17";

    private  static final String BALANCE_BIG = "12345678901234567890.1234567890";



    private static  final  LocalDate FROM_ACCOUNTING_DATE = LocalDate.of(2019,1,1);
    private static  final LocalDate TO_ACCOUNTING_DATE = LocalDate.of(2019,12,1);

    private static  String URL_ACCOUNT_TRANSACTION;

    private static  String URL_ACCOUNT_BALANCE;

    private static  String URL_MONEY_TRANSFER;

    private static String NAME = "John Doe";

    @BeforeAll
    public static void init () {
        properties = new EsercizioConfigProperties("account","payment",
                "/api/esercizio/",
                "v1.0",
                "https://sandbox.platfr.io/api/gbs/banking/v4.0",
                "12345678900");

        URL_ACCOUNT_TRANSACTION = (properties.fabrickApiUrl() + Mapping.ACCOUNT_TRANSACTIONS_METHOD )
                .replace("{accountId}", ACCOUNT_ID.toString())
                .replace("{fromAccountingDate}", FROM_ACCOUNTING_DATE.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("{toAccountingDate}", TO_ACCOUNTING_DATE.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        URL_ACCOUNT_BALANCE  = (properties.fabrickApiUrl() + Mapping.ACCOUNT_BALANCE_METHOD )
                .replace("{accountId}", ACCOUNT_ID.toString());


    }


    @BeforeEach
    public void setUp() {
        fabrickService = new AccountsServiceImpl(restTemplate, properties,transactionRepository);
    }



    @Test
    public void testGetAccountBalance_Success() {

        PayloadAccountBalance payloadAccountBalance  = new PayloadAccountBalance();
        payloadAccountBalance.setDate(DATE);
        payloadAccountBalance.setBalance( BALANCE_SMALL);
        payloadAccountBalance.setAvailableBalance(BALANCE_SMALL);
        payloadAccountBalance.setCurrency(CURRENCY);

        FabrickResponse<PayloadAccountBalance> fabrickResponse;
        fabrickResponse = new FabrickResponse<>(HttpStatus.OK.toString(),payloadAccountBalance,null);

        when(restTemplate.getForObject(eq(URL_ACCOUNT_BALANCE), eq(FabrickResponse.class))).thenReturn(fabrickResponse);

        AccountBalanceDTO accountBalanceDTO = fabrickService.getAccountBalance(ACCOUNT_ID);

        assertNotNull(accountBalanceDTO);
        assertEquals(DATE, accountBalanceDTO.getDate());
        assertEquals(  new BigDecimal(BALANCE_SMALL), accountBalanceDTO.getBalance());
        assertEquals( new BigDecimal(BALANCE_SMALL), accountBalanceDTO.getAvailableBalance());
        assertEquals(CURRENCY, accountBalanceDTO.getCurrency());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(FabrickResponse.class));
    }

    @Test
    public void testGetAccountBalance_Success_WithBigNumber() {

        PayloadAccountBalance payloadAccountBalance  = new PayloadAccountBalance();
        payloadAccountBalance.setDate(DATE);
        payloadAccountBalance.setBalance( BALANCE_BIG);
        payloadAccountBalance.setAvailableBalance(BALANCE_BIG);
        payloadAccountBalance.setCurrency(CURRENCY);

        FabrickResponse<PayloadAccountBalance> fabrickResponse = new FabrickResponse<>(HttpStatus.OK.toString(),payloadAccountBalance,null); ;

        String urlAccountBalanceBigAmount = (properties.fabrickApiUrl() +Mapping.ACCOUNT_BALANCE_METHOD )
                .replace("{accountId}", ACCOUNT_ID_BIG_AMOUNT_BALANCE.toString());

        when(restTemplate.getForObject(eq(urlAccountBalanceBigAmount), eq(FabrickResponse.class))).thenReturn(fabrickResponse);

        AccountBalanceDTO accountBalanceDTO = fabrickService.getAccountBalance(ACCOUNT_ID_BIG_AMOUNT_BALANCE);

        assertNotNull(accountBalanceDTO);
        assertEquals(DATE, accountBalanceDTO.getDate());
        assertEquals(  new BigDecimal(BALANCE_BIG), accountBalanceDTO.getBalance());
        assertEquals( new BigDecimal(BALANCE_BIG), accountBalanceDTO.getAvailableBalance());
        assertEquals(CURRENCY, accountBalanceDTO.getCurrency());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(FabrickResponse.class));
    }

    @Test
    public void testGetAccountBalance_HttpClientErrorException() {
        when(restTemplate.getForObject(anyString(), eq(FabrickResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, "Forbidden"));


        assertThrows(FabrickApiException.class, () -> fabrickService.getAccountBalance(12345L));
    }

    @Test
    public void testGetAccountBalance_RestClientException() {
        when(restTemplate.getForObject(anyString(), eq(FabrickResponse.class)))
                .thenThrow(new RestClientException("RestClientException"));

        assertThrows(FabrickApiException.class, () -> fabrickService.getAccountBalance(12345L));
    }



    @Test
    public void testGetAccountTransactions() {
        FabrickAccountTransactionResponse fabrickResponse = new FabrickAccountTransactionResponse();
        Payload payload = new Payload();
        List<TransactionDto> list = new LinkedList<>();
        list.add(
                new TransactionDto(
                        "12345",
                        "100",
                        "2022-11-10",
                        "2022-11-08",
                        null,
                        BigDecimal.valueOf(400),
                        "EUR",
                        "desc"
                )
        );
        payload.setList(list);
        fabrickResponse.setPayload(payload);

        LocalDate fromAccountingDate = LocalDate.of(2019,1,1);
        LocalDate toAccountingDate = LocalDate.of(2019,12,1);

        when(restTemplate.getForObject(eq(URL_ACCOUNT_TRANSACTION), eq(FabrickAccountTransactionResponse.class))).thenReturn(fabrickResponse);

        AccountTransactionsDTO response = fabrickService.getAccountTransactions(ACCOUNT_ID,fromAccountingDate,toAccountingDate);

        assertNotNull(response);

    }


    @Test
    public void testGetAccountTransactions_FabrickResponseIsNull() {

        when(restTemplate.getForObject(eq(URL_ACCOUNT_TRANSACTION), eq(FabrickAccountTransactionResponse.class))).thenReturn(null);

         assertThrows(FabrickApiException.class, () -> fabrickService.getAccountTransactions(ACCOUNT_ID, FROM_ACCOUNTING_DATE, TO_ACCOUNTING_DATE));
    }

    @Test
    public void testGetAccountTransactions_HttpClientErrorException() {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // Choose any appropriate status code
        String statusText = "Bad Request";
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(httpStatus, statusText);
        when(restTemplate.getForObject(eq(URL_ACCOUNT_TRANSACTION), eq(FabrickAccountTransactionResponse.class)))
                .thenThrow(httpClientErrorException);

        FabrickApiException fabrickApiException
                = assertThrows(FabrickApiException.class, () -> fabrickService.getAccountTransactions(ACCOUNT_ID, FROM_ACCOUNTING_DATE, TO_ACCOUNTING_DATE));
        assertEquals(httpStatus, fabrickApiException.getStatusCode());
    }

    @Test
    public void testGetAccountTransactions_RestClientException() {

                RestClientException restClientException = new RestClientException("Rest Client Exception");
        when(restTemplate.getForObject(eq(URL_ACCOUNT_TRANSACTION), eq(FabrickAccountTransactionResponse.class)))
                .thenThrow(restClientException);

        FabrickApiException fabrickApiException = assertThrows(FabrickApiException.class, () -> fabrickService.getAccountTransactions(ACCOUNT_ID, FROM_ACCOUNTING_DATE, TO_ACCOUNTING_DATE));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, fabrickApiException.getStatusCode());
    }



}