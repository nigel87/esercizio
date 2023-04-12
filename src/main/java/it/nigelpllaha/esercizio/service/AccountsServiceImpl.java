

package it.nigelpllaha.esercizio.service;


import it.nigelpllaha.esercizio.constants.Mapping;
import it.nigelpllaha.esercizio.dto.fabrick.FabrickResponse;
import it.nigelpllaha.esercizio.dto.fabrick.accountbalance.PayloadAccountBalance;
import it.nigelpllaha.esercizio.dto.fabrick.accountransaction.FabrickAccountTransactionResponse;
import it.nigelpllaha.esercizio.dto.fabrick.accountransaction.TransactionDto;
import it.nigelpllaha.esercizio.repository.TransactionRepository;
import it.nigelpllaha.esercizio.dto.AccountTransactionsDTO;
import it.nigelpllaha.esercizio.entity.Transaction;
import it.nigelpllaha.esercizio.exception.FabrickApiException;
import it.nigelpllaha.esercizio.config.EsercizioConfigProperties;
import it.nigelpllaha.esercizio.dto.AccountBalanceDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static it.nigelpllaha.esercizio.constants.ErrorMessages.*;

@Service
public class AccountsServiceImpl implements AccountsService {

    private final RestTemplate restTemplate;

    private final TransactionRepository transactionRepository;
    private final EsercizioConfigProperties properties;
    static ModelMapper modelMapper = new ModelMapper();


    public AccountsServiceImpl(RestTemplate restTemplate,
                               EsercizioConfigProperties properties,
                               TransactionRepository transactionRepository) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.transactionRepository = transactionRepository;

    }

    public AccountBalanceDTO getAccountBalance(Long accountId) {
        String url = properties.fabrickApiUrl() + Mapping.ACCOUNT_BALANCE_METHOD;
        url = url.replace("{accountId}", accountId.toString());
         try {
             FabrickResponse<PayloadAccountBalance> fabrickResponse = restTemplate.getForObject(url,  FabrickResponse.class);
            if (fabrickResponse == null) {
                throw new FabrickApiException(HttpStatus.BAD_GATEWAY, NULL_FABRICK_RESPONSE);
            }
             return modelMapper.map(fabrickResponse.getPayload(), AccountBalanceDTO.class);
        } catch (HttpClientErrorException e) {
            throw new FabrickApiException(e.getStatusCode(), e.getMessage());
        } catch (RestClientException e) {
            throw new FabrickApiException(HttpStatus.INTERNAL_SERVER_ERROR, REST_CLIENT_EXCEPTION);
        }
     }

    public AccountTransactionsDTO getAccountTransactions(Long accountId,
                                                         LocalDate fromAccountingDate,
                                                         LocalDate toAccountingDate) {
        String url = properties.fabrickApiUrl() + Mapping.ACCOUNT_TRANSACTIONS_METHOD;
        url = url.replace("{accountId}", accountId.toString())
                .replace("{fromAccountingDate}", fromAccountingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("{toAccountingDate}", toAccountingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
         try {
            FabrickAccountTransactionResponse fabrickResponse =
                    restTemplate.getForObject(url, FabrickAccountTransactionResponse.class);
            if (fabrickResponse == null) {
                throw new FabrickApiException(HttpStatus.BAD_GATEWAY , NULL_FABRICK_RESPONSE);
            }
            saveResponseToDatabase(fabrickResponse);
            return  modelMapper.map(fabrickResponse.getPayload(), AccountTransactionsDTO.class);
        } catch (HttpClientErrorException e) {
            throw new FabrickApiException(e.getStatusCode(), e.getMessage());
        } catch (RestClientException e) {
            throw new FabrickApiException(HttpStatus.INTERNAL_SERVER_ERROR, REST_CLIENT_EXCEPTION);
        }
    }

    // Private methods

    private void saveResponseToDatabase(FabrickAccountTransactionResponse fabrickResponse) {
        fabrickResponse.getPayload().getList().stream()
                .map(AccountsServiceImpl::transactionDtoToEntityConverter)
                .forEach(transactionRepository::save);
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

