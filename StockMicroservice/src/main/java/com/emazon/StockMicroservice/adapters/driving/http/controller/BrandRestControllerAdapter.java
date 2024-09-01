package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.StockMicroservice.domain.api.IBrandServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Brand;
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
 * REST controller for managing brands.
 */
@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Tag(name = "Brand", description = "API for managing brands")
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;

    /**
     * Adds a new brand.
     *
     * @param addBrandRequest the brand to add
     * @return a response indicating the result
     */
    @Operation(summary = "Add a new brand", description = "Adds a new brand to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand successfully added",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid brand name",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<String> saveBrand(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Brand to add", required = true,
                    content = @Content(schema = @Schema(implementation = AddBrandRequest.class)))
            @RequestBody AddBrandRequest addBrandRequest) {
        try {
            // Use the injected mapper instance to map the request to the domain model
            Brand brand = brandRequestMapper.toModel(addBrandRequest);
            brandServicePort.saveBrand(brand);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Brand '" + brand.getName() + "' has been successfully added.");
        } catch (InvalidNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    /**
     * Retrieves a paginated list of all brands.
     *
     * @param page          the page number to retrieve
     * @param size          the number of items per page
     * @param sortDirection the sorting direction (ASC or DESC)
     * @return a paginated list of brands
     */
    @Operation(summary = "Get all brands", description = "Retrieves a paginated list of all brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of brands successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResult.class)))
    })
    @GetMapping
    public PagedResult<Brand> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") SortDirection sortDirection) {
        return brandServicePort.listBrands(page, size, sortDirection);
    }
}
