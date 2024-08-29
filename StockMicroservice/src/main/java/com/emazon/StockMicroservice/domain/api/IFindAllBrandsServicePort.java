package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

public interface IFindAllBrandsServicePort {
    PagedResult<Brand> getAllBrands(int page, int size, SortDirection sortDirection);
}
