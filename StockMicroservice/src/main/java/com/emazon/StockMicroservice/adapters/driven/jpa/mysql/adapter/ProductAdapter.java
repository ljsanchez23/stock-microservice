package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IProductRepository;
import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;

/**
 * Adapter for managing products in the database using JPA.
 */
@RequiredArgsConstructor
public class ProductAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product){
        productRepository.save(productEntityMapper.toEntity(product));
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.findByName(name).isPresent();
    }
}
