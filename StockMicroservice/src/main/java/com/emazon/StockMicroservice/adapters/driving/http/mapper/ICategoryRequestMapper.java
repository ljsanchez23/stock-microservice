package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import com.emazon.StockMicroservice.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = AdapConstants.SPRING)
public interface ICategoryRequestMapper {

    @Mapping(target = AdapConstants.ID, ignore = true)
    Category toModel(AddCategoryRequest addCategoryRequest);
}
