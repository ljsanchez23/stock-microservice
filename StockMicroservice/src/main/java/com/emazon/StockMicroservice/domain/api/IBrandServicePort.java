package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

/**
 * Defines the service operations for brands,
 * including saving a brand and listing brands
 * with pagination and sorting.
 */
public interface IBrandServicePort {
    void saveBrand(Brand brand);
    PagedResult<Brand> listBrands(int page, int size, SortDirection sortDirection);
}
