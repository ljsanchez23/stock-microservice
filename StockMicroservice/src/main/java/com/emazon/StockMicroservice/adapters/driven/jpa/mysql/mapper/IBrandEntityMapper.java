package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.StockMicroservice.domain.model.Brand;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper interface for converting between Brand domain model and BrandEntity.
 */
@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {
    BrandEntity toEntity(Brand brand);
    List<Brand> toDomainList(List<BrandEntity> brandEntities);
    Brand toDomain(BrandEntity brandEntity);
}
