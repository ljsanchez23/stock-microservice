package com.emazon.StockMicroservice.domain.exception;

public class InvalidQuantityException extends RuntimeException{
    public InvalidQuantityException(String message){
        super(message);
    }
}
