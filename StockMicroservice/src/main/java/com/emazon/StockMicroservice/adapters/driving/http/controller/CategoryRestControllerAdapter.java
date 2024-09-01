package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.StockMicroservice.domain.api.ICategoryServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import com.emazon.StockMicroservice.domain.util.SortDirection;
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

/**
 * REST controller for managing categories.
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "API for managing categories")
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;

    /**
     * Adds a new category.
     *
     * @param addCategoryRequest the category to add
     * @return a response indicating the result
     */
    @Operation(summary = "Add a new category", description = "Adds a new category to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category successfully added",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category name",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<String> saveCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category to add", required = true,
                    content = @Content(schema = @Schema(implementation = AddCategoryRequest.class)))
            @RequestBody AddCategoryRequest addCategoryRequest) {
        try {
            // Use the injected mapper instance to map the request to the domain model
            Category category = categoryRequestMapper.toModel(addCategoryRequest);
            categoryServicePort.saveCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Category '" + category.getName() + "' has been successfully added.");
        } catch (InvalidNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @Operation(summary = "Get all categories", description = "Retrieves a paginated list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResult.class)))
    })
    @GetMapping
    public ResponseEntity<PagedResult<Category>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        PagedResult<Category> categories = categoryServicePort.listCategories(page, size, SortDirection.fromString(sortDirection));
        return ResponseEntity.ok(categories);
    }
}
