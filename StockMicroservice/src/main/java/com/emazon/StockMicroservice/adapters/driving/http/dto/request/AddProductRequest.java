package com.emazon.StockMicroservice.adapters.driving.http.dto.request;

import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.model.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AddProductRequest {
    private final Long id;
    @NotBlank(message = AdapConstants.NAME_CANNOT_BE_BLANK)
    @Size(max = AdapConstants.MAX_NAME_VALUE, message = AdapConstants.NAME_TOO_LONG)
    private final String name;
    @NotBlank(message = AdapConstants.DESCRIPTION_CANNOT_BE_BLANK)
    @Size(max = AdapConstants.MAX_DESCRIPTION_VALUE, message = AdapConstants.CATEGORY_DESCRIPTION_TOO_LONG)
    private final String description;
    @Min(value = AdapConstants.NO_NEG_VALUE, message = AdapConstants.QUANTITY_MUST_BE_POSITIVE)
    private final int quantity;
    @Min(value = AdapConstants.NO_NEG_VALUE, message = AdapConstants.PRICE_MUST_BE_POSITIVE)
    private final double price;
    @NotEmpty(message = AdapConstants.AT_LEAST_ONE_CATEGORY)
    @Size(max = AdapConstants.MAX_CAT_VALUE, message = AdapConstants.MAX_THREE_CATEGORIES)
    private final List<Category> categories;
    @NotNull(message = AdapConstants.BRAND_MUST_BE_PROVIDED)
    private final Brand brand;
}
