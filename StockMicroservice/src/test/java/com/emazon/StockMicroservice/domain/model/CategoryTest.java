package com.emazon.StockMicroservice.domain.model;

import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    @Test
    void shouldCreateCategoryWhenValidInput() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        assertNotNull(category);
        assertEquals("Electronics", category.getName());
        assertEquals("Devices and gadgets", category.getDescription());
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooLong() {
        String longName = "A".repeat(51);
        assertThrows(InvalidNameException.class, () -> new Category(1L, longName,"Valid description"));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsTooLong() {
        String longDescription = "A".repeat(91);
        assertThrows(InvalidDescriptionException.class, () -> new Category(1L,"Valid name", longDescription));
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(InvalidNameException.class, () -> new Category(1L, "", "Valid description"));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        assertThrows(InvalidDescriptionException.class, () -> new Category(1L,"Valid name", ""));
    }
}
