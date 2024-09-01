package com.emazon.StockMicroservice.adapters.driving.controller;

import com.emazon.StockMicroservice.adapters.driving.http.controller.ProductRestControllerAdapter;
import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddProductRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IProductRequestMapper;
import com.emazon.StockMicroservice.domain.api.IProductServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductRestControllerAdapter.class)
class ProductRestControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductServicePort productServicePort;

    @MockBean
    private IProductRequestMapper productRequestMapper;

    @Test
    @DisplayName("Should return Bad Request when product name already exists")
    void shouldReturnBadRequestWhenProductNameAlreadyExists() throws Exception {
        Product product = new Product(1L, "Laptop", "High-end gaming laptop", 5, 1500.00,
                List.of(new Category(1L, "Electronics", "Devices and gadgets")), new Brand(1L, "TechBrand", "Leading tech brand"));

        when(productRequestMapper.toModel(any(AddProductRequest.class))).thenReturn(product);
        doThrow(new InvalidNameException("The product with the name 'Laptop' already exists."))
                .when(productServicePort).saveProduct(any(Product.class));

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Laptop\",\"description\":\"High-end gaming laptop\",\"quantity\":5,\"price\":1500.00,\"categories\":[{\"id\":1,\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}],\"brand\":{\"id\":1,\"name\":\"TechBrand\",\"description\":\"Leading tech brand\"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The product with the name 'Laptop' already exists."));
    }

    @Test
    @DisplayName("Should create the product successfully")
    void shouldCreateProductSuccessfully() throws Exception {
        Product product = new Product(1L, "Smartphone", "Latest model smartphone", 10, 999.99,
                List.of(new Category(1L, "Electronics", "Devices and gadgets")), new Brand(1L, "SmartBrand", "Top smartphone brand"));

        when(productRequestMapper.toModel(any(AddProductRequest.class))).thenReturn(product);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Smartphone\",\"description\":\"Latest model smartphone\",\"quantity\":10,\"price\":999.99,\"categories\":[{\"id\":1,\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}],\"brand\":{\"id\":1,\"name\":\"SmartBrand\",\"description\":\"Top smartphone brand\"}}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product 'Smartphone' has been successfully added."));
    }
}
