package com.emazon.StockMicroservice.adapters.driving.http.dto.request;

import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.model.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * DTO for adding a new product.
 */
@AllArgsConstructor
@Getter
public class AddProductRequest {
    private final Long id;
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private final String name;
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 90, message = "Description must be less than 90 characters")
    private final String description;
    @Min(value = 0, message = "Quantity must be at least 0")
    private final int quantity;
    @Min(value = 0, message = "Price must be at least 0")
    private final double price;
    @NotEmpty(message = "At least one category must be provided")
    @Size(max = 3, message = "A product cannot have more than 3 categories")
    private final List<Category> categories;
    @NotNull(message = "Brand must be provided")
    private final Brand brand;
}
