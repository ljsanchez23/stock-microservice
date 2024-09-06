package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.StockMicroservice.domain.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductEntityMapper {
    ProductEntity toEntity(Product product);
    List<Product> toDomainList(List<ProductEntity> productEntities);
    Product toDomain(ProductEntity productEntity);
}
