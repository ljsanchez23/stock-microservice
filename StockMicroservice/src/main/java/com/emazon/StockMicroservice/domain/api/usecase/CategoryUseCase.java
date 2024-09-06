package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.ICategoryServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        validateCategory(category.getName(), category.getDescription());
        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new InvalidNameException("Category with name '" + category.getName() + "' already exist.");
        }
        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PagedResult<Category> listCategories(Integer page, Integer size, String sortDirection) {
        int defaultPage = 0;
        int defaultSize = 10;
        String defaultSortDirection = "ASC";

        int actualPage = (page != null) ? page : defaultPage;
        int actualSize = (size != null) ? size : defaultSize;
        String actualSortDirection = (sortDirection != null) ? sortDirection : defaultSortDirection;

        return categoryPersistencePort.getAllCategories(actualPage, actualSize, actualSortDirection);
    }

    public void validateCategory(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new InvalidNameException("Name cannot be null or blank.");
        }
        if (name.length() > Constants.NAME_MAX_LENGTH) {
            throw new InvalidNameException("Name must be less than " + Constants.NAME_MAX_LENGTH + " characters.");
        }
        if (description == null || description.isBlank()) {
            throw new InvalidDescriptionException("Description cannot be null or blank.");
        }
        if (description.length() > Constants.CATEGORY_DESCRIPTION_MAX_LENGTH) {
            throw new InvalidDescriptionException("Description must be less than " + Constants.CATEGORY_DESCRIPTION_MAX_LENGTH + " characters.");
        }
    }
}
