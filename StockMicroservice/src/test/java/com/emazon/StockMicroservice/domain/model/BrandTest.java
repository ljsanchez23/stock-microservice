package com.emazon.StockMicroservice.domain.model;

import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrandTest {
    @Test
    @DisplayName("Debería crear una marca cuando se proporciona una entrada válida")
    void shouldCreateBrandWhenValidInput() {
        Brand brand = new Brand(1L, "Adimas", "Fast as wind");
        assertNotNull(brand);
        assertEquals("Adimas", brand.getName());
        assertEquals("Fast as wind", brand.getDescription());
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre es demasiado largo")
    void shouldThrowExceptionWhenNameIsTooLong() {
        String longName = "A".repeat(51);
        assertThrows(InvalidNameException.class, () -> new Brand(1L, longName,"Valid description"));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la descripción es demasiado larga")
    void shouldThrowExceptionWhenDescriptionIsTooLong() {
        String longDescription = "A".repeat(121);
        assertThrows(InvalidDescriptionException.class, () -> new Brand(1L,"Valid name", longDescription));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre está vacío")
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(InvalidNameException.class, () -> new Brand(1L, "", "Valid description"));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la descripción está vacía")
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        assertThrows(InvalidDescriptionException.class, () -> new Brand(1L,"Valid name", ""));
    }
}
