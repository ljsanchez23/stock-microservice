package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.IFindAllBrandsServicePort;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

public class FindAllBrandsUseCase implements IFindAllBrandsServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public FindAllBrandsUseCase(IBrandPersistencePort brandPersistencePort){
        this.brandPersistencePort = brandPersistencePort;
    }
    @Override
    public PagedResult<Brand> getAllBrands(int page, int size, SortDirection sortDirection) {
        return brandPersistencePort.getAllBrands(page, size, sortDirection);
    }
}
