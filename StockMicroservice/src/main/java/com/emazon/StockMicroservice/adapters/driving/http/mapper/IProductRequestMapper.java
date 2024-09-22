package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddProductRequest;
import com.emazon.StockMicroservice.adapters.driving.http.dto.response.ProductResponse;
import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import com.emazon.StockMicroservice.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = AdapConstants.SPRING)
public interface IProductRequestMapper {
    @Mapping(target = AdapConstants.ID, ignore = true)
    Product toModel(AddProductRequest addProductRequest);
    @Mapping(target = AdapConstants.ID, source = AdapConstants.ID)
    @Mapping(target = AdapConstants.NAME, source = AdapConstants.NAME)
    ProductResponse toResponse(Product product);
}
