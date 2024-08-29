package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CreateCategoryUseCaseTest {
    private CreateCategoryUseCase createCategoryUseCase;
    private ICategoryPersistencePort categoryPersistencePort;

    @BeforeEach
    void setUp() {
        categoryPersistencePort = mock(ICategoryPersistencePort.class);
        createCategoryUseCase = new CreateCategoryUseCase(categoryPersistencePort);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre de la categoría ya existe")
    void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
        Category existingCategory = new Category(1L,"Electronics", "Devices and gadgets");
        when(categoryPersistencePort.existsByName("Electronics")).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> createCategoryUseCase.saveCategory(existingCategory));

        verify(categoryPersistencePort, never()).saveCategory(existingCategory);
    }

    @Test
    @DisplayName("Debería guardar la categoría cuando el nombre es único")
    void shouldSaveCategoryWhenNameIsUnique() {
        Category newCategory = new Category(1L, "Electronics", "Devices and gadgets");
        when(categoryPersistencePort.existsByName("Electronics")).thenReturn(false);

        createCategoryUseCase.saveCategory(newCategory);

        verify(categoryPersistencePort, times(1)).saveCategory(newCategory);
    }
}
