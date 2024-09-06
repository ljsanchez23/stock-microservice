package com.emazon.StockMicroservice.adapters.driving.controller;


import com.emazon.StockMicroservice.adapters.driving.http.controller.ProductRestControllerAdapter;
import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddProductRequest;
import com.emazon.StockMicroservice.adapters.driving.http.dto.response.ProductResponse;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IProductRequestMapper;
import com.emazon.StockMicroservice.domain.api.IProductServicePort;
import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductRestControllerAdapterTest {

    @Mock
    private IProductServicePort productServicePort;

    @Mock
    private IProductRequestMapper productRequestMapper;

    @InjectMocks
    private ProductRestControllerAdapter productRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productRestControllerAdapter = new ProductRestControllerAdapter(productServicePort, productRequestMapper);
    }

    @Test
    @DisplayName("Should add a new product correctly")
    void shouldSaveProduct() {
        AddProductRequest addProductRequest = new AddProductRequest(1L, "Laptop", "High-end gaming laptop", 5, 1500.00, List.of(), null);
        Product product = new Product(1L, "Laptop", "High-end gaming laptop", 5, 1500.00, List.of(), null);
        when(productRequestMapper.toModel(addProductRequest)).thenReturn(product);

        ResponseEntity<Map<String, Object>> response = productRestControllerAdapter.saveProduct(addProductRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Product has been successfully added.", response.getBody().get("message"));
        assertEquals("Laptop", response.getBody().get("name"));
        verify(productServicePort, times(1)).saveProduct(product);
    }

    @Test
    @DisplayName("Should return a paginated result of products")
    void shouldReturnPagedResultOfProducts() {
        Product product = new Product(1L, "Laptop", "High-end gaming laptop", 5, 1500.00, List.of(), null);
        ProductResponse productResponse = new ProductResponse(
                1L,
                "Laptop",
                "High-end gaming laptop",
                1500.00,
                5,
                List.of(),
                null
        );
        PagedResult<Product> pagedResult = new PagedResult<>(List.of(product), 0, 10, 1L);

        PagedResult<ProductResponse> expectedPagedResult = new PagedResult<>(
                List.of(productResponse),
                0,
                10,
                1L
        );

        when(productServicePort.listProducts(0, 10, "ASC", "name")).thenReturn(pagedResult);
        when(productRequestMapper.toResponse(product)).thenReturn(productResponse);

        ResponseEntity<PagedResult<ProductResponse>> response = productRestControllerAdapter.getAllProducts(0, 10, "ASC", "name");

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PagedResult<ProductResponse> actualPagedResult = response.getBody();
        assertNotNull(actualPagedResult);
        assertEquals(expectedPagedResult.getPage(), actualPagedResult.getPage());
        assertEquals(expectedPagedResult.getSize(), actualPagedResult.getSize());
        assertEquals(expectedPagedResult.getTotalElements(), actualPagedResult.getTotalElements());
        assertEquals(expectedPagedResult.getTotalPages(), actualPagedResult.getTotalPages());
        assertEquals(expectedPagedResult.getContent(), actualPagedResult.getContent());

        verify(productServicePort, times(1)).listProducts(0, 10, "ASC", "name");
        verify(productRequestMapper, times(1)).toResponse(product);
    }
}
