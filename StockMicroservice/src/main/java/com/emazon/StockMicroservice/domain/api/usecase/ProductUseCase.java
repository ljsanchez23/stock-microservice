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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles business logic for products, including validation and persistence.
 */
public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    /**
     * Saves a product after validating its attributes.
     *
     * @param product the product to be saved
     */
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

    /**
     * Validation methods to ensure that the product attributes meet the required rules:
     * - Quantity must be non-negative.
     * - Price must be non-negative.
     * - Categories must not be empty, have duplicates, or exceed the maximum limit.
     * - A product must have an associated brand.
     */
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
