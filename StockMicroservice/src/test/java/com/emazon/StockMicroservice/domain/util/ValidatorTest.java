package com.emazon.StockMicroservice.domain.util;

import com.emazon.StockMicroservice.domain.exception.*;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.model.Category;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {

    @Test
    void testValidateQuantityValid() {
        assertDoesNotThrow(() -> Validator.validateQuantity(10));
    }

    @Test
    void testValidateQuantityInvalid() {
        assertThrows(InvalidQuantityException.class, () -> Validator.validateQuantity(-1));
        assertThrows(InvalidQuantityException.class, () -> Validator.validateQuantity(Constants.MIN_QUANTITY));
    }

    @Test
    void testValidatePriceValid() {
        assertDoesNotThrow(() -> Validator.validatePrice(10.0));
    }

    @Test
    void testValidatePriceInvalid() {
        assertThrows(InvalidPriceException.class, () -> Validator.validatePrice(-1.0));
        assertThrows(InvalidPriceException.class, () -> Validator.validatePrice(Constants.MIN_PRICE));
    }

    @Test
    void testValidateCategoriesValid() {
        List<Category> categories = Arrays.asList(new Category(1L, "Electronics", "Electronics description"), new Category(2L, "Books", "Books description"));
        assertDoesNotThrow(() -> Validator.validateCategories(categories));
    }

    @Test
    void testValidateCategoriesInvalid() {
        assertThrows(InvalidCategoryException.class, () -> Validator.validateCategories(null));
        assertThrows(InvalidCategoryException.class, () -> Validator.validateCategories(Collections.emptyList()));

        List<Category> tooManyCategories = Arrays.asList(new Category(1L, "Electronics", "Electronics description"), new Category(2L, "Books", "Books description"),
                new Category(3L,"Toys", "Toys description"), new Category(4L, "Clothing", "Clothing description"));
        assertThrows(InvalidCategoryException.class, () -> Validator.validateCategories(tooManyCategories));
    }

    @Test
    void testValidateBrandValid() {
        assertDoesNotThrow(() -> Validator.validateBrand(new Brand(1L,"Sony", "Electronics")));
    }

    @Test
    void testValidateBrandInvalid() {
        assertThrows(InvalidBrandException.class, () -> Validator.validateBrand(null));
    }

    @Test
    void testValidateCategoryValid() {
        assertDoesNotThrow(() -> Validator.validateCategory("Electronics", "Devices and gadgets"));
    }

    @Test
    void testValidateCategoryInvalid() {
        assertThrows(InvalidNameException.class, () -> Validator.validateCategory(null, "Devices and gadgets"));
        assertThrows(InvalidNameException.class, () -> Validator.validateCategory("", "Devices and gadgets"));
        assertThrows(InvalidNameException.class, () -> Validator.validateCategory("A very long name that exceeds the maximum lengthA very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,", "Devices and gadgets"));

        assertThrows(InvalidDescriptionException.class, () -> Validator.validateCategory("Electronics", null));
        assertThrows(InvalidDescriptionException.class, () -> Validator.validateCategory("Electronics", ""));
        assertThrows(InvalidDescriptionException.class, () -> Validator.validateCategory("Electronics", "A description that exceeds the maximum length of the descriptionA very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,"));
    }

    @Test
    void testValidateBrandStringValid() {
        assertDoesNotThrow(() -> Validator.validateBrand("Sony", "Leading electronics brand"));
    }

    @Test
    void testValidateBrandStringInvalid() {
        assertThrows(InvalidNameException.class, () -> Validator.validateBrand(null, "Leading electronics brand"));
        assertThrows(InvalidNameException.class, () -> Validator.validateBrand("", "Leading electronics brand"));
        assertThrows(InvalidNameException.class, () -> Validator.validateBrand("A very long name that exceeds the maximum length, A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,however it was not as long as needed, so we had to increase the letters", "Leading electronics brand"));

        assertThrows(InvalidDescriptionException.class, () -> Validator.validateBrand("Sony", null));
        assertThrows(InvalidDescriptionException.class, () -> Validator.validateBrand("Sony", ""));
        assertThrows(InvalidDescriptionException.class, () -> Validator.validateBrand("Sony", "A description that exceeds the maximum length of the description but hey we have to put it longer because you know A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,A very long name that exceeds the maximum length,"));
    }
}
