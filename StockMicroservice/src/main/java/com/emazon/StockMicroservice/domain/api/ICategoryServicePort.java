package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;

public interface ICategoryServicePort {
    void saveCategory(Category category);
    PagedResult<Category> listCategories(Integer page, Integer size, String sortDirection);
}
