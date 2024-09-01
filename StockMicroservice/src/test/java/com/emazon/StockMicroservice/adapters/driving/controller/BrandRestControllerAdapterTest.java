package com.emazon.StockMicroservice.adapters.driving.controller;

import com.emazon.StockMicroservice.adapters.driving.http.controller.BrandRestControllerAdapter;
import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.StockMicroservice.domain.api.IBrandServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BrandRestControllerAdapter.class)
class BrandRestControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBrandServicePort brandServicePort;

    @MockBean
    private IBrandRequestMapper brandRequestMapper;

    @Test
    @DisplayName("Should return Bad Request when brand name already exists")
    void shouldReturnBadRequestWhenBrandNameAlreadyExists() throws Exception {
        Brand brand = new Brand(1L, "Adimas", "Shoes");

        when(brandRequestMapper.toModel(any(AddBrandRequest.class))).thenReturn(brand);
        doThrow(new InvalidNameException("The brand with the name 'Adimas' already exists."))
                .when(brandServicePort).saveBrand(any(Brand.class));

        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Adimas\",\"description\":\"Shoes\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The brand with the name 'Adimas' already exists."));
    }

    @Test
    @DisplayName("Should create the brand successfully")
    void shouldCreateBrandSuccessfully() throws Exception {
        Brand brand = new Brand(1L, "Nacho", "A wide range of books");

        when(brandRequestMapper.toModel(any(AddBrandRequest.class))).thenReturn(brand);

        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nacho\",\"description\":\"A wide range of books\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Brand 'Nacho' has been successfully added."));
    }

    @Test
    @DisplayName("Should return all brands")
    void shouldReturnAllBrands() throws Exception {
        PagedResult<Brand> pagedResult = new PagedResult<>(
                List.of(new Brand(1L, "Adimas", "Shoes")),
                0,
                10,
                1
        );

        when(brandServicePort.listBrands(0, 10, SortDirection.ASC)).thenReturn(pagedResult);

        mockMvc.perform(get("/brand")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "ASC")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[{\"id\":1,\"name\":\"Adimas\",\"description\":\"Shoes\"}],\"page\":0,\"size\":10,\"totalElements\":1,\"totalPages\":1}"));
    }
}
