package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.ICreateBrandServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;

public class CreateBrandUseCase implements ICreateBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public CreateBrandUseCase (IBrandPersistencePort brandPersistencePort){
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand){
        if(brandPersistencePort.existsByName(brand.getName())){
            throw new InvalidNameException("Brand with name '" + brand.getName() + "' already exist.");
        }
        brandPersistencePort.saveBrand(brand);
    }
}
