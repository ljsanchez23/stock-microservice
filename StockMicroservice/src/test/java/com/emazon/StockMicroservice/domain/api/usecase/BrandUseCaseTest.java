package com.emazon.StockMicroservice.domain.api.usecase;

import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.spi.IBrandPersistencePort;
import com.emazon.StockMicroservice.domain.util.Constants;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

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
    @DisplayName("Should save the brand when the inputs are valid")
    void shouldSaveBrandWhenValid() {
        Brand brand = new Brand(1L, "Adidas", "Renowned for its sports clothing and accessories.");
        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(false);

        brandUseCase.saveBrand(brand);

        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }

    @Test
    @DisplayName("Should throw exception when the brand name already exists")
    void shouldThrowExceptionWhenNameAlreadyExists() {
        Brand brand = new Brand(1L, "Adidas", "Renowned for its sports clothing and accessories.");
        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> brandUseCase.saveBrand(brand));
    }

    @Test
    @DisplayName("Should return a paginated result of brands")
    void shouldReturnPagedResult() {
        PagedResult<Brand> expectedPagedResult = new PagedResult<>(Collections.emptyList(), 0, 10, 0L);
        when(brandPersistencePort.getAllBrands(0, 10, "ASC")).thenReturn(expectedPagedResult);

        PagedResult<Brand> result = brandUseCase.listBrands(0, 10, "ASC");

        assertEquals(expectedPagedResult, result);
        verify(brandPersistencePort, times(1)).getAllBrands(0, 10, "ASC");
    }

    @Test
    @DisplayName("Should throw exception when the name is not given")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(InvalidNameException.class, () -> Validator.validateBrand(null, "Valid Description"));
    }

    @Test
    @DisplayName("Should throw exception when the name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(InvalidNameException.class, () -> Validator.validateBrand(" ", "Valid Description"));
    }

    @Test
    @DisplayName("Should throw exception when the name is too long")
    void shouldThrowExceptionWhenNameIsTooLong() {
        String longName = "A".repeat(Constants.NAME_MAX_LENGTH + 1);

        assertThrows(InvalidNameException.class, () -> Validator.validateBrand(longName, "Valid Description"));
    }

    @Test
    @DisplayName("Should throw exception when the description is not given")
    void shouldThrowExceptionWhenDescriptionIsNull() {
        assertThrows(InvalidDescriptionException.class, () -> Validator.validateBrand("Valid Name", null));
    }

    @Test
    @DisplayName("Should throw exception when the description is blank")
    void shouldThrowExceptionWhenDescriptionIsBlank() {
        assertThrows(InvalidDescriptionException.class, () -> Validator.validateBrand("Valid Name", " "));
    }

    @Test
    @DisplayName("Should throw exception when the description is too long")
    void shouldThrowExceptionWhenDescriptionIsTooLong() {
        String longDescription = "A".repeat(Constants.BRAND_DESCRIPTION_MAX_LENGTH + 1);
        assertThrows(InvalidDescriptionException.class, () -> Validator.validateBrand("Valid Name", longDescription));
    }
}
