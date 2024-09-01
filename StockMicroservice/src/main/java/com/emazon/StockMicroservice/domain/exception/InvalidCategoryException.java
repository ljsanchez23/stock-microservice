package com.emazon.StockMicroservice.domain.exception;

/**
 * Exception thrown when an invalid category is encountered,
 * such as when categories exceed the allowed limits or contain duplicates.
 */
public class InvalidCategoryException extends RuntimeException{
    public InvalidCategoryException(String message){
        super(message);
    }
}
