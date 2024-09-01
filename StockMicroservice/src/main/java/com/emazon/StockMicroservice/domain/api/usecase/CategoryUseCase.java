package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.ICategoryServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

/**
 * Handles business logic for categories, including validation and persistence.
 */
public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }

    /**
     * Saves a category after validating its attributes.
     *
     * @param category the category to be saved
     */
    @Override
    public void saveCategory(Category category) {
        validateCategory(category.getName(), category.getDescription());
        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new InvalidNameException("Category with name '" + category.getName() + "' already exist.");
        }
        categoryPersistencePort.saveCategory(category);
    }

    /**
     * Lists categories with pagination and sorting.
     *
     * @param page the page number
     * @param size the number of items per page
     * @param sortDirection the direction to sort the categories
     * @return a paginated result of categories
     */
    @Override
    public PagedResult<Category> listCategories(int page, int size, SortDirection sortDirection) {
        return categoryPersistencePort.getAllCategories(page, size, sortDirection);
    }

    /**
     * Validation methods to ensure that category attributes meet the required rules:
     * - Name must not be null, blank, or exceed the maximum length.
     * - Description must not be null, blank, or exceed the maximum length.
     */
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
