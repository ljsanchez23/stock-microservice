package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.StockMicroservice.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryEntityMapper {
    CategoryEntity toEntity(Category category);
    List<Category> toDomainList(List<CategoryEntity> categoryEntities);
    Category toDomain(CategoryEntity categoryEntity);
}
