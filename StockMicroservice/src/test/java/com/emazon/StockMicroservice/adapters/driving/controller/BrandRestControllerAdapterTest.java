package com.emazon.StockMicroservice.adapters.driving.controller;

import com.emazon.StockMicroservice.adapters.driving.http.controller.BrandRestControllerAdapter;
import com.emazon.StockMicroservice.configuration.StringToSortDirectionConverter;
import com.emazon.StockMicroservice.domain.api.ICreateBrandServicePort;
import com.emazon.StockMicroservice.domain.api.IFindAllBrandsServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(StringToSortDirectionConverter.class)
class BrandRestControllerAdapterTest {

    private MockMvc mockMvc;

    @Mock
    private ICreateBrandServicePort createBrandServicePort;

    @Mock
    private IFindAllBrandsServicePort findAllBrandsServicePort;

    @InjectMocks
    private BrandRestControllerAdapter brandRestControllerAdapter;

    public BrandRestControllerAdapterTest() {
        openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(brandRestControllerAdapter).build();
    }

    @Test
    @DisplayName("Debería devolver Bad Request cuando el nombre de la marca ya existe")
    void shouldReturnBadRequestWhenBrandNameAlreadyExists() throws Exception {
        doThrow(new InvalidNameException("The brand with the name 'Adimas' already exists."))
                .when(createBrandServicePort).saveBrand(any(Brand.class));
        mockMvc.perform(post("/brand")
                        .contentType("application/json")
                        .content("{\"name\":\"Adimas\",\"description\":\"Shoes\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The brand with the name 'Adimas' already exists."));
    }

    @Test
    @DisplayName("Debería crear la marca exitosamente")
    void shouldCreateBrandSuccessfully() throws Exception {
        mockMvc.perform(post("/brand")
                        .contentType("application/json")
                        .content("{\"name\":\"Nacho\",\"description\":\"A wide range of books\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Brand 'Nacho' has been successfully added."));
    }
    @Test
    @DisplayName("Debería devolver todas las marcas")
    void shouldReturnAllBrands() throws Exception {
        PagedResult<Brand> pagedResult = new PagedResult<>(
                List.of(new Brand(1L, "Adimas", "Shoes")),
                0,
                10,
                1
        );
        when(findAllBrandsServicePort.getAllBrands(0, 10, SortDirection.ASC)).thenReturn(pagedResult);
        mockMvc.perform(get("/brand")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "ASC"))
                .andExpect(status().isOk());
    }
}
