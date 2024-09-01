package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddProductRequest;
import com.emazon.StockMicroservice.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Maps AddProductRequest DTOs to Product domain models.
 */
@Mapper(componentModel = "spring")
public interface IProductRequestMapper {
    /**
     * Maps AddProductRequest to Product model.
     *
     * @param addProductRequest the request DTO for adding a product
     * @return the Product domain model
     */
    @Mapping(target = "id", ignore = true) // This mapping ignores the ID field in the target
    Product toModel(AddProductRequest addProductRequest);
}
