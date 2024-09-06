package com.emazon.StockMicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private List<CategoryResponse> categories;
    private BrandResponse brand;
}
