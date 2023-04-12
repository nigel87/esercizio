package it.nigelpllaha.esercizio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    private boolean success;
    private T payload;
    private String errorMessage;




}
