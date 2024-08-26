package com.emazon.StockMicroservice.domain.spi;

import com.emazon.StockMicroservice.domain.model.Category;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean existsByName(String name);

}
