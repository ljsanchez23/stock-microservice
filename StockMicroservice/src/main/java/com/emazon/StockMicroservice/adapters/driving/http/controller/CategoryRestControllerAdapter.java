package com.emazon.StockMicroservice.adapters.driving.http.controller;

import com.emazon.StockMicroservice.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.StockMicroservice.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.StockMicroservice.domain.api.ICategoryServicePort;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    @PostMapping
    public ResponseEntity<String> saveCategory(@RequestBody AddCategoryRequest addCategoryRequest) {
        try {
            // Convertir el request en modelo de dominio
            Category category = ICategoryRequestMapper.toModel(addCategoryRequest);

            // Guardar la categoría
            categoryServicePort.saveCategory(category);

            // Responder con código 201 Created si se creó exitosamente
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Categoría '" + category.getName() + "' se ha añadido correctamente.");
        } catch (InvalidNameException e) {
            // Responder con código 400 Bad Request si la categoría ya existe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
