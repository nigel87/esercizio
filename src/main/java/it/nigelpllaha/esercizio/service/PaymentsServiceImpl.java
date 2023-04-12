package it.nigelpllaha.esercizio.service;

import it.nigelpllaha.esercizio.config.EsercizioConfigProperties;
import it.nigelpllaha.esercizio.dto.MoneyTransferDTO;
import it.nigelpllaha.esercizio.dto.MoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.AccountDTO;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.CreditorDTO;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.FabrickMoneyTransferRequest;
import it.nigelpllaha.esercizio.dto.fabrick.moneytransfer.FabrickMoneyTransferResponse;
import it.nigelpllaha.esercizio.exception.FabrickApiException;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static it.nigelpllaha.esercizio.constants.ErrorMessages.NULL_FABRICK_RESPONSE;
import static it.nigelpllaha.esercizio.constants.ErrorMessages.REST_CLIENT_EXCEPTION;
@Service
public class PaymentsServiceImpl implements PaymentsService {
    private static final String OPERAZIONE_BONIFICO ="" ;
    RestTemplate restTemplate;
    EsercizioConfigProperties properties;
    public PaymentsServiceImpl(RestTemplate restTemplate,
                               EsercizioConfigProperties properties ) {
        this.restTemplate = restTemplate;
        this.properties = properties;

    }
    static ModelMapper modelMapper = new ModelMapper();

    private static final String TIME_ZONE_HEADER = "X-Time-Zone";
    private static final String TIME_ZONE_VALUE = "Europe/Rome";
    public MoneyTransferDTO createMoneyTransfer(MoneyTransferRequest input) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(TIME_ZONE_HEADER, TIME_ZONE_VALUE);
        FabrickMoneyTransferRequest request = buildFabrickMoneyTransferRequest(input);
        final String url = (properties.fabrickApiUrl() + OPERAZIONE_BONIFICO)
                .replace("{accountId}", input.getAccountId().toString());

        HttpEntity<FabrickMoneyTransferRequest> httpEntity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<FabrickMoneyTransferResponse> fabrickResponse = restTemplate.postForEntity(url, httpEntity, FabrickMoneyTransferResponse.class);

            if (fabrickResponse.getBody() == null) {
                throw new FabrickApiException(HttpStatus.BAD_GATEWAY, NULL_FABRICK_RESPONSE);
            }
            FabrickMoneyTransferResponse body = fabrickResponse.getBody();
            System.out.println("response" + fabrickResponse);
            return modelMapper.map(body.getPayload(), MoneyTransferDTO.class);

        } catch (HttpClientErrorException e) {
            throw new FabrickApiException(e.getStatusCode(), e.getMessage());

        } catch (RestClientException e) {
            throw new FabrickApiException(HttpStatus.INTERNAL_SERVER_ERROR, REST_CLIENT_EXCEPTION);
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

}
