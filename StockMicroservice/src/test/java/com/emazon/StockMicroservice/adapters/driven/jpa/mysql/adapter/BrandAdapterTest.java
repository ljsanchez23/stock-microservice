package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BrandAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private IBrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandAdapter brandAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brandAdapter = new BrandAdapter(brandRepository, brandEntityMapper);
    }

    @Test
    @DisplayName("Should create the brand correctly")
    void shouldSaveBrand() {
        Brand brand = new Brand(1L, "Adidas", "Sportswear");
        BrandEntity brandEntity = new BrandEntity();
        when(brandEntityMapper.toEntity(brand)).thenReturn(brandEntity);

        brandAdapter.saveBrand(brand);

        verify(brandRepository, times(1)).save(brandEntity);
    }

    @Test
    @DisplayName("Should return true if the brand exists by name")
    void shouldReturnTrueIfBrandExistsByName() {
        String brandName = "Adidas";
        when(brandRepository.findByName(brandName)).thenReturn(Optional.of(new BrandEntity()));

        boolean exists = brandAdapter.existsByName(brandName);

        assertTrue(exists);
        verify(brandRepository, times(1)).findByName(brandName);
    }

    @Test
    @DisplayName("Should return a paginated result of brands")
    void shouldReturnPagedResultOfBrands() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1L);
        brandEntity.setName("Adidas");
        brandEntity.setDescription("Sportswear");

        Brand brand = new Brand(1L, "Adidas", "Sportswear");

        when(brandEntityMapper.toDomain(brandEntity)).thenReturn(brand);

        List<BrandEntity> brandEntities = List.of(brandEntity, brandEntity, brandEntity, brandEntity, brandEntity,
                brandEntity, brandEntity, brandEntity, brandEntity, brandEntity);
        Page<BrandEntity> brandPage = new PageImpl<>(brandEntities, PageRequest.of(0, 10), 10);
        when(brandRepository.findAll(any(PageRequest.class))).thenReturn(brandPage);

        PagedResult<Brand> result = brandAdapter.getAllBrands(0, 10, "ASC");

        assertEquals(10, result.getContent().size());
        assertEquals(brand.getId(), result.getContent().get(0).getId());
        assertEquals(brand.getName(), result.getContent().get(0).getName());
        assertEquals(brand.getDescription(), result.getContent().get(0).getDescription());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(10, result.getTotalElements());

        verify(brandRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));
    }
}
