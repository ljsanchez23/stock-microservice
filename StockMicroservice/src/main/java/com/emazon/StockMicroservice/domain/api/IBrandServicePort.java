package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.util.PagedResult;

public interface IBrandServicePort {
    void saveBrand(Brand brand);
    PagedResult<Brand> listBrands(Integer page, Integer size, String sortDirection);
}
