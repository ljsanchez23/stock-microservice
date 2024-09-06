package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddProductRequest;
import com.emazon.StockMicroservice.adapters.driving.http.dto.response.ProductResponse;
import com.emazon.StockMicroservice.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface IProductRequestMapper {
    @Mapping(target = "id", ignore = true)
    Product toModel(AddProductRequest addProductRequest);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductResponse toResponse(Product product);
}
