package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {
    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre de la marca ya existe")
    void shouldThrowExceptionWhenBrandNameAlreadyExists() {
        Brand existingBrand = new Brand(1L,"Adimas", "Fast as wind");
        when(brandPersistencePort.existsByName("Adimas")).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> brandUseCase.saveBrand(existingBrand));

        verify(brandPersistencePort, never()).saveBrand(existingBrand);
    }

    @Test
    @DisplayName("Debería guardar la marca cuando el nombre es único")
    void shouldSaveCategoryWhenNameIsUnique() {
        Brand newBrand = new Brand(1L, "Adimas", "Fast as wind");
        when(brandPersistencePort.existsByName("Adimas")).thenReturn(false);

        brandUseCase.saveBrand(newBrand);

        verify(brandPersistencePort, times(1)).saveBrand(newBrand);
    }

    @Test
    @DisplayName("Debería devolver un resultado paginado de marcas")
    void shouldReturnPagedResultOfBrands() {
        int page = 0;
        int size = 10;
        SortDirection sortDirection = SortDirection.ASC;
        PagedResult<Brand> expectedPagedResult = new PagedResult<>(
                List.of(new Brand(1L, "Adimas", "Shoes")),
                page,
                size,
                1
        );
        when(brandPersistencePort.getAllBrands(page, size, sortDirection)).thenReturn(expectedPagedResult);
        PagedResult<Brand> actualPagedResult = brandUseCase.listBrands(page, size, sortDirection);
        assertEquals(expectedPagedResult, actualPagedResult);
    }

    @Test
    @DisplayName("Debería devolver un resultado vacío cuando no existen marcas")
    void shouldReturnEmptyResultWhenNoBrandsExist() {
        int page = 0;
        int size = 10;
        SortDirection sortDirection = SortDirection.ASC;

        PagedResult<Brand> expectedPagedResult = new PagedResult<>(
                List.of(),
                page,
                size,
                0
        );

        when(brandPersistencePort.getAllBrands(page, size, sortDirection)).thenReturn(expectedPagedResult);

        PagedResult<Brand> actualPagedResult = brandUseCase.listBrands(page, size, sortDirection);

        assertEquals(expectedPagedResult, actualPagedResult);
        assertEquals(0, actualPagedResult.getTotalElements());
        assertEquals(0, actualPagedResult.getContent().size());
    }

    @Test
    @DisplayName("Debería manejar el ordenamiento por orden descendente correctamente")
    void shouldHandleSortingByDescendingOrder() {
        int page = 0;
        int size = 10;
        SortDirection sortDirection = SortDirection.DESC;

        List<Brand> brands = List.of(
                new Brand(2L, "Pfizor", "Pharmaceutics"),
                new Brand(1L, "Adimas", "Shoes")
        );
        PagedResult<Brand> expectedPagedResult = new PagedResult<>(
                brands,
                page,
                size,
                brands.size()
        );

        when(brandPersistencePort.getAllBrands(page, size, sortDirection)).thenReturn(expectedPagedResult);

        PagedResult<Brand> actualPagedResult = brandUseCase.listBrands(page, size, sortDirection);

        assertEquals(expectedPagedResult, actualPagedResult);
        assertEquals(brands, actualPagedResult.getContent());
    }

    @Test
    @DisplayName("Debería llamar al puerto de persistencia con los parámetros correctos")
    void shouldCallPersistencePortWithCorrectParameters() {
        int page = 1;
        int size = 20;
        SortDirection sortDirection = SortDirection.DESC;

        brandUseCase.listBrands(page, size, sortDirection);

        verify(brandPersistencePort, times(1)).getAllBrands(page, size, sortDirection);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el puerto de persistencia falla")
    void shouldThrowExceptionWhenPersistencePortFails() {

        int page = 1;
        int size = 10;
        SortDirection sortDirection = SortDirection.ASC;

        doThrow(new RuntimeException("Persistence error")).when(brandPersistencePort).getAllBrands(page, size, sortDirection);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            brandUseCase.listBrands(page, size, sortDirection);
        });
        assertEquals("Persistence error", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre es nulo o vacío")
    void shouldThrowExceptionWhenBrandNameIsNullOrBlank() {
        assertThrows(InvalidNameException.class, () -> brandUseCase.validateBrand(null, "Valid description"));
        assertThrows(InvalidNameException.class, () -> brandUseCase.validateBrand("", "Valid description"));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre excede el tamaño máximo permitido")
    void shouldThrowExceptionWhenBrandNameExceedsMaxLength() {
        String longName = "A".repeat(Constants.NAME_MAX_LENGTH + 1);
        assertThrows(InvalidNameException.class, () -> brandUseCase.validateBrand(longName, "Valid description"));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la descripción es nula o vacía")
    void shouldThrowExceptionWhenBrandDescriptionIsNullOrBlank() {
        assertThrows(InvalidDescriptionException.class, () -> brandUseCase.validateBrand("Valid name", null));
        assertThrows(InvalidDescriptionException.class, () -> brandUseCase.validateBrand("Valid name", ""));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la descripción excede el tamaño máximo permitido")
    void shouldThrowExceptionWhenBrandDescriptionExceedsMaxLength() {
        String longDescription = "A".repeat(Constants.BRAND_DESCRIPTION_MAX_LENGTH + 1);
        assertThrows(InvalidDescriptionException.class, () -> brandUseCase.validateBrand("Valid name", longDescription));
    }

    @Test
    @DisplayName("Debería pasar la validación cuando el nombre y la descripción son válidos")
    void shouldPassValidationWhenBrandNameAndDescriptionAreValid() {
        brandUseCase.validateBrand("Valid name", "Valid description");
    }
}
