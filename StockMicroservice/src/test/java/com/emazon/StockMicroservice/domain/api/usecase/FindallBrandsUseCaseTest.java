package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

class FindallBrandsUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private FindAllBrandsUseCase findAllBrandsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería devolver un resultado paginado de marcas")
    void shouldReturnPagedResultOfBrands() {
        int page = 0;
        int size = 10;
        SortDirection sortDirection = SortDirection.ASC;
        PagedResult<Brand> expectedPagedResult = new PagedResult<>(
                List.of(new Brand(1L, "Adimas", "Shoes")),
                page,
                size,
                1
        );
        when(brandPersistencePort.getAllBrands(page, size, sortDirection)).thenReturn(expectedPagedResult);
        PagedResult<Brand> actualPagedResult = findAllBrandsUseCase.getAllBrands(page, size, sortDirection);
        assertEquals(expectedPagedResult, actualPagedResult);
    }

    @Test
    @DisplayName("Debería devolver un resultado vacío cuando no existen marcas")
    void shouldReturnEmptyResultWhenNoBrandsExist() {
        int page = 0;
        int size = 10;
        SortDirection sortDirection = SortDirection.ASC;

        PagedResult<Brand> expectedPagedResult = new PagedResult<>(
                List.of(),
                page,
                size,
                0
        );

        when(brandPersistencePort.getAllBrands(page, size, sortDirection)).thenReturn(expectedPagedResult);

        PagedResult<Brand> actualPagedResult = findAllBrandsUseCase.getAllBrands(page, size, sortDirection);

        assertEquals(expectedPagedResult, actualPagedResult);
        assertEquals(0, actualPagedResult.getTotalElements());
        assertEquals(0, actualPagedResult.getContent().size());
    }

    @Test
    @DisplayName("Debería manejar el ordenamiento por orden descendente correctamente")
    void shouldHandleSortingByDescendingOrder() {
        int page = 0;
        int size = 10;
        SortDirection sortDirection = SortDirection.DESC;

        List<Brand> brands = List.of(
                new Brand(2L, "Pfizor", "Pharmaceutics"),
                new Brand(1L, "Adimas", "Shoes")
        );
        PagedResult<Brand> expectedPagedResult = new PagedResult<>(
                brands,
                page,
                size,
                brands.size()
        );

        when(brandPersistencePort.getAllBrands(page, size, sortDirection)).thenReturn(expectedPagedResult);

        PagedResult<Brand> actualPagedResult = findAllBrandsUseCase.getAllBrands(page, size, sortDirection);

        assertEquals(expectedPagedResult, actualPagedResult);
        assertEquals(brands, actualPagedResult.getContent());
    }

    @Test
    @DisplayName("Debería llamar al puerto de persistencia con los parámetros correctos")
    void shouldCallPersistencePortWithCorrectParameters() {
        int page = 1;
        int size = 20;
        SortDirection sortDirection = SortDirection.DESC;

        findAllBrandsUseCase.getAllBrands(page, size, sortDirection);

        verify(brandPersistencePort, times(1)).getAllBrands(page, size, sortDirection);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el puerto de persistencia falla")
    void shouldThrowExceptionWhenPersistencePortFails() {

        int page = 1;
        int size = 10;
        SortDirection sortDirection = SortDirection.ASC;

        doThrow(new RuntimeException("Persistence error")).when(brandPersistencePort).getAllBrands(page, size, sortDirection);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            findAllBrandsUseCase.getAllBrands(page, size, sortDirection);
        });
        assertEquals("Persistence error", exception.getMessage());
    }
}
