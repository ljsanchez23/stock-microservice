package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryAdapter categoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryAdapter = new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    @DisplayName("Should save the category correctly")
    void shouldSaveCategory() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);

        categoryAdapter.saveCategory(category);

        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    @DisplayName("Should return true if the category exists by name")
    void shouldReturnTrueIfCategoryExistsByName() {
        String categoryName = "Electronics";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(new CategoryEntity()));

        boolean exists = categoryAdapter.existsByName(categoryName);

        assertTrue(exists);
        verify(categoryRepository, times(1)).findByName(categoryName);
    }

    @Test
    @DisplayName("Should return a paginated result of categories")
    void shouldReturnPagedResultOfCategories() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Electronics");
        categoryEntity.setDescription("Devices and gadgets");

        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        when(categoryEntityMapper.toDomain(categoryEntity)).thenReturn(category);

        List<CategoryEntity> categoryEntities = List.of(categoryEntity, categoryEntity, categoryEntity, categoryEntity, categoryEntity,
                categoryEntity, categoryEntity, categoryEntity, categoryEntity, categoryEntity);
        Page<CategoryEntity> categoryPage = new PageImpl<>(categoryEntities, PageRequest.of(0, 10), 10);
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(categoryPage);

        PagedResult<Category> result = categoryAdapter.getAllCategories(0, 10, "ASC");

        assertEquals(10, result.getContent().size());
        assertEquals(category.getId(), result.getContent().get(0).getId());
        assertEquals(category.getName(), result.getContent().get(0).getName());
        assertEquals(category.getDescription(), result.getContent().get(0).getDescription());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(10, result.getTotalElements());

        verify(categoryRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));
    }
}
