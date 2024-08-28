package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.IFindAllCategoriesServicePort;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import com.emazon.StockMicroservice.domain.util.SortDirection;

public class FindAllCategoriesUseCase implements IFindAllCategoriesServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public FindAllCategoriesUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public PagedResult<Category> getAllCategories(int page, int size, SortDirection sortDirection) {
        return categoryPersistencePort.getAllCategories(page, size, sortDirection);
    }
}
