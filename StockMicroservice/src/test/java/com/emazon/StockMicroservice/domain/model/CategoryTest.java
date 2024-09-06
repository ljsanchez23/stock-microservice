package com.emazon.StockMicroservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    @Test
    @DisplayName("Should correctly initialize the category attributes")
    void shouldInitializeCategoryAttributesCorrectly() {

        Long id = 1L;
        String name = "Clothing";
        String description = "Apparel including shirts, trousers, dresses, and jackets";

        Category category = new Category(id, name, description);
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
    }

    @Test
    @DisplayName("Should allow setting a new name and description")
    void shouldSetNameAndDescription() {
        Category category = new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets");
        category.setName("Electronics");
        category.setDescription("Devices and gadgets like phones, laptops, and cameras");
        assertEquals("Clothing", category.getName());
        assertEquals("Apparel including shirts, trousers, dresses, and jackets", category.getDescription());
    }
}
