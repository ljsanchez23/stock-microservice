package com.emazon.StockMicroservice.domain.exception;

/**
 * Exception thrown when an invalid description is provided,
 * such as a null, empty, or excessively long description.
 */
public class InvalidDescriptionException extends RuntimeException{
    public InvalidDescriptionException(String message){
        super(message);
    }
}
