package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddProductRequest;
import com.emazon.StockMicroservice.adapters.driving.http.dto.response.ProductResponse;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IProductRequestMapper;
import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import com.emazon.StockMicroservice.domain.api.IProductServicePort;
import com.emazon.StockMicroservice.domain.model.Product;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AdapConstants.PRODUCT_URL)
@RequiredArgsConstructor
@Tag(name = AdapConstants.PRODUCT, description = AdapConstants.API_FOR_PRODUCTS)
public class ProductRestControllerAdapter {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;

    @Operation(summary = AdapConstants.ADD_NEW_PRODUCT, description = AdapConstants.ADDS_NEW_PRODUCT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdapConstants.RESPONSE_CODE_201, description = AdapConstants.PRODUCT_SUCCESSFULLY_ADDED,
                    content = @Content(mediaType = AdapConstants.APPLICATION_JSON, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = AdapConstants.RESPONSE_CODE_400, description = AdapConstants.INVALID_PRODUCT_NAME,
                    content = @Content(mediaType = AdapConstants.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = AdapConstants.PRODUCT_TO_ADD, required = true,
                    content = @Content(schema = @Schema(implementation = AddProductRequest.class)))
            @RequestBody AddProductRequest addProductRequest) {
        Product product = productRequestMapper.toModel(addProductRequest);
        productServicePort.saveProduct(product);
        Map<String, Object> response = new HashMap<>();
        response.put(AdapConstants.MESSAGE, AdapConstants.PRODUCT_SUCCESSFULLY_ADDED);
        response.put(AdapConstants.NAME, product.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = AdapConstants.GET_ALL_PRODUCTS, description = AdapConstants.LIST_OF_PRODUCTS)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdapConstants.RESPONSE_CODE_201, description = AdapConstants.PRODUCT_LIST_RETURNED,
                    content = @Content(mediaType = AdapConstants.APPLICATION_JSON, schema = @Schema(implementation = PagedResult.class)))
    })
    @GetMapping
    public ResponseEntity<PagedResult<ProductResponse>> getAllProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) String sort) {
        PagedResult<Product> products = productServicePort.listProducts(page, size, direction, sort);
        List<ProductResponse> productResponses = products.getContent()
                .stream()
                .map(productRequestMapper::toResponse)
                .toList();
        PagedResult<ProductResponse> pagedProductResponses = new PagedResult<>(
                productResponses,
                products.getPage(),
                products.getSize(),
                products.getTotalElements()
        );
        return ResponseEntity.ok(pagedProductResponses);
    }
}
