package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save the category when the inputs are valid ")
    void shouldSaveCategoryWhenValid() {
        Category category = new Category(1L, "Valid Name", "Valid Description");
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(false);

        categoryUseCase.saveCategory(category);

        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    @DisplayName("Should throw exception when the name already exists")
    void shouldThrowExceptionWhenNameAlreadyExists() {
        Category category = new Category(1L, "Duplicate Name", "Valid Description");
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    @DisplayName("Should return a paginated result of categories")
    void shouldReturnPagedResult() {
        PagedResult<Category> expectedPagedResult = new PagedResult<>(Collections.emptyList(), 0, 10, 0L);
        when(categoryPersistencePort.getAllCategories(0, 10, "ASC")).thenReturn(expectedPagedResult);

        PagedResult<Category> result = categoryUseCase.listCategories(0, 10, "ASC");

        assertEquals(expectedPagedResult, result);
        verify(categoryPersistencePort, times(1)).getAllCategories(0, 10, "ASC");
    }

    @Test
    @DisplayName("Should throw exception when the name is not given")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(InvalidNameException.class, () -> categoryUseCase.validateCategory(null, "Valid Description"));
    }

    @Test
    @DisplayName("Should throw exception when the name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(InvalidNameException.class, () -> categoryUseCase.validateCategory(" ", "Valid Description"));
    }

    @Test
    @DisplayName("Should throw exception when the name is too long")
    void shouldThrowExceptionWhenNameIsTooLong() {
        String longName = "A".repeat(Constants.NAME_MAX_LENGTH + 1);

        assertThrows(InvalidNameException.class, () -> categoryUseCase.validateCategory(longName, "Valid Description"));
    }

    @Test
    @DisplayName("Should throw exception when the description is not given")
    void shouldThrowExceptionWhenDescriptionIsNull() {
        assertThrows(InvalidDescriptionException.class, () -> categoryUseCase.validateCategory("Valid Name", null));
    }

    @Test
    @DisplayName("Should throw exception when the description is blank")
    void shouldThrowExceptionWhenDescriptionIsBlank() {
        assertThrows(InvalidDescriptionException.class, () -> categoryUseCase.validateCategory("Valid Name", " "));
    }

    @Test
    @DisplayName("Should throw exception when the description is too long")
    void shouldThrowExceptionWhenDescriptionIsTooLong() {
        String longDescription = "A".repeat(Constants.CATEGORY_DESCRIPTION_MAX_LENGTH + 1);
        assertThrows(InvalidDescriptionException.class, () -> categoryUseCase.validateCategory("Valid Name", longDescription));
    }
}
