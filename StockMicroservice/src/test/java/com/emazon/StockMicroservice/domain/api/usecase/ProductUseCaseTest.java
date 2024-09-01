package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.exception.InvalidCategoryException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.exception.InvalidPriceException;
import com.emazon.StockMicroservice.domain.exception.InvalidQuantityException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.spi.IProductPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductUseCaseTest {
    @Mock
    private IProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la cantidad es negativa")
    void shouldThrowExceptionWhenQuantityIsNegative() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets"));
        Brand brand = new Brand(1L, "Adidas", "Renowned for its wide range of sports clothing, shoes, and accessories");

        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", -1, 99.99, categories, brand);

        assertThrows(InvalidQuantityException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el precio es negativo")
    void shouldThrowExceptionWhenPriceIsNegative() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets"));
        Brand brand = new Brand(1L, "Adidas", "Renowned for its wide range of sports clothing, shoes, and accessories");

        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, -99.99, categories, brand);

        assertThrows(InvalidPriceException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando no se proporcionan categorías")
    void shouldThrowExceptionWhenCategoriesAreEmpty() {
        Brand brand = new Brand(1L, "Adidas", "Renowned for its wide range of sports clothing, shoes, and accessories");

        List<Category> emptyCategories = List.of();

        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, emptyCategories, brand);

        assertThrows(InvalidCategoryException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando hay categorías duplicadas")
    void shouldThrowExceptionWhenCategoriesAreDuplicated() {
        Category category = new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets");
        List<Category> categories = List.of(category, category);

        Brand brand = new Brand(1L, "Adidas", "Renowned for its wide range of sports clothing, shoes, and accessories");

        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, brand);

        assertThrows(InvalidCategoryException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando no se proporciona una marca")
    void shouldThrowExceptionWhenBrandIsNull() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets"));

        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, null);

        assertThrows(IllegalArgumentException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre ya existe")
    void shouldThrowExceptionWhenNameAlreadyExists() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets"));
        Brand brand = new Brand(1L, "Adidas", "Renowned for its wide range of sports clothing, shoes, and accessories");

        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, brand);

        when(productPersistencePort.existsByName("Running Shoes")).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Debería guardar el producto cuando todos los datos son válidos")
    void shouldSaveProductWhenValidInput() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel including shirts, trousers, dresses, and jackets"));
        Brand brand = new Brand(1L, "Adidas", "Renowned for its wide range of sports clothing, shoes, and accessories");

        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, brand);

        when(productPersistencePort.existsByName("Running Shoes")).thenReturn(false);

        productUseCase.saveProduct(product);

        verify(productPersistencePort, times(1)).saveProduct(any(Product.class));
    }
}
