package com.emazon.StockMicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddBrandRequest {
    private final Long id;
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private final String name;
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 90, message = "Description must be less than 120 characters")
    private final String description;
}
