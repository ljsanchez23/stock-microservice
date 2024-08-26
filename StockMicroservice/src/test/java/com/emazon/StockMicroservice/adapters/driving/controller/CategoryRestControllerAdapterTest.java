package com.emazon.StockMicroservice.adapters.driving.controller;

import com.emazon.StockMicroservice.adapters.driving.http.controller.CategoryRestControllerAdapter;
import com.emazon.StockMicroservice.domain.api.usecase.CreateCategoryUseCase;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryRestControllerAdapter.class)
class CategoryRestControllerAdapterTest {

    private MockMvc mockMvc;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CategoryRestControllerAdapter(createCategoryUseCase)).build();
    }

    @Test
    void shouldReturnBadRequestWhenCategoryNameAlreadyExists() throws Exception {
        doThrow(new InvalidNameException("La categoría con el nombre 'Electronics' ya existe."))
                .when(createCategoryUseCase).saveCategory(any(Category.class));

        mockMvc.perform(post("/category")
                        .contentType("application/json")
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La categoría con el nombre 'Electronics' ya existe."));
    }
}
