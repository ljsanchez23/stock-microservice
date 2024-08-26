package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
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
    void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
        Category existingCategory = new Category(1L,"Electronics", "Devices and gadgets");
        when(categoryPersistencePort.existsByName("Electronics")).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> createCategoryUseCase.saveCategory(existingCategory));

        verify(categoryPersistencePort, never()).saveCategory(existingCategory);
    }

    @Test
    void shouldSaveCategoryWhenNameIsUnique() {
        Category newCategory = new Category(1L, "Electronics", "Devices and gadgets");
        when(categoryPersistencePort.existsByName("Electronics")).thenReturn(false);

        createCategoryUseCase.saveCategory(newCategory);

        verify(categoryPersistencePort, times(1)).saveCategory(newCategory);
    }
}
