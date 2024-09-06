package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.exception.InvalidCategoryException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.exception.InvalidPriceException;
import com.emazon.StockMicroservice.domain.exception.InvalidQuantityException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.spi.IProductPersistencePort;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Should save the product when the inputs are valid")
    void shouldSaveProductWhenValid() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel"));
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, brand);

        when(productPersistencePort.existsByName("Running Shoes")).thenReturn(false);
        productUseCase.saveProduct(product);

        verify(productPersistencePort, times(1)).saveProduct(any(Product.class));
    }

    @Test
    @DisplayName("Should throw exception when the product name already exists")
    void shouldThrowExceptionWhenNameAlreadyExists() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel"));
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, brand);

        when(productPersistencePort.existsByName("Running Shoes")).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Should throw exception when the product quantity is negative")
    void shouldThrowExceptionWhenQuantityIsNegative() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel"));
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", -1, 99.99, categories, brand);

        assertThrows(InvalidQuantityException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Should throw exception when the product price is negative")
    void shouldThrowExceptionWhenPriceIsNegative() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel"));
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, -99.99, categories, brand);

        assertThrows(InvalidPriceException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Should throw exception when categories are empty")
    void shouldThrowExceptionWhenCategoriesAreEmpty() {
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, List.of(), brand);

        assertThrows(InvalidCategoryException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Should throw exception when there are more than three categories")
    void shouldThrowExceptionWhenMoreThanThreeCategories() {
        List<Category> categories = List.of(
                new Category(1L, "Clothing", "Apparel"),
                new Category(2L, "Electronics", "Gadgets"),
                new Category(3L, "Books", "Literature"),
                new Category(4L, "Sports", "Equipment")
        );
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, brand);

        assertThrows(InvalidCategoryException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Should throw exception when the input has duplicated categories")
    void shouldThrowExceptionWhenCategoriesAreDuplicated() {
        Category category = new Category(1L, "Clothing", "Apparel");
        List<Category> categories = List.of(category, category);
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, brand);

        assertThrows(InvalidCategoryException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
    @DisplayName("Should throw exception when a brand is not given")
    void shouldThrowExceptionWhenBrandIsNull() {
        List<Category> categories = List.of(new Category(1L, "Clothing", "Apparel"));
        Product product = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, categories, null);

        assertThrows(IllegalArgumentException.class, () -> productUseCase.saveProduct(product));
    }

    @ParameterizedTest
    @CsvSource({
            "ASC, name",
            "DESC, name"
    })
    @DisplayName("Should return paged result with provided sort direction")
    void shouldReturnPagedResultWithSortDirection(String sortDirection, String sortField) {
        List<Product> products = List.of(new Product(1L, "Adidas Campus", "Casual shoes", 10, 20.0, List.of(), new Brand(1L, "Adidas", "Fast as wind")));
        PagedResult<Product> pagedResult = new PagedResult<>(products, 0, 10, 1);

        when(productPersistencePort.getAllProducts(0, 10, sortDirection, "name")).thenReturn(pagedResult);

        PagedResult<Product> result = productUseCase.listProducts(0, 10, sortDirection, sortField);

        assertEquals(pagedResult, result);
    }

    @ParameterizedTest
    @CsvSource({
            "product, name",
            "brand, brand.name"
    })
    @DisplayName("Should return paged result with proper sort field")
    void shouldReturnPagedResultWithSortField(String sortFieldInput, String expectedSortField) {
        List<Product> products = List.of(new Product(1L, "Product1", "Description1", 10, 20.0, List.of(), new Brand(1L, "Adidas", "Fast as wind")));
        PagedResult<Product> pagedResult = new PagedResult<>(products, 0, 10, 1);

        when(productPersistencePort.getAllProducts(0, 10, "ASC", expectedSortField)).thenReturn(pagedResult);

        PagedResult<Product> result = productUseCase.listProducts(0, 10, "ASC", sortFieldInput);

        assertEquals(pagedResult, result);
    }

    @Test
    @DisplayName("Should return paged result with sorted products by category name in ascending order")
    void shouldSortProductsByCategoryNameAscending() {

        Category category1 = new Category(1L, "Clothing", "Apparel");
        Category category2 = new Category(2L, "Electronics", "Gadgets");

        Product product1 = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, List.of(category1), new Brand(1L, "Adidas", "Sportswear"));
        Product product2 = new Product(2L, "Smartphone", "Latest model", 20, 299.99, List.of(category2), new Brand(2L, "Samsung", "Electronics"));

        List<Product> products = List.of(product1, product2);
        PagedResult<Product> pagedResult = new PagedResult<>(products, 0, 10, products.size());

        when(productPersistencePort.getAllProducts(0, 10, "ASC", "categories.name")).thenReturn(pagedResult);

        PagedResult<Product> result = productUseCase.listProducts(0, 10, "ASC", "category");

        assertNotNull(result);
        List<Product> sortedProducts = result.getContent();
        assertEquals(2, sortedProducts.size());
        assertEquals("Clothing", sortedProducts.get(0).getCategories().get(0).getName());
        assertEquals("Electronics", sortedProducts.get(1).getCategories().get(0).getName());
    }

    @Test
    @DisplayName("Should return products sorted by category name in descending order")
    void shouldSortProductsByCategoryNameDescending() {
        Category category1 = new Category(1L, "Clothing", "Apparel");
        Category category2 = new Category(2L, "Electronics", "Gadgets");

        Product product1 = new Product(1L, "Running Shoes", "High-performance running shoes", 10, 99.99, List.of(category1), new Brand(1L, "Adidas", "Sportswear"));
        Product product2 = new Product(2L, "Smartphone", "Latest model", 20, 299.99, List.of(category2), new Brand(2L, "Samsung", "Electronics"));

        List<Product> products = List.of(product1, product2);
        PagedResult<Product> pagedResult = new PagedResult<>(products, 0, 10, products.size());

        when(productPersistencePort.getAllProducts(0, 10, "DESC", "categories.name")).thenReturn(pagedResult);

        PagedResult<Product> result = productUseCase.listProducts(0, 10, "DESC", "category");

        assertNotNull(result);
        List<Product> sortedProducts = result.getContent();
        assertEquals(2, sortedProducts.size());
        assertEquals("Electronics", sortedProducts.get(0).getCategories().get(0).getName(), "First product should be in the Electronics category");
        assertEquals("Clothing", sortedProducts.get(1).getCategories().get(0).getName(), "Second product should be in the Clothing category");
    }
}
