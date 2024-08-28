package com.emazon.StockMicroservice.configuration;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.StockMicroservice.domain.api.ICreateCategoryServicePort;
import com.emazon.StockMicroservice.domain.api.IFindAllCategoriesServicePort;
import com.emazon.StockMicroservice.domain.api.usecase.CreateCreateCategoryUseCase;
import com.emazon.StockMicroservice.domain.api.usecase.FindAllCategoriesUseCase;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }
    @Bean
    public ICreateCategoryServicePort categoryServicePort(){
        return new CreateCreateCategoryUseCase(categoryPersistencePort());
    }
    @Bean
    public IFindAllCategoriesServicePort findAllCategoriesServicePort(){
        return new FindAllCategoriesUseCase(categoryPersistencePort());
    }
}
