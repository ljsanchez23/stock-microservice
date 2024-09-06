package com.emazon.StockMicroservice.domain.exception;

public class InvalidCategoryException extends RuntimeException{
    public InvalidCategoryException(String message){
        super(message);
    }
}
