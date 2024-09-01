package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Product;

/**
 * Defines the service operations for products,
 * including saving a product.
 */
public interface IProductServicePort {
    void saveProduct(Product product);
}
