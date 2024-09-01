package com.emazon.StockMicroservice.adapters.driving.controller;

import com.emazon.StockMicroservice.adapters.driving.http.controller.CategoryRestControllerAdapter;
import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.StockMicroservice.domain.api.ICategoryServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
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

@WebMvcTest(controllers = CategoryRestControllerAdapter.class)
class CategoryRestControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryServicePort categoryServicePort;

    @MockBean
    private ICategoryRequestMapper categoryRequestMapper;

    @Test
    @DisplayName("Should return Bad Request when category name already exists")
    void shouldReturnBadRequestWhenCategoryNameAlreadyExists() throws Exception {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        when(categoryRequestMapper.toModel(any(AddCategoryRequest.class))).thenReturn(category);
        doThrow(new InvalidNameException("The category with the name 'Electronics' already exists."))
                .when(categoryServicePort).saveCategory(any(Category.class));

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The category with the name 'Electronics' already exists."));
    }

    @Test
    @DisplayName("Should create the category successfully")
    void shouldCreateCategorySuccessfully() throws Exception {
        Category category = new Category(1L, "Books", "All kinds of books");

        when(categoryRequestMapper.toModel(any(AddCategoryRequest.class))).thenReturn(category);

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Books\",\"description\":\"All kinds of books\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Category 'Books' has been successfully added."));
    }

    @Test
    @DisplayName("Should return all categories")
    void shouldReturnAllCategories() throws Exception {
        PagedResult<Category> pagedResult = new PagedResult<>(
                List.of(new Category(1L, "Electronics", "Devices and gadgets")),
                0,
                10,
                1
        );

        when(categoryServicePort.listCategories(0, 10, SortDirection.ASC)).thenReturn(pagedResult);

        mockMvc.perform(get("/category")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "ASC")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[{\"id\":1,\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}],\"page\":0,\"size\":10,\"totalElements\":1,\"totalPages\":1}"));
    }
}