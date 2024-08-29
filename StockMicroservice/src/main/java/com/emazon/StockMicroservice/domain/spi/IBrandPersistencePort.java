package com.emazon.StockMicroservice.domain.spi;

import com.emazon.StockMicroservice.domain.model.Brand;

public interface IBrandPersistencePort {
    void saveBrand (Brand brand);
    boolean existsByName (String name);
}
