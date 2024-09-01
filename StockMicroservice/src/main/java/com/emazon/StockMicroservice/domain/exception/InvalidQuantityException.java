package com.emazon.StockMicroservice.domain.exception;

/**
 * Exception thrown when an invalid quantity is provided,
 * such as a negative value or zero when not allowed.
 */
public class InvalidQuantityException extends RuntimeException{
    public InvalidQuantityException(String message){
        super(message);
    }
}
