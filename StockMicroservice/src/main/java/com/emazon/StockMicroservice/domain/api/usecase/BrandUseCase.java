package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.IBrandServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.Validator;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase (IBrandPersistencePort brandPersistencePort){
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand){
        Validator.validateBrand(brand.getName(), brand.getDescription());
        if(brandPersistencePort.existsByName(brand.getName())){
            throw new InvalidNameException(Constants.BRAND_ALREADY_EXISTS);
        }
        brandPersistencePort.saveBrand(brand);
    }

    @Override
    public PagedResult<Brand> listBrands(Integer page, Integer size, String sortDirection) {
        int defaultPage = Constants.DEFAULT_PAGE;
        int defaultSize = Constants.DEFAULT_SIZE;
        String defaultSortDirection = Constants.DEFAULT_DIRECTION;

        int actualPage = (page != null) ? page : defaultPage;
        int actualSize = (size != null) ? size : defaultSize;
        String actualSortDirection = (sortDirection != null) ? sortDirection : defaultSortDirection;

        return brandPersistencePort.getAllBrands(actualPage, actualSize, actualSortDirection);
    }

}
