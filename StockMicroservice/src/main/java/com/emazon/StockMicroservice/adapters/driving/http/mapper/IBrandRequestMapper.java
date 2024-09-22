package com.emazon.StockMicroservice.adapters.driving.http.mapper;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import com.emazon.StockMicroservice.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = AdapConstants.SPRING)
public interface IBrandRequestMapper {
    @Mapping(target = AdapConstants.ID, ignore = true)
    Brand toModel(AddBrandRequest addBrandRequest);
}
