package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Category;

public interface ICreateCategoryServicePort {
    void saveCategory(Category category);
}
