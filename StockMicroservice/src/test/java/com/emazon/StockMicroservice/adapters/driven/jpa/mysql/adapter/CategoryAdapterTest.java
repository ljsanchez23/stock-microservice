package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CategoryAdapterTest {
    private CategoryAdapter categoryAdapter;
    private ICategoryRepository categoryRepository;
    private ICategoryEntityMapper categoryEntityMapper;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(ICategoryRepository.class);
        categoryEntityMapper = mock(ICategoryEntityMapper.class);
        categoryAdapter = new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
        Category category = new Category(1L,"Electronics", "Devices and gadgets");
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(new CategoryEntity()));

        assertThrows(InvalidNameException.class, () -> categoryAdapter.saveCategory(category));

        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }

    @Test
    void shouldSaveCategoryWhenNameIsUnique() {
        Category category = new Category(1L,"Electronics", "Devices and gadgets");
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.empty());
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);

        categoryAdapter.saveCategory(category);

        verify(categoryRepository, times(1)).save(categoryEntity);
    }
}
