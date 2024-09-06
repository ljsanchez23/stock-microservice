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
import com.emazon.StockMicroservice.domain.util.PagedResult;

import java.util.*;
import java.util.stream.Collectors;

public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void saveProduct(Product product) {
        validateQuantity(product.getQuantity());
        validatePrice(product.getPrice());
        validateCategories(product.getCategories());
        validateBrand(product.getBrand());
        if (productPersistencePort.existsByName(product.getName())) {
            throw new InvalidNameException("Product with the name '" + product.getName() + "' already exists.");
        }
        productPersistencePort.saveProduct(product);
    }

    @Override
    public PagedResult<Product> listProducts(Integer page, Integer size, String sortDirection, String sort) {
        int defaultPage = 0;
        int defaultSize = 10;
        String defaultSortDirection = "ASC";
        String defaultSortField = "product";

        int actualPage = (page != null) ? page : defaultPage;
        int actualSize = (size != null) ? size : defaultSize;
        String actualSortDirection = (sortDirection != null) ? sortDirection : defaultSortDirection;
        String actualSortField = (sort != null) ? sort : defaultSortField;

        String sortField = switch (actualSortField.toLowerCase()) {
            case "category" -> "categories.name";
            case "brand" -> "brand.name";
            default -> "name";
        };

        PagedResult<Product> pagedResult = productPersistencePort.getAllProducts(actualPage, actualSize, actualSortDirection, sortField);

        if ("categories.name".equals(sortField)) {
            List<Product> sortedProducts = pagedResult.getContent().stream()
                    .sorted(Comparator.comparing(product ->
                            product.getCategories().isEmpty() ? "" :
                                    product.getCategories().get(0).getName()))
                    .collect(Collectors.toList());

            if ("desc".equalsIgnoreCase(actualSortDirection)) {
                Collections.reverse(sortedProducts);
            }
            return new PagedResult<>(sortedProducts, pagedResult.getPage(), pagedResult.getSize(), pagedResult.getTotalElements());
        }
        return pagedResult;
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new InvalidQuantityException("Quantity cannot be negative.");
        }
    }
    private void validatePrice(double price) {
        if (price < 0) {
            throw new InvalidPriceException("Price cannot be negative.");
        }
    }
    private void validateCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new InvalidCategoryException("At least one category must be associated.");
        }
        if (categories.size() > 3) {
            throw new InvalidCategoryException("An article cannot have more than 3 categories.");
        }
        Set<Category> categorySet = new HashSet<>(categories);
        if (categorySet.size() != categories.size()) {
            throw new InvalidCategoryException("Categories cannot be duplicated.");
        }
    }
    private void validateBrand(Brand brand) {
        if (brand == null) {
            throw new IllegalArgumentException("An article must have a brand associated.");
        }
    }
}
