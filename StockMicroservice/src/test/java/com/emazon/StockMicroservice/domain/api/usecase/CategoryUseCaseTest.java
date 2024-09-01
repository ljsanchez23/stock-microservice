package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {
    private CategoryUseCase categoryUseCase;
    private ICategoryPersistencePort categoryPersistencePort;

    @BeforeEach
    void setUp() {
        categoryPersistencePort = mock(ICategoryPersistencePort.class);
        categoryUseCase = new CategoryUseCase(categoryPersistencePort);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre de la categoría ya existe")
    void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
        Category existingCategory = new Category(1L,"Electronics", "Devices and gadgets");
        when(categoryPersistencePort.existsByName("Electronics")).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> categoryUseCase.saveCategory(existingCategory));

        verify(categoryPersistencePort, never()).saveCategory(existingCategory);
    }

    @Test
    @DisplayName("Debería guardar la categoría cuando el nombre es único")
    void shouldSaveCategoryWhenNameIsUnique() {
        Category newCategory = new Category(1L, "Electronics", "Devices and gadgets");
        when(categoryPersistencePort.existsByName("Electronics")).thenReturn(false);

        categoryUseCase.saveCategory(newCategory);

        verify(categoryPersistencePort, times(1)).saveCategory(newCategory);
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
        PagedResult<Category> actualPagedResult = categoryUseCase.listCategories(page, size, sortDirection);
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

        PagedResult<Category> actualPagedResult = categoryUseCase.listCategories(page, size, sortDirection);

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

        PagedResult<Category> actualPagedResult = categoryUseCase.listCategories(page, size, sortDirection);

        assertEquals(expectedPagedResult, actualPagedResult);
        assertEquals(categories, actualPagedResult.getContent());
    }

    @Test
    @DisplayName("Debería llamar al puerto de persistencia con los parámetros correctos")
    void shouldCallPersistencePortWithCorrectParameters() {
        int page = 1;
        int size = 20;
        SortDirection sortDirection = SortDirection.DESC;

        categoryUseCase.listCategories(page, size, sortDirection);

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
            categoryUseCase.listCategories(page, size, sortDirection);
        });
        assertEquals("Persistence error", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre de la categoría es nulo o vacío")
    void shouldThrowExceptionWhenCategoryNameIsNullOrBlank() {
        assertThrows(InvalidNameException.class, () -> categoryUseCase.validateCategory(null, "Valid description"));
        assertThrows(InvalidNameException.class, () -> categoryUseCase.validateCategory("", "Valid description"));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre de la categoría excede el tamaño máximo permitido")
    void shouldThrowExceptionWhenCategoryNameExceedsMaxLength() {
        String longName = "A".repeat(Constants.NAME_MAX_LENGTH + 1);
        assertThrows(InvalidNameException.class, () -> categoryUseCase.validateCategory(longName, "Valid description"));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la descripción de la categoría es nula o vacía")
    void shouldThrowExceptionWhenCategoryDescriptionIsNullOrBlank() {
        assertThrows(InvalidDescriptionException.class, () -> categoryUseCase.validateCategory("Valid name", null));
        assertThrows(InvalidDescriptionException.class, () -> categoryUseCase.validateCategory("Valid name", ""));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la descripción de la categoría excede el tamaño máximo permitido")
    void shouldThrowExceptionWhenCategoryDescriptionExceedsMaxLength() {
        String longDescription = "A".repeat(Constants.CATEGORY_DESCRIPTION_MAX_LENGTH + 1);
        assertThrows(InvalidDescriptionException.class, () -> categoryUseCase.validateCategory("Valid name", longDescription));
    }

    @Test
    @DisplayName("Debería pasar la validación cuando el nombre y la descripción de la categoría son válidos")
    void shouldPassValidationWhenCategoryNameAndDescriptionAreValid() {
        categoryUseCase.validateCategory("Valid name", "Valid description");
    }
}
