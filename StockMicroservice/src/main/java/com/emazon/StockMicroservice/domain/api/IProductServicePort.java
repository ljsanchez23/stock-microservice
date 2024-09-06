package com.emazon.StockMicroservice.domain.api;

import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.util.PagedResult;

public interface IProductServicePort {
    void saveProduct(Product product);
    PagedResult<Product> listProducts(Integer page, Integer size, String sortDirection, String sort);
}
