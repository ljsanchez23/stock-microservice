package com.emazon.StockMicroservice.domain.model;

import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    @Test
    @DisplayName("Debería crear una categoría cuando se proporciona una entrada válida")
    void shouldCreateCategoryWhenValidInput() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        assertNotNull(category);
        assertEquals("Electronics", category.getName());
        assertEquals("Devices and gadgets", category.getDescription());
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre es demasiado largo")
    void shouldThrowExceptionWhenNameIsTooLong() {
        String longName = "A".repeat(51);
        assertThrows(InvalidNameException.class, () -> new Category(1L, longName,"Valid description"));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la descripción es demasiado larga")
    void shouldThrowExceptionWhenDescriptionIsTooLong() {
        String longDescription = "A".repeat(91);
        assertThrows(InvalidDescriptionException.class, () -> new Category(1L,"Valid name", longDescription));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre está vacío")
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(InvalidNameException.class, () -> new Category(1L, "", "Valid description"));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la descripción está vacía")
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        assertThrows(InvalidDescriptionException.class, () -> new Category(1L,"Valid name", ""));
    }
}
