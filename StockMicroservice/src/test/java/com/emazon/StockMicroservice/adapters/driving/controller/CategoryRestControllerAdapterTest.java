package com.emazon.StockMicroservice.adapters.driving.controller;

import com.emazon.StockMicroservice.configuration.StringToSortDirectionConverter;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.emazon.StockMicroservice.adapters.driving.http.controller.CategoryRestControllerAdapter;
import com.emazon.StockMicroservice.domain.api.ICreateCategoryServicePort;
import com.emazon.StockMicroservice.domain.api.IFindAllCategoriesServicePort;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.SortDirection;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Import(StringToSortDirectionConverter.class)
class CategoryRestControllerAdapterTest {

    private MockMvc mockMvc;

    @Mock
    private ICreateCategoryServicePort createCategoryServicePort;

    @Mock
    private IFindAllCategoriesServicePort findAllCategoriesServicePort;

    @InjectMocks
    private CategoryRestControllerAdapter categoryRestControllerAdapter;

    public CategoryRestControllerAdapterTest() {
        openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryRestControllerAdapter).build();
    }

    @Test
    @DisplayName("Debería devolver Bad Request cuando el nombre de la categoría ya existe")
    void shouldReturnBadRequestWhenCategoryNameAlreadyExists() throws Exception {
        doThrow(new InvalidNameException("The category with the name 'Electronics' already exists."))
                .when(createCategoryServicePort).saveCategory(any(Category.class));
        mockMvc.perform(post("/category")
                        .contentType("application/json")
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The category with the name 'Electronics' already exists."));
    }

    @Test
    @DisplayName("Debería crear la categoría exitosamente")
    void shouldCreateCategorySuccessfully() throws Exception {
        mockMvc.perform(post("/category")
                        .contentType("application/json")
                        .content("{\"name\":\"Books\",\"description\":\"A wide range of books\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Category 'Books' has been successfully added."));
    }
    @Test
    @DisplayName("Debería devolver todas las categorías")
    void shouldReturnAllCategories() throws Exception {
        PagedResult<Category> pagedResult = new PagedResult<>(
                List.of(new Category(1L, "Electronics", "Devices")),
                0,
                10,
                1
        );
        when(findAllCategoriesServicePort.getAllCategories(0, 10, SortDirection.ASC)).thenReturn(pagedResult);
        mockMvc.perform(get("/category")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "ASC"))
                .andExpect(status().isOk());
    }
}
