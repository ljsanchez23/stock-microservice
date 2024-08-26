package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Category;

public interface ICategoryServicePort {
    void saveCategory(Category category);
}
