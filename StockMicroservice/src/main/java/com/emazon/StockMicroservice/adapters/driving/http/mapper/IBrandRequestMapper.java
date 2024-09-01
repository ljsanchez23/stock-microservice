package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.StockMicroservice.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Maps AddBrandRequest DTOs to Brand domain models.
 */
@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {
    /**
     * Maps AddBrandRequest to Brand model.
     *
     * @param addBrandRequest the request DTO for adding a brand
     * @return the Brand domain model
     */
    @Mapping(target = "id", ignore = true) // This mapping ignores the ID field in the target
    Brand toModel(AddBrandRequest addBrandRequest);
}
