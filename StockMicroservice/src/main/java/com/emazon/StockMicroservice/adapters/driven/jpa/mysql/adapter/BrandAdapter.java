package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public void saveBrand(Brand brand){
        brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    public boolean existsByName(String name) {
        return brandRepository.findByName(name).isPresent();
    }

    @Override
    public PagedResult<Brand> getAllBrands(Integer page, Integer size, String sortDirection) {
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, "name"));
        Page<BrandEntity> brandPage = brandRepository.findAll(pageRequest);
        List<Brand> brands = brandPage.getContent()
                .stream()
                .map(brandEntityMapper::toDomain)
                .toList();
        return new PagedResult<>(brands, brandPage.getNumber(), brandPage.getSize(), brandPage.getTotalElements());
    }
}
