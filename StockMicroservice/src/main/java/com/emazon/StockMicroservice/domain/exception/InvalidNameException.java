package com.emazon.StockMicroservice.domain.exception;

public class InvalidNameException extends RuntimeException{
    public InvalidNameException(String message){
        super(message);
    }
}
