package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddProductRequest;
import com.emazon.StockMicroservice.adapters.driving.http.dto.response.ProductResponse;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IProductRequestMapper;
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
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "API for managing products")
public class ProductRestControllerAdapter {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;

    @Operation(summary = "Add a new product", description = "Adds a new product to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully added",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product name",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product to add", required = true,
                    content = @Content(schema = @Schema(implementation = AddProductRequest.class)))
            @RequestBody AddProductRequest addProductRequest) {
        Product product = productRequestMapper.toModel(addProductRequest);
        productServicePort.saveProduct(product);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product has been successfully added.");
        response.put("name", product.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all products", description = "Retrieves a paginated list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResult.class)))
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
