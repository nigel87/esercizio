

package it.nigelpllaha.esercizio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nigelpllaha.esercizio.dto.MoneyTransferResponse;
import it.nigelpllaha.esercizio.dto.fabrick.accountbalance.FabrickAccountBalanceResponse;
import it.nigelpllaha.esercizio.dto.fabrick.accountransaction.FabrickAccountTransactionResponse;
import it.nigelpllaha.esercizio.dto.fabrick.accountransaction.TransactionDto;
import it.nigelpllaha.esercizio.dto.fabrick.error.FabrickErrorMessage;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.AccountDTO;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.CreditorDTO;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.FabrickMoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.FabrickMoneyTransferResponse;
import it.nigelpllaha.esercizio.repository.TransactionRepository;
import it.nigelpllaha.esercizio.dto.AccountTransactionsResponse;
import it.nigelpllaha.esercizio.dto.MoneyTransferRequest;
import it.nigelpllaha.esercizio.entity.Transaction;
import it.nigelpllaha.esercizio.exception.FabrickApiException;
import it.nigelpllaha.esercizio.config.EsercizioConfigProperties;
import it.nigelpllaha.esercizio.dto.AccountBalanceResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static it.nigelpllaha.esercizio.constants.ErrorMessages.*;

@Service
public class FabrickServiceImpl implements FabrickService {

    private final RestTemplate restTemplate;

    private final TransactionRepository transactionRepository;
    private final EsercizioConfigProperties properties;
    static ModelMapper modelMapper = new ModelMapper();
    public static final String OPERAZIONE_LETTURA_SALDO = "/accounts/{accountId}/balance";
    public static final String OPERAZIONE_LETTURA_TRANSAZIONI = "/accounts/{accountId}/transactions?fromAccountingDate={fromAccountingDate}&toAccountingDate={toAccountingDate}";
    public static final String OPERAZIONE_BONIFICO = "/accounts/{accountId}/payments/money-transfers";

    private static final String TIME_ZONE_HEADER = "X-Time-Zone";
    private static final String TIME_ZONE_VALUE = "Europe/Rome";

    public FabrickServiceImpl(RestTemplate restTemplate,
                              EsercizioConfigProperties properties,
                              TransactionRepository transactionRepository) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.transactionRepository = transactionRepository;

    }

    public AccountBalanceResponse getAccountBalance(Long accountId) {
        String url = properties.fabrickApiUrl() + OPERAZIONE_LETTURA_SALDO;
        url = url.replace("{accountId}", accountId.toString());
        AccountBalanceResponse response = new AccountBalanceResponse();
        try {
            FabrickAccountBalanceResponse fabrickResponse = restTemplate.getForObject(url, FabrickAccountBalanceResponse.class);
            if (fabrickResponse == null) {
                throw new FabrickApiException(HttpStatus.BAD_GATEWAY, NULL_FABRICK_RESPONSE);
            }
            response = modelMapper.map(fabrickResponse.getPayload(), AccountBalanceResponse.class);
        } catch (HttpClientErrorException e) {
            FabrickErrorMessage fabrickErrorMessage = parseFabrickException (e.getMessage(),e.getStatusCode());
            handleFabrickErrorMessage(e.getStatusCode(), fabrickErrorMessage);

        } catch (RestClientException e) {
            throw new FabrickApiException(HttpStatus.INTERNAL_SERVER_ERROR, REST_CLIENT_EXCEPTION);
        }
        return response;
    }

    public AccountTransactionsResponse getAccountTransactions(Long accountId,
                                                              LocalDate fromAccountingDate,
                                                              LocalDate toAccountingDate) {
        String url = properties.fabrickApiUrl() + OPERAZIONE_LETTURA_TRANSAZIONI;
        url = url.replace("{accountId}", accountId.toString())
                .replace("{fromAccountingDate}", fromAccountingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("{toAccountingDate}", toAccountingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        AccountTransactionsResponse response = new AccountTransactionsResponse();
        try {
            FabrickAccountTransactionResponse fabrickResponse =
                    restTemplate.getForObject(url, FabrickAccountTransactionResponse.class);

            if (fabrickResponse == null) {
                throw new FabrickApiException(HttpStatus.BAD_GATEWAY , NULL_FABRICK_RESPONSE);
            }
            saveResponseToDatabase(fabrickResponse);
            response = modelMapper.map(fabrickResponse.getPayload(), AccountTransactionsResponse.class);
        } catch (HttpClientErrorException e) {
            FabrickErrorMessage fabrickErrorMessage = parseFabrickException (e.getMessage(), e.getStatusCode());
            handleFabrickErrorMessage(e.getStatusCode(), fabrickErrorMessage);
        } catch (RestClientException e) {
            throw new FabrickApiException(HttpStatus.INTERNAL_SERVER_ERROR, REST_CLIENT_EXCEPTION);
        }
        return response;
    }

    public MoneyTransferResponse createMoneyTransfer(MoneyTransferRequest input) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(TIME_ZONE_HEADER, TIME_ZONE_VALUE);
        FabrickMoneyTransferRequest request = buildFabrickMoneyTransferRequest(input);
        final String url = (properties.fabrickApiUrl() + OPERAZIONE_BONIFICO)
                .replace("{accountId}", input.getAccountId().toString());

        MoneyTransferResponse response = new MoneyTransferResponse();
        HttpEntity<FabrickMoneyTransferRequest> httpEntity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<FabrickMoneyTransferResponse> fabrickResponse = restTemplate.postForEntity(url, httpEntity, FabrickMoneyTransferResponse.class);

            if (fabrickResponse.getBody() == null) {
                throw new FabrickApiException(HttpStatus.BAD_GATEWAY, NULL_FABRICK_RESPONSE);
            }
            FabrickMoneyTransferResponse body = fabrickResponse.getBody();
            System.out.println("response" + fabrickResponse);
            response = convertResponse(body.getPayload());
        } catch (HttpClientErrorException e) {
            FabrickErrorMessage fabrickErrorMessage = parseFabrickException (e.getMessage(),e.getStatusCode());
            handleFabrickErrorMessage(e.getStatusCode(), fabrickErrorMessage);
         } catch (RestClientException e) {
            throw new FabrickApiException(HttpStatus.INTERNAL_SERVER_ERROR, REST_CLIENT_EXCEPTION);
        }
        return response;
     }
    // Private methods

    private static void handleFabrickErrorMessage(HttpStatusCode httpStatusCode, FabrickErrorMessage fabrickErrorMessage) {
        if (fabrickErrorMessage !=null && fabrickErrorMessage.getErrors()!= null && !fabrickErrorMessage.getErrors().isEmpty()) {
            List<String> errorMessages = fabrickErrorMessage.getErrors().stream()
                    .map(error -> "codice errore:" + error.getCode()
                            + ", descrizione errore:" + error.getDescription())
                    .collect(Collectors.toList());
            throw new FabrickApiException(httpStatusCode, errorMessages);
        }
        throw new FabrickApiException(httpStatusCode, HTTP_ERROR + fabrickErrorMessage);

    }

    private FabrickErrorMessage parseFabrickException(String jsonMessage, HttpStatusCode httpStatusCode) {
        int startIndex = jsonMessage.indexOf('{');
        int endIndex = jsonMessage.lastIndexOf('}');
        if (startIndex < 0 || endIndex < 0) {
            throw new FabrickApiException(httpStatusCode, PARSING_ERROR + jsonMessage);
        }


        String jsonPayload = jsonMessage.substring(startIndex, endIndex + 1).replace("<EOL>", "");
        ObjectMapper jacksonMapper = new ObjectMapper();
        try {
            return jacksonMapper.readValue(jsonPayload, FabrickErrorMessage.class);
        } catch (JsonProcessingException e) {
            throw new FabrickApiException(httpStatusCode, PARSING_ERROR + jsonMessage);

        }

     }



    private static FabrickMoneyTransferRequest buildFabrickMoneyTransferRequest(MoneyTransferRequest input) {
        FabrickMoneyTransferRequest request = new FabrickMoneyTransferRequest();

        AccountDTO account = new AccountDTO();
        account.setAccountCode(input.getAccountCode());

        CreditorDTO creditor = new CreditorDTO();
        creditor.setName(input.getReceiverName());
        creditor.setAccount(account);

        request.setCreditor(creditor);
        request.setDescription(input.getDescription());
        request.setAmount(input.getAmount());
        request.setCurrency(input.getCurrency());
        request.setExecutionDate(input.getExecutionDate());
        request.setInstant(false);
        request.setUrgent(false);
        return request;
    }

    private void saveResponseToDatabase(FabrickAccountTransactionResponse fabrickResponse) {
        fabrickResponse.getPayload().getList().stream()
                .map(FabrickServiceImpl::transactionDtoToEntityConverter)
                .forEach(transactionRepository::save);
    }


    private MoneyTransferResponse convertResponse (it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.Payload fabrickPayload) {
        return modelMapper.map(fabrickPayload, MoneyTransferResponse.class);
    }
    private static Transaction transactionDtoToEntityConverter( TransactionDto dto) {
        Transaction transaction = modelMapper.map(dto, Transaction.class);
        if (dto.getType() != null) {
            transaction.setTransactionEnumeration(dto.getType().getEnumeration());
            transaction.setTransactionValue(dto.getType().getValue());
        }
        return transaction;
    }


}

