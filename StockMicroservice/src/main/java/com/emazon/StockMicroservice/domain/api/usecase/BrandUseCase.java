package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.IBrandServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase (IBrandPersistencePort brandPersistencePort){
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand){
        validateBrand(brand.getName(), brand.getDescription());
        if(brandPersistencePort.existsByName(brand.getName())){
            throw new InvalidNameException("Brand with name '" + brand.getName() + "' already exist.");
        }
        brandPersistencePort.saveBrand(brand);
    }

    @Override
    public PagedResult<Brand> listBrands(Integer page, Integer size, String sortDirection) {
        int defaultPage = 0;
        int defaultSize = 10;
        String defaultSortDirection = "ASC";

        int actualPage = (page != null) ? page : defaultPage;
        int actualSize = (size != null) ? size : defaultSize;
        String actualSortDirection = (sortDirection != null) ? sortDirection : defaultSortDirection;

        return brandPersistencePort.getAllBrands(actualPage, actualSize, actualSortDirection);
    }

    public void validateBrand (String name, String description){
        if (name == null || name.isBlank()) {
            throw new InvalidNameException("Name cannot be null or blank.");
        }
        if (name.length() > Constants.NAME_MAX_LENGTH) {
            throw new InvalidNameException("Name must be less than 50 characters.");
        }
        if (description == null || description.isBlank()) {
            throw new InvalidDescriptionException("Description cannot be null or blank.");
        }
        if (description.length() > Constants.BRAND_DESCRIPTION_MAX_LENGTH) {
            throw new InvalidDescriptionException("Description must be less than 120 characters.");
        }
    }
}
