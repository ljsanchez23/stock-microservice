package com.emazon.StockMicroservice.domain.spi;

import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.util.PagedResult;

public interface IBrandPersistencePort {
    void saveBrand (Brand brand);
    boolean existsByName (String name);
    PagedResult<Brand> getAllBrands(Integer page, Integer size, String sortDirection);
}
