package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IProductRepository;
import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductAdapterTest {
    private ProductAdapter articleAdapter;
    private IProductRepository articleRepository;
    private IProductEntityMapper articleEntityMapper;

    @BeforeEach
    void setUp() {
        articleRepository = mock(IProductRepository.class);
        articleEntityMapper = mock(IProductEntityMapper.class);
        articleAdapter = new ProductAdapter(articleRepository, articleEntityMapper);
    }

    @Test
    @DisplayName("Debería guardar el artículo cuando el nombre es único")
    void shouldSaveProductWhenNameIsUnique() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets"));
        Brand brand = new Brand(1L, "Adidas", "Renowned for its wide range of sports clothing, shoes, and accessories");

        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, brand);
        ProductEntity productEntity = new ProductEntity();

        when(articleRepository.findByName("Running Shoes")).thenReturn(Optional.empty());

        when(articleEntityMapper.toEntity(product)).thenReturn(productEntity);

        articleAdapter.saveProduct(product);

        verify(articleRepository, times(1)).save(productEntity);
    }

    @Test
    @DisplayName("Debería devolver true cuando el artículo existe")
    void shouldReturnTrueWhenArticleExists() {
        String name = "Running Shoes";
        when(articleRepository.findByName(name)).thenReturn(Optional.of(new ProductEntity()));

        boolean result = articleAdapter.existsByName(name);

        assertTrue(result, "The article should exist.");
    }
    @Test
    @DisplayName("Debería devolver false cuando el artículo no existe")
    void shouldReturnFalseWhenArticleDoesNotExist() {
        String name = "Running Shoes";
        when(articleRepository.findByName(name)).thenReturn(Optional.empty());

        boolean result = articleAdapter.existsByName(name);

        assertFalse(result, "The article should not exist.");
    }
}
