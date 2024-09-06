package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IProductRepository;
import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.spi.IProductPersistencePort;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class ProductAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product) {
        productRepository.save(productEntityMapper.toEntity(product));
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.findByName(name).isPresent();
    }

    @Override
    public PagedResult<Product> getAllProducts(Integer page, Integer size, String sortDirection, String sortField) {
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortField));
        Page<ProductEntity> productPage = productRepository.findAll(pageRequest);
        List<Product> products = productPage.getContent()
                .stream()
                .map(productEntityMapper::toDomain)
                .toList();
        return new PagedResult<>(products, productPage.getNumber(), productPage.getSize(), productPage.getTotalElements());
    }
}
