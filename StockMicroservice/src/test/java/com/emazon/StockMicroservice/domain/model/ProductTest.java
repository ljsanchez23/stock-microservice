package com.emazon.StockMicroservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    @DisplayName("Should correctly initialize the product attributes")
    void shouldInitializeProductAttributesCorrectly() {
        Long id = 1L;
        String name = "Running Shoes";
        String description = "High-performance running shoes";
        int quantity = 10;
        double price = 99.99;
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        List<Category> categories = List.of(
                new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets")
        );
        Product product = new Product(id, name, description, quantity, price, categories, brand);
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(quantity, product.getQuantity());
        assertEquals(price, product.getPrice());
        assertEquals(categories, product.getCategories());
        assertEquals(brand, product.getBrand());
    }
}
