package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.StockMicroservice.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Debería guardar la categoría cuando el nombre es único")
    void shouldSaveCategoryWhenNameIsUnique() {
        Category category = new Category(1L,"Electronics", "Devices and gadgets");
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.empty());
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);

        categoryAdapter.saveCategory(category);

        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    @DisplayName("Debería devolver true cuando la categoría existe")
    void shouldReturnTrueWhenCategoryExists() {
        String name = "Electronics";
        when(categoryRepository.findByName(name)).thenReturn(Optional.of(new CategoryEntity()));

        boolean result = categoryAdapter.existsByName(name);

        assertTrue(result, "The category should exist.");
    }

    @Test
    @DisplayName("Debería devolver false cuando la categoría no existe")
    void shouldReturnFalseWhenCategoryDoesNotExist() {
        String name = "Electronics";
        when(categoryRepository.findByName(name)).thenReturn(Optional.empty());

        boolean result = categoryAdapter.existsByName(name);

        assertFalse(result, "The category should not exist.");
    }

    @Test
    @DisplayName("Debería devolver un resultado paginado de categorías")
    void shouldReturnPagedResultOfCategories() {
        int page = 0, size = 10;
        SortDirection sortDirection = SortDirection.ASC;
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Devices");
        Category category = new Category(1L, "Electronics", "Devices");

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<CategoryEntity> categoryPage = new PageImpl<>(List.of(categoryEntity), pageRequest, 1);

        when(categoryRepository.findAll(pageRequest)).thenReturn(categoryPage);
        when(categoryEntityMapper.toDomain(categoryEntity)).thenReturn(category);


        PagedResult<Category> result = categoryAdapter.getAllCategories(page, size, sortDirection);

        assertEquals(1, result.getTotalElements());
        assertEquals(page, result.getPage());
        assertEquals(size, result.getSize());
        assertEquals(List.of(category), result.getContent());

        verify(categoryRepository).findAll(pageRequest);
        verify(categoryEntityMapper).toDomain(categoryEntity);
    }
}
