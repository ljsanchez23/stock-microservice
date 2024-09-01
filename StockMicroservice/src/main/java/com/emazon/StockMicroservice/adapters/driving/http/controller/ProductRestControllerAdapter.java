package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddProductRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IProductRequestMapper;
import com.emazon.StockMicroservice.domain.api.IProductServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "API for managing products")
public class ProductRestControllerAdapter {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;

    /**
     * Adds a new product.
     *
     * @param addProductRequest the product to add
     * @return a response indicating the result
     */
    @Operation(summary = "Add a new product", description = "Adds a new product to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully added",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product name",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<String> saveProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product to add", required = true,
                    content = @Content(schema = @Schema(implementation = AddProductRequest.class)))
            @RequestBody AddProductRequest addProductRequest) {
        try {
            // Use the injected mapper instance to map the request to the domain model
            Product product = productRequestMapper.toModel(addProductRequest);
            productServicePort.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product '" + product.getName() + "' has been successfully added.");
        } catch (InvalidNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
