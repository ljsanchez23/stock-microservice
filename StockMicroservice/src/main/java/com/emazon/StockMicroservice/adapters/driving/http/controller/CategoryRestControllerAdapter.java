package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import com.emazon.StockMicroservice.domain.api.ICategoryServicePort;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(AdapConstants.CATEGORY_URL)
@RequiredArgsConstructor
@Tag(name = AdapConstants.CATEGORY, description = AdapConstants.API_FOR_CATEGORIES)
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;

    @Operation(summary = AdapConstants.ADD_NEW_CATEGORY, description = AdapConstants.ADDS_NEW_CATEGORY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdapConstants.RESPONSE_CODE_201, description = AdapConstants.CATEGORY_SUCCESSFULLY_ADDED,
                    content = @Content(mediaType = AdapConstants.APPLICATION_JSON, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = AdapConstants.RESPONSE_CODE_400, description = AdapConstants.INVALID_CATEGORY_NAME,
                    content = @Content(mediaType = AdapConstants.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = AdapConstants.CATEGORY_TO_ADD, required = true,
                    content = @Content(schema = @Schema(implementation = AddCategoryRequest.class)))
            @RequestBody AddCategoryRequest addCategoryRequest) {
        Category category = categoryRequestMapper.toModel(addCategoryRequest);
        categoryServicePort.saveCategory(category);
        Map<String, Object> response = new HashMap<>();
        response.put(AdapConstants.MESSAGE, AdapConstants.CATEGORY_SUCCESSFULLY_ADDED);
        response.put(AdapConstants.NAME, category.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = AdapConstants.GET_ALL_CATEGORIES, description = AdapConstants.LIST_OF_CATEGORIES)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdapConstants.RESPONSE_CODE_200, description = AdapConstants.CATEGORY_LIST_RETURNED,
                    content = @Content(mediaType = AdapConstants.APPLICATION_JSON, schema = @Schema(implementation = PagedResult.class)))
    })
    @GetMapping
    public ResponseEntity<PagedResult<Category>> getAllCategories(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String direction) {
        PagedResult<Category> categories = categoryServicePort.listCategories(page, size, direction);
        return ResponseEntity.ok(categories);
    }
}
