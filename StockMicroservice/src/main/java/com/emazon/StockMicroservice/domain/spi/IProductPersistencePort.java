package com.emazon.StockMicroservice.domain.spi;

import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.util.PagedResult;

public interface IProductPersistencePort {
    void saveProduct(Product product);
    boolean existsByName(String name);
    PagedResult<Product> getAllProducts(Integer page, Integer size, String sortDirection, String sort);
}
