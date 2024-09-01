package com.emazon.StockMicroservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    @Test
    @DisplayName("Debería crear una categoría cuando se proporciona una entrada válida")
    void shouldCreateCategoryWhenValidInput() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        assertNotNull(category);
        assertEquals("Electronics", category.getName());
        assertEquals("Devices and gadgets", category.getDescription());
    }
}
