package com.emazon.StockMicroservice.domain.exception;

/**
 * Exception thrown when an invalid price is provided,
 * such as a negative value.
 */
public class InvalidPriceException extends RuntimeException{
    public InvalidPriceException(String message){
        super(message);
    }
}
