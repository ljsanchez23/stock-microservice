package com.emazon.StockMicroservice.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCategoryRequest {
    private final Long id;
    private final String name;
    private final String description;
}
