package com.emazon.StockMicroservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrandTest {
    @Test
    @DisplayName("Debería crear una marca cuando se proporciona una entrada válida")
    void shouldCreateBrandWhenValidInput() {
        Brand brand = new Brand(1L, "Adidas", "Renowned for its wide range of sports clothing, shoes, and accessories");
        assertNotNull(brand);
        assertEquals(1L, brand.getId());
        assertEquals("Adidas", brand.getName());
        assertEquals("Renowned for its wide range of sports clothing, shoes, and accessories", brand.getDescription());
    }
}
