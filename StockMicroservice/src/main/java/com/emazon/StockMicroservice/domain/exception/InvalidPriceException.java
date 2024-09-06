package com.emazon.StockMicroservice.domain.exception;

public class InvalidPriceException extends RuntimeException{
    public InvalidPriceException(String message){
        super(message);
    }
}
