package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CreateBrandUseCaseTest {
    private CreateBrandUseCase createBrandUseCase;
    private IBrandPersistencePort brandPersistencePort;

    @BeforeEach
    void setUp() {
        brandPersistencePort = mock(IBrandPersistencePort.class);
        createBrandUseCase = new CreateBrandUseCase(brandPersistencePort);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre de la marca ya existe")
    void shouldThrowExceptionWhenBrandNameAlreadyExists() {
        Brand existingBrand = new Brand(1L,"Adimas", "Fast as wind");
        when(brandPersistencePort.existsByName("Adimas")).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> createBrandUseCase.saveBrand(existingBrand));

        verify(brandPersistencePort, never()).saveBrand(existingBrand);
    }

    @Test
    @DisplayName("Debería guardar la marca cuando el nombre es único")
    void shouldSaveCategoryWhenNameIsUnique() {
        Brand newBrand = new Brand(1L, "Adimas", "Fast as wind");
        when(brandPersistencePort.existsByName("Adimas")).thenReturn(false);

        createBrandUseCase.saveBrand(newBrand);

        verify(brandPersistencePort, times(1)).saveBrand(newBrand);
    }
}
