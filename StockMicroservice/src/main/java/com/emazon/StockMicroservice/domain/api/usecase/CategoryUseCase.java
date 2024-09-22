package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.ICategoryServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.Validator;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        Validator.validateCategory(category.getName(), category.getDescription());
        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new InvalidNameException(Constants.CATEGORY_ALREADY_EXISTS);
        }
        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PagedResult<Category> listCategories(Integer page, Integer size, String sortDirection) {
        int defaultPage = Constants.DEFAULT_PAGE;
        int defaultSize = Constants.DEFAULT_SIZE;
        String defaultSortDirection = Constants.DEFAULT_DIRECTION;

        int actualPage = (page != null) ? page : defaultPage;
        int actualSize = (size != null) ? size : defaultSize;
        String actualSortDirection = (sortDirection != null) ? sortDirection : defaultSortDirection;

        return categoryPersistencePort.getAllCategories(actualPage, actualSize, actualSortDirection);
    }

}
