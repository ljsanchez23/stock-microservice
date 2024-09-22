package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.api.IProductServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidCategoryException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.exception.InvalidPriceException;
import com.emazon.StockMicroservice.domain.exception.InvalidQuantityException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.spi.IProductPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.Validator;

import java.util.*;
import java.util.stream.Collectors;

public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void saveProduct(Product product) {
        Validator.validateQuantity(product.getQuantity());
        Validator.validatePrice(product.getPrice());
        Validator.validateCategories(product.getCategories());
        Validator.validateBrand(product.getBrand());
        if (productPersistencePort.existsByName(product.getName())) {
            throw new InvalidNameException(Constants.PRODUCT_ALREADY_EXISTS);
        }
        productPersistencePort.saveProduct(product);
    }

    @Override
    public PagedResult<Product> listProducts(Integer page, Integer size, String sortDirection, String sort) {
        int defaultPage = Constants.DEFAULT_PAGE;
        int defaultSize = Constants.DEFAULT_SIZE;
        String defaultSortDirection = Constants.DEFAULT_DIRECTION;
        String defaultSortField = Constants.DEFAULT_SORT_FIELD;

        int actualPage = (page != null) ? page : defaultPage;
        int actualSize = (size != null) ? size : defaultSize;
        String actualSortDirection = (sortDirection != null) ? sortDirection : defaultSortDirection;
        String actualSortField = (sort != null) ? sort : defaultSortField;

        String sortField = switch (actualSortField.toLowerCase()) {
            case Constants.CATEGORY -> Constants.CATEGORY_SORTING;
            case Constants.BRAND -> Constants.BRAND_SORTING;
            default -> Constants.DEFAULT_SORTING;
        };

        PagedResult<Product> pagedResult = productPersistencePort.getAllProducts(actualPage, actualSize, actualSortDirection, sortField);

        if (Constants.CATEGORY_SORTING.equals(sortField)) {
            List<Product> sortedProducts = pagedResult.getContent().stream()
                    .sorted(Comparator.comparing(product ->
                            product.getCategories().isEmpty() ? "" :
                                    product.getCategories().get(0).getName()))
                    .collect(Collectors.toList());

            if (Constants.DESC_SORT_FIELD.equalsIgnoreCase(actualSortDirection)) {
                Collections.reverse(sortedProducts);
            }
            return new PagedResult<>(sortedProducts, pagedResult.getPage(), pagedResult.getSize(), pagedResult.getTotalElements());
        }
        return pagedResult;
    }


}
