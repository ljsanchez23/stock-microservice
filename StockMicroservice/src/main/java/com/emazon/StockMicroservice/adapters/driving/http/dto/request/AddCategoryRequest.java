package com.emazon.StockMicroservice.adapters.driving.http.dto.request;

import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCategoryRequest {
    private final Long id;
    @NotBlank(message = AdapConstants.NAME_CANNOT_BE_BLANK)
    @Size(max = AdapConstants.MAX_NAME_VALUE, message = AdapConstants.NAME_TOO_LONG)
    private final String name;
    @NotBlank(message = AdapConstants.DESCRIPTION_CANNOT_BE_BLANK)
    @Size(max = AdapConstants.MAX_DESCRIPTION_VALUE, message = AdapConstants.CATEGORY_DESCRIPTION_TOO_LONG)
    private final String description;
}
