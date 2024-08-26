package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ICategoryRequestMapper {

    static Category toModel(AddCategoryRequest addCategoryRequest) {
        return new Category(
                addCategoryRequest.getId(),
                addCategoryRequest.getName(),
                addCategoryRequest.getDescription());
    }

    @Mapping(target = "id", ignore = true)
    Category addCategory(AddCategoryRequest addCategoryRequest);
}
