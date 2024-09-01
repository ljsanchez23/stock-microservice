package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Maps AddCategoryRequest DTOs to Category domain models.
 */
@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {

    /**
     * Maps AddCategoryRequest to Category model.
     *
     * @param addCategoryRequest the request DTO for adding a category
     * @return the Category domain model
     */
    @Mapping(target = "id", ignore = true) // This mapping ignores the ID field in the target
    Category toModel(AddCategoryRequest addCategoryRequest);
}
