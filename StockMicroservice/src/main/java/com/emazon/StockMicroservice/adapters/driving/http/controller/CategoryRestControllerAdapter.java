package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.StockMicroservice.domain.api.ICreateCategoryServicePort;
import com.emazon.StockMicroservice.domain.api.IFindAllCategoriesServicePort;
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

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "API for managing categories")
public class CategoryRestControllerAdapter {
    private final ICreateCategoryServicePort createCategoryServicePort;
    private final IFindAllCategoriesServicePort findAllCategoriesServicePort;

    @Operation(summary = "Agregar una nueva categoría", description = "Añade una nueva categoría al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría añadida con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Nombre de categoría no válido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<String> saveCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Categoría a añadir", required = true,
                    content = @Content(schema = @Schema(implementation = AddCategoryRequest.class)))
            @RequestBody AddCategoryRequest addCategoryRequest) {
        try {
            Category category = ICategoryRequestMapper.toModel(addCategoryRequest);
            createCategoryServicePort.saveCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Category '" + category.getName() + "' has been successfully added.");
        } catch (InvalidNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @Operation(summary = "Obtener todas las categorías", description = "Recupera una lista paginada de todas las categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías recuperada con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResult.class)))
    })
    @GetMapping
    public PagedResult<Category> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") SortDirection sortDirection) {
        return findAllCategoriesServicePort.getAllCategories(page, size, sortDirection);
    }
}
