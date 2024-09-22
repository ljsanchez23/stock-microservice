package com.emazon.StockMicroservice.configuration;

import com.emazon.StockMicroservice.configuration.util.ConfigConstants;
import com.emazon.StockMicroservice.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.emazon.StockMicroservice.configuration.util.ErrorResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.emazon.StockMicroservice.domain.util.Constants;

import java.security.SignatureException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<ErrorResponse> handleInvalidNameException(InvalidNameException ex) {
        HttpServletRequest request = getCurrentHttpRequest();
        String requestUri = (request != null) ? request.getRequestURI() : ConfigConstants.DEFAULT_PATH;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ConfigConstants.BAD_REQUEST,
                ex.getMessage(),
                requestUri
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDescriptionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDescriptionException(InvalidDescriptionException ex) {
        HttpServletRequest request = getCurrentHttpRequest();
        String requestUri = (request != null) ? request.getRequestURI() : ConfigConstants.DEFAULT_PATH;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ConfigConstants.BAD_REQUEST,
                ex.getMessage(),
                requestUri
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCategoryException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCategoryException(InvalidCategoryException ex) {
        HttpServletRequest request = getCurrentHttpRequest();
        String requestUri = (request != null) ? request.getRequestURI() : ConfigConstants.DEFAULT_PATH;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ConfigConstants.BAD_REQUEST,
                ex.getMessage(),
                requestUri
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPriceException(InvalidPriceException ex) {
        HttpServletRequest request = getCurrentHttpRequest();
        String requestUri = (request != null) ? request.getRequestURI() : ConfigConstants.DEFAULT_PATH;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ConfigConstants.BAD_REQUEST,
                ex.getMessage(),
                requestUri
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidQuantityException(InvalidQuantityException ex) {
        HttpServletRequest request = getCurrentHttpRequest();
        String requestUri = (request != null) ? request.getRequestURI() : ConfigConstants.DEFAULT_PATH;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ConfigConstants.BAD_REQUEST,
                ex.getMessage(),
                requestUri
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return (attributes != null) ? attributes.getRequest() : null;
    }

}
