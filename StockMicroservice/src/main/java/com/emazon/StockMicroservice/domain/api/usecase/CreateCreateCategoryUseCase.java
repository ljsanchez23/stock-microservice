package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.ICreateCategoryServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;

public class CreateCreateCategoryUseCase implements ICreateCategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CreateCreateCategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new InvalidNameException("Category with name '" + category.getName() + "' already exist.");
        }
        categoryPersistencePort.saveCategory(category);
    }
}
