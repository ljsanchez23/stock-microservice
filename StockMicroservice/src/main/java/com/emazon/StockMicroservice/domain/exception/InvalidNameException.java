package com.emazon.StockMicroservice.domain.exception;

/**
 * Exception thrown when an invalid name is provided,
 * such as a null, empty, or excessively long name.
 */
public class InvalidNameException extends RuntimeException{
    public InvalidNameException(String message){
        super(message);
    }
}
