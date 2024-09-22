package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import com.emazon.StockMicroservice.domain.model.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = AdapConstants.SPRING)
public interface IBrandEntityMapper {
    BrandEntity toEntity(Brand brand);
    List<Brand> toDomainList(List<BrandEntity> brandEntities);
    Brand toDomain(BrandEntity brandEntity);
}
