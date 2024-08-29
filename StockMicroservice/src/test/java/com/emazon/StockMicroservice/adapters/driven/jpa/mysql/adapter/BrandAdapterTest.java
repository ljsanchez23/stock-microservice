package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class BrandAdapterTest {
    private BrandAdapter brandAdapter;
    private IBrandRepository brandRepository;
    private IBrandEntityMapper brandEntityMapper;

    @BeforeEach
    void setUp() {
        brandRepository = mock(IBrandRepository.class);
        brandEntityMapper = mock(IBrandEntityMapper.class);
        brandAdapter = new BrandAdapter(brandRepository, brandEntityMapper);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre de la marca ya existe")
    void shouldThrowExceptionWhenBrandNameAlreadyExists() {
        Brand brand = new Brand(1L,"Adimas", "Fast as wind");
        when(brandRepository.findByName("Adimas")).thenReturn(Optional.of(new BrandEntity()));

        assertThrows(InvalidNameException.class, () -> brandAdapter.saveBrand(brand));

        verify(brandRepository, never()).save(any(BrandEntity.class));
    }

    @Test
    @DisplayName("Debería guardar la marca cuando el nombre es único")
    void shouldSaveBrandWhenNameIsUnique() {
        Brand brand = new Brand(1L,"Adimas", "Fast as wind");
        when(brandRepository.findByName("Adimas")).thenReturn(Optional.empty());
        BrandEntity brandEntity = new BrandEntity();
        when(brandEntityMapper.toEntity(brand)).thenReturn(brandEntity);

        brandAdapter.saveBrand(brand);

        verify(brandRepository, times(1)).save(brandEntity);
    }

    @Test
    @DisplayName("Debería devolver true cuando la marca existe")
    void shouldReturnTrueWhenBrandExists() {
        String name = "Adimas";
        when(brandRepository.findByName(name)).thenReturn(Optional.of(new BrandEntity()));

        boolean result = brandAdapter.existsByName(name);

        assertTrue(result, "The brand should exist.");
    }

    @Test
    @DisplayName("Debería devolver false cuando la marca no existe")
    void shouldReturnFalseWhenBrandDoesNotExist() {
        String name = "Adimas";
        when(brandRepository.findByName(name)).thenReturn(Optional.empty());

        boolean result = brandAdapter.existsByName(name);

        assertFalse(result, "The brand should not exist.");
    }
}
