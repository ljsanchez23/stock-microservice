package com.emazon.StockMicroservice.configuration;

import com.emazon.StockMicroservice.domain.util.SortDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringToSortDirectionConverterTest {

    private final StringToSortDirectionConverter converter = new StringToSortDirectionConverter();

    @Test
    @DisplayName("Should convert valid strings to corresponding SortDirection")
    void shouldConvertStringToSortDirection() {
        assertEquals(SortDirection.ASC, converter.convert("ASC"));
        assertEquals(SortDirection.DESC, converter.convert("DESC"));
    }

    @Test
    @DisplayName("Should return null for invalid SortDirection strings")
    void shouldReturnNullForInvalidSortDirection() {
        assertNull(converter.convert("INVALID"));
    }
}
