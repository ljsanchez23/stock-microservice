package com.emazon.StockMicroservice.domain.spi;

import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean existsByName(String name);
    PagedResult<Category> getAllCategories(Integer page, Integer size, String sortDirection);
}
