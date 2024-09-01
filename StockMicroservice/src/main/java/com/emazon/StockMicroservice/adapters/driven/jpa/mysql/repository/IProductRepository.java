package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Product entities.
 */
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByName(String name);
}
