package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.StockMicroservice.domain.api.ICreateBrandServicePort;
import com.emazon.StockMicroservice.domain.api.IFindAllBrandsServicePort;
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

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Tag(name = "Brand", description = "API for managing brands")
public class BrandRestControllerAdapter {
    private final ICreateBrandServicePort createBrandServicePort;
    private final IFindAllBrandsServicePort findAllBrandsServicePort;

    @Operation(summary = "Agregar una nueva marca", description = "Añade una nueva marca al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca añadida con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Nombre de marca no válido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<String> saveBrand(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Marca a añadir", required = true,
                    content = @Content(schema = @Schema(implementation = AddBrandRequest.class)))
            @RequestBody AddBrandRequest addBrandRequest) {
        try {
            Brand brand = IBrandRequestMapper.toModel(addBrandRequest);
            createBrandServicePort.saveBrand(brand);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Brand '" + brand.getName() + "' has been successfully added.");
        } catch (InvalidNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @Operation(summary = "Obtener todas las marcas", description = "Recupera una lista paginada de todas las marcas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de marcas recuperada con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResult.class)))
    })
    @GetMapping
    public PagedResult<Brand> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") SortDirection sortDirection) {
        return findAllBrandsServicePort.getAllBrands(page, size, sortDirection);
    }
}
