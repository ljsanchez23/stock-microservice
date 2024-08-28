package com.emazon.StockMicroservice.domain.api;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

public interface IFindAllCategoriesServicePort {
    PagedResult<Category> getAllCategories(int page, int size, SortDirection sortDirection);
}
