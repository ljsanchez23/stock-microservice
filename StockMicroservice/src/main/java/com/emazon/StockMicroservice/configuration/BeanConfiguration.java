package com.emazon.StockMicroservice.configuration;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter.BrandAdapter;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.StockMicroservice.domain.api.ICreateBrandServicePort;
import com.emazon.StockMicroservice.domain.api.ICreateCategoryServicePort;
import com.emazon.StockMicroservice.domain.api.IFindAllCategoriesServicePort;
import com.emazon.StockMicroservice.domain.api.usecase.CreateBrandUseCase;
import com.emazon.StockMicroservice.domain.api.usecase.CreateCategoryUseCase;
import com.emazon.StockMicroservice.domain.api.usecase.FindAllCategoriesUseCase;
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

    // Category
    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }
    @Bean
    public ICreateCategoryServicePort categoryServicePort(){
        return new CreateCategoryUseCase(categoryPersistencePort());
    }
    @Bean
    public IFindAllCategoriesServicePort findAllCategoriesServicePort(){
        return new FindAllCategoriesUseCase(categoryPersistencePort());
    }

    // Brand
    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandAdapter(brandRepository, brandEntityMapper);
    }
    @Bean
    public ICreateBrandServicePort brandServicePort(){
        return new CreateBrandUseCase(brandPersistencePort());
    }

}
