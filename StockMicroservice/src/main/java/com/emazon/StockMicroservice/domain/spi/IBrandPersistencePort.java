package com.emazon.StockMicroservice.domain.spi;

import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

/**
 * Defines persistence operations for brands,
 * including saving, checking existence by name,
 * and listing with pagination and sorting.
 */
public interface IBrandPersistencePort {
    void saveBrand (Brand brand);
    boolean existsByName (String name);
    PagedResult<Brand> getAllBrands(int page, int size, SortDirection sortDirection);
}
