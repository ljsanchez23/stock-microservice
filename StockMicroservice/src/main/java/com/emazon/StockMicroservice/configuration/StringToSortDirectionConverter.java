package com.emazon.StockMicroservice.configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.emazon.StockMicroservice.domain.util.SortDirection;

/**
 * Converts a string input to a SortDirection enum.
 * Handles invalid inputs by returning null.
 */
@Component
public class StringToSortDirectionConverter implements Converter<String, SortDirection> {

    @Override
    public SortDirection convert(String source) {
        try {
            return SortDirection.fromString(source);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
