package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    @Override
    public PagedResult<Category> getAllCategories(Integer page, Integer size, String sortDirection) {
        Sort.Direction direction = AdapConstants.DESC.equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, AdapConstants.NAME));
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageRequest);
        List<Category> categories = categoryPage.getContent()
                .stream()
                .map(categoryEntityMapper::toDomain)
                .toList();
        return new PagedResult<>(categories, categoryPage.getNumber(), categoryPage.getSize(), categoryPage.getTotalElements());
    }
}
