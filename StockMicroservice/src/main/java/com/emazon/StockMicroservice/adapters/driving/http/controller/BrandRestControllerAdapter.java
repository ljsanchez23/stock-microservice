package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.StockMicroservice.adapters.util.AdapConstants;
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
@RequestMapping(AdapConstants.BRAND_URL)
@RequiredArgsConstructor
@Tag(name = AdapConstants.BRAND, description = AdapConstants.API_FOR_BRANDS)
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;

    @Operation(summary = AdapConstants.ADD_NEW_BRAND, description = AdapConstants.ADDS_NEW_BRAND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdapConstants.RESPONSE_CODE_201, description = AdapConstants.BRAND + AdapConstants.SUCCESSFULLY_ADDED,
                    content = @Content(mediaType = AdapConstants.APPLICATION_JSON, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = AdapConstants.RESPONSE_CODE_400, description = AdapConstants.INVALID_BRAND_NAME,
                    content = @Content(mediaType = AdapConstants.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveBrand(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = AdapConstants.BRAND + AdapConstants.TO_ADD, required = true,
                    content = @Content(schema = @Schema(implementation = AddBrandRequest.class)))
            @RequestBody AddBrandRequest addBrandRequest) {
        Brand brand = brandRequestMapper.toModel(addBrandRequest);
        brandServicePort.saveBrand(brand);
        Map<String, Object> response = new HashMap<>();
        response.put(AdapConstants.MESSAGE, AdapConstants.BRAND + AdapConstants.SUCCESSFULLY_ADDED);
        response.put(AdapConstants.NAME, brand.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = AdapConstants.GET_ALL_BRANDS, description = AdapConstants.LIST_OF_BRANDS)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdapConstants.RESPONSE_CODE_200, description = AdapConstants.BRAND_LIST_RETURNED,
                    content = @Content(mediaType = AdapConstants.APPLICATION_JSON, schema = @Schema(implementation = PagedResult.class)))
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
