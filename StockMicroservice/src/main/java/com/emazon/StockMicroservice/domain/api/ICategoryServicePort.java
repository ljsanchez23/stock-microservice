package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

/**
 * Defines the service operations for categories,
 * including saving a category and listing categories
 * with pagination and sorting.
 */
public interface ICategoryServicePort {
    void saveCategory(Category category);
    PagedResult<Category> listCategories(int page, int size, SortDirection sortDirection);
}
