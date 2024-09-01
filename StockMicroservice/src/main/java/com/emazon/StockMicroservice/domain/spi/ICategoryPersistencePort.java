package com.emazon.StockMicroservice.domain.spi;

import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

/**
 * Defines persistence operations for categories,
 * including saving, checking existence by name,
 * and listing with pagination and sorting.
 */
public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean existsByName(String name);
    PagedResult<Category> getAllCategories(int page, int size, SortDirection sortDirection);
}
