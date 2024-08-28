package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
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

class FindAllCategoriesUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private FindAllCategoriesUseCase findAllCategoriesUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería devolver un resultado paginado de categorías")
    void shouldReturnPagedResultOfCategories() {
        int page = 0;
        int size = 10;
        SortDirection sortDirection = SortDirection.ASC;
        PagedResult<Category> expectedPagedResult = new PagedResult<>(
                List.of(new Category(1L, "Electronics", "Devices")),
                page,
                size,
                1
        );
        when(categoryPersistencePort.getAllCategories(page, size, sortDirection)).thenReturn(expectedPagedResult);
        PagedResult<Category> actualPagedResult = findAllCategoriesUseCase.getAllCategories(page, size, sortDirection);
        assertEquals(expectedPagedResult, actualPagedResult);
    }

    @Test
    @DisplayName("Debería devolver un resultado vacío cuando no existen categorías")
    void shouldReturnEmptyResultWhenNoCategoriesExist() {
        int page = 0;
        int size = 10;
        SortDirection sortDirection = SortDirection.ASC;

        PagedResult<Category> expectedPagedResult = new PagedResult<>(
                List.of(),
                page,
                size,
                0
        );

        when(categoryPersistencePort.getAllCategories(page, size, sortDirection)).thenReturn(expectedPagedResult);

        PagedResult<Category> actualPagedResult = findAllCategoriesUseCase.getAllCategories(page, size, sortDirection);

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

        List<Category> categories = List.of(
                new Category(2L, "Zoology", "Study of animals"),
                new Category(1L, "Electronics", "Devices")
        );
        PagedResult<Category> expectedPagedResult = new PagedResult<>(
                categories,
                page,
                size,
                categories.size()
        );

        when(categoryPersistencePort.getAllCategories(page, size, sortDirection)).thenReturn(expectedPagedResult);

        PagedResult<Category> actualPagedResult = findAllCategoriesUseCase.getAllCategories(page, size, sortDirection);

        assertEquals(expectedPagedResult, actualPagedResult);
        assertEquals(categories, actualPagedResult.getContent());
    }

    @Test
    @DisplayName("Debería llamar al puerto de persistencia con los parámetros correctos")
    void shouldCallPersistencePortWithCorrectParameters() {
        int page = 1;
        int size = 20;
        SortDirection sortDirection = SortDirection.DESC;

        findAllCategoriesUseCase.getAllCategories(page, size, sortDirection);

        verify(categoryPersistencePort, times(1)).getAllCategories(page, size, sortDirection);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el puerto de persistencia falla")
    void shouldThrowExceptionWhenPersistencePortFails() {

        int page = 1;
        int size = 10;
        SortDirection sortDirection = SortDirection.ASC;

        doThrow(new RuntimeException("Persistence error")).when(categoryPersistencePort).getAllCategories(page, size, sortDirection);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            findAllCategoriesUseCase.getAllCategories(page, size, sortDirection);
        });
        assertEquals("Persistence error", exception.getMessage());
    }
}

