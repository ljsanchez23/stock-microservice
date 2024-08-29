package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.StockMicroservice.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IBrandRequestMapper {
    static Brand toModel(AddBrandRequest addBrandRequest) {
        return new Brand(
                addBrandRequest.getId(),
                addBrandRequest.getName(),
                addBrandRequest.getDescription());
    }

    @Mapping(target = "id", ignore = true)
    Brand addBrand(AddBrandRequest addBrandRequest);
}
