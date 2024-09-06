package com.emazon.StockMicroservice.configuration;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter.ProductAdapter;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter.BrandAdapter;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IProductRepository;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.StockMicroservice.domain.api.*;
import com.emazon.StockMicroservice.domain.api.usecase.*;
import com.emazon.StockMicroservice.domain.spi.IProductPersistencePort;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }
    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandAdapter(brandRepository, brandEntityMapper);
    }
    @Bean
    public IBrandServicePort brandServicePort(){
        return new BrandUseCase(brandPersistencePort());
    }

    @Bean
    public IProductPersistencePort productPersistencePort(){
        return new ProductAdapter(productRepository, productEntityMapper);
    }
    @Bean
    public IProductServicePort articleServicePort(){
        return new ProductUseCase(productPersistencePort());
    }
}
