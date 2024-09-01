package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.IBrandServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;

/**
 * Handles business logic for brands, including validation and persistence.
 */
public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase (IBrandPersistencePort brandPersistencePort){
        this.brandPersistencePort = brandPersistencePort;
    }

    /**
     * Saves a brand after validating its attributes.
     *
     * @param brand the brand to be saved
     */
    @Override
    public void saveBrand(Brand brand){
        validateBrand(brand.getName(), brand.getDescription());
        if(brandPersistencePort.existsByName(brand.getName())){
            throw new InvalidNameException("Brand with name '" + brand.getName() + "' already exist.");
        }
        brandPersistencePort.saveBrand(brand);
    }

    /**
     * Lists brands with pagination and sorting.
     *
     * @param page the page number
     * @param size the number of items per page
     * @param sortDirection the direction to sort the brands
     * @return a paginated result of brands
     */
    @Override
    public PagedResult<Brand> listBrands(int page, int size, SortDirection sortDirection) {
        return brandPersistencePort.getAllBrands(page, size, sortDirection);
    }

    /**
     * Validation methods to ensure that brand attributes meet the required rules:
     * - Name must not be null, blank, or exceed the maximum length.
     * - Description must not be null, blank, or exceed the maximum length.
     */
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
