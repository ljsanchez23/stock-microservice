package com.emazon.StockMicroservice.domain.exception;

public class InvalidDescriptionException extends RuntimeException{
    public InvalidDescriptionException(String message){
        super(message);
    }
}
