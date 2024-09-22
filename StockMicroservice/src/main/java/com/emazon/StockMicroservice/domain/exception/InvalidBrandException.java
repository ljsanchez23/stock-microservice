package com.emazon.StockMicroservice.domain.exception;

public class InvalidBrandException extends RuntimeException{
    public InvalidBrandException(String message){
        super(message);
    }
}
