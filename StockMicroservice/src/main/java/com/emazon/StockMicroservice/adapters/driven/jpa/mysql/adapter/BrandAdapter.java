package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public void saveBrand(Brand brand){
        if (brandRepository.findByName(brand.getName()).isPresent()) {
            throw new InvalidNameException("Brand with the name '" + brand.getName() + "' already exist.");
        }

        brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    public boolean existsByName(String name) {
        return brandRepository.findByName(name).isPresent();
    }
}
