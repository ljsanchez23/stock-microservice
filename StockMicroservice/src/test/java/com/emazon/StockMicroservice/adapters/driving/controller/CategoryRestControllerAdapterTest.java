package com.emazon.StockMicroservice.adapters.driving.controller;

import com.emazon.StockMicroservice.adapters.driving.http.controller.CategoryRestControllerAdapter;
import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.StockMicroservice.domain.api.ICategoryServicePort;
import com.emazon.StockMicroservice.domain.model.Category;
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
import static org.mockito.Mockito.*;

class CategoryRestControllerAdapterTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

    @InjectMocks
    private CategoryRestControllerAdapter categoryRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryRestControllerAdapter = new CategoryRestControllerAdapter(categoryServicePort, categoryRequestMapper);
    }

    @Test
    @DisplayName("Should add a new category correctly")
    void shouldSaveCategory() {
        AddCategoryRequest addCategoryRequest = new AddCategoryRequest(1L, "Electronics", "Devices and gadgets");
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        when(categoryRequestMapper.toModel(addCategoryRequest)).thenReturn(category);

        ResponseEntity<Map<String, Object>> response = categoryRestControllerAdapter.saveCategory(addCategoryRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Category has been successfully added.", response.getBody().get("message"));
        assertEquals("Electronics", response.getBody().get("name"));
        verify(categoryServicePort, times(1)).saveCategory(category);
    }

    @Test
    @DisplayName("Should return a paginated result of categories")
    void shouldReturnPagedResultOfCategories() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        PagedResult<Category> pagedResult = new PagedResult<>(List.of(category), 0, 10, 1L);
        when(categoryServicePort.listCategories(0, 10, "ASC")).thenReturn(pagedResult);

        ResponseEntity<PagedResult<Category>> response = categoryRestControllerAdapter.getAllCategories(0, 10, "ASC");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(category, response.getBody().getContent().get(0));
        assertEquals(0, response.getBody().getPage());
        assertEquals(10, response.getBody().getSize());
        assertEquals(1L, response.getBody().getTotalElements());
        verify(categoryServicePort, times(1)).listCategories(0, 10, "ASC");
    }
}
