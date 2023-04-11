package it.nigelpllaha.esercizio.dto.fabrick.accountransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public  class Payload {
    @JsonProperty("list")
    private List<TransactionDto> list;

    public List<TransactionDto> getList() {
        return list;
    }
}