package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Brand;

public interface ICreateBrandServicePort {
    void saveBrand(Brand brand);
}
