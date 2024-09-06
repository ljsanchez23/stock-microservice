package com.emazon.StockMicroservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandTest {

    @Test
    @DisplayName("Should correctly initialize the brand attributes")
    void shouldInitializeBrandAttributesCorrectly() {
        Long id = 1L;
        String name = "Adidas";
        String description = "Renowned for its sports clothing and accessories.";

        Brand brand = new Brand(id, name, description);

        assertEquals(id, brand.getId());
        assertEquals(name, brand.getName());
        assertEquals(description, brand.getDescription());
    }

    @Test
    @DisplayName("Should allow setting a new name and description")
    void shouldSetNameAndDescription() {
        Brand brand = new Brand(1L, "Adidas", "Renowned for its sports clothing and accessories.");

        brand.setName("Nike");
        brand.setDescription("Specializes in athletic footwear, apparel, and equipment.");

        assertEquals("Adidas", brand.getName());
        assertEquals("Renowned for its sports clothing and accessories.", brand.getDescription());
    }
}
