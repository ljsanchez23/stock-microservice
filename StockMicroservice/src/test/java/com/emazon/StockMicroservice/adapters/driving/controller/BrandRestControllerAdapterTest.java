package com.emazon.StockMicroservice.adapters.driving.controller;

import com.emazon.StockMicroservice.adapters.driving.http.controller.BrandRestControllerAdapter;
import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.StockMicroservice.domain.api.IBrandServicePort;
import com.emazon.StockMicroservice.domain.model.Brand;
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

@DisplayName("Tests para la clase BrandRestControllerAdapter")
class BrandRestControllerAdapterTest {

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;

    @InjectMocks
    private BrandRestControllerAdapter brandRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brandRestControllerAdapter = new BrandRestControllerAdapter(brandServicePort, brandRequestMapper);
    }

    @Test
    @DisplayName("Debería añadir una nueva marca correctamente")
    void shouldSaveBrand() {
        // Arrange
        AddBrandRequest addBrandRequest = new AddBrandRequest(1L,"Adidas", "Sportswear");
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        when(brandRequestMapper.toModel(addBrandRequest)).thenReturn(brand);

        // Act
        ResponseEntity<Map<String, Object>> response = brandRestControllerAdapter.saveBrand(addBrandRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Brand has been successfully added.", response.getBody().get("message"));
        assertEquals("Adidas", response.getBody().get("name"));
        verify(brandServicePort, times(1)).saveBrand(brand);
    }

    @Test
    @DisplayName("Debería devolver una lista paginada de marcas")
    void shouldReturnPagedResultOfBrands() {
        // Arrange
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        PagedResult<Brand> pagedResult = new PagedResult<>(List.of(brand), 0, 10, 1L);
        when(brandServicePort.listBrands(0, 10, "ASC")).thenReturn(pagedResult);

        // Act
        ResponseEntity<PagedResult<Brand>> response = brandRestControllerAdapter.getAllBrands(0, 10, "ASC");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(brand, response.getBody().getContent().get(0));
        assertEquals(0, response.getBody().getPage());
        assertEquals(10, response.getBody().getSize());
        assertEquals(1L, response.getBody().getTotalElements());
        verify(brandServicePort, times(1)).listBrands(0, 10, "ASC");
    }
}
