package com.emazon.StockMicroservice.domain.spi;

import com.emazon.StockMicroservice.domain.model.Product;

/**
 * Defines persistence operations for products,
 * including saving and checking existence by name.
 */
public interface IProductPersistencePort {
    void saveProduct(Product product);
    boolean existsByName(String name);
}
