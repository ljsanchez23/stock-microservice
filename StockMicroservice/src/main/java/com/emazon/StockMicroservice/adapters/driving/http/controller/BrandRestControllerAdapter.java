package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.StockMicroservice.domain.api.IBrandServicePort;
import com.emazon.StockMicroservice.domain.model.Brand;
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
@RequestMapping("/brand")
@RequiredArgsConstructor
@Tag(name = "Brand", description = "API for managing brands")
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;

    @Operation(summary = "Add a new brand", description = "Adds a new brand to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand successfully added",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid brand name",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveBrand(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Brand to add", required = true,
                    content = @Content(schema = @Schema(implementation = AddBrandRequest.class)))
            @RequestBody AddBrandRequest addBrandRequest) {
        Brand brand = brandRequestMapper.toModel(addBrandRequest);
        brandServicePort.saveBrand(brand);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Brand has been successfully added.");
        response.put("name", brand.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all brands", description = "Retrieves a paginated list of all brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of brands successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResult.class)))
    })
    @GetMapping
    public ResponseEntity<PagedResult<Brand>> getAllBrands(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String direction) {
        PagedResult<Brand> brands = brandServicePort.listBrands(page, size, direction);
        return ResponseEntity.ok(brands);
    }
}
