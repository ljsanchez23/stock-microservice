package com.emazon.StockMicroservice.domain.spi;

import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

public interface IBrandPersistencePort {
    void saveBrand (Brand brand);
    boolean existsByName (String name);
    PagedResult<Brand> getAllBrands(int page, int size, SortDirection sortDirection);
}
