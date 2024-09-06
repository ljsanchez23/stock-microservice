package com.emazon.StockMicroservice.configuration.util;

import com.emazon.StockMicroservice.domain.exception.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/invalid-name")
    public void triggerInvalidNameException() throws InvalidNameException {
        throw new InvalidNameException("Invalid name exception triggered.");
    }

    @GetMapping("/invalid-description")
    public void triggerInvalidDescriptionException() throws InvalidDescriptionException {
        throw new InvalidDescriptionException("Invalid description exception triggered.");
    }

    @GetMapping("/invalid-category")
    public void triggerInvalidCategoryException() throws InvalidCategoryException {
        throw new InvalidCategoryException("Invalid category exception triggered.");
    }

    @GetMapping("/invalid-price")
    public void triggerInvalidPriceException() throws InvalidPriceException {
        throw new InvalidPriceException("Invalid price exception triggered.");
    }

    @GetMapping("/invalid-quantity")
    public void triggerInvalidQuantityException() throws InvalidQuantityException {
        throw new InvalidQuantityException("Invalid quantity exception triggered.");
    }

    @GetMapping("/general")
    public void triggerGeneralException() throws Exception {
        throw new Exception("General exception triggered.");
    }
}
