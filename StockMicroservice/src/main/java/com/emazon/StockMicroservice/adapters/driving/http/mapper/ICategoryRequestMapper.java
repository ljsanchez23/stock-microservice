package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    Category toModel(AddCategoryRequest addCategoryRequest);
}
