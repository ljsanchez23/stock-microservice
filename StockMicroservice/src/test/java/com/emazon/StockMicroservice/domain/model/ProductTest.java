package com.emazon.StockMicroservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    @DisplayName("Debería crear un producto cuando se proporciona una entrada válida")
    void shouldCreateArticleWhenValidInput() {
        List<Category> categories = List.of(
                new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets")
        );
        Brand brand = new Brand(1L, "Adidas", "Renowned for its wide range of sports clothing, shoes, and accessories");
        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, brand);
        assertNotNull(product);
        assertEquals("Running Shoes", product.getName());
        assertEquals("High-performance running shoes", product.getDescription());
        assertEquals(10, product.getQuantity());
        assertEquals(99.99, product.getPrice());
        assertEquals(1, product.getCategories().size());
        assertEquals(brand, product.getBrand());
    }
}
