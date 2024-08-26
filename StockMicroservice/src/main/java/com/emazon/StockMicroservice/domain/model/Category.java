package com.emazon.StockMicroservice.domain.model;

import com.emazon.StockMicroservice.domain.exception.InvalidDescriptionException;
import com.emazon.StockMicroservice.domain.exception.InvalidNameException;
import com.emazon.StockMicroservice.domain.util.Constants;

public class Category {
    private final Long id;
    private final String name;
    private final String description;

    public Category(Long id, String name, String description) {
        if (name == null || name.isBlank()) {
            throw new InvalidNameException("Name cannot be null or blank.");
        }
        if (name.length() > Constants.CATEGORY_NAME_MAX_LENGTH) {
            throw new InvalidNameException("Name must be less than 50 characters.");
        }
        if (description == null || description.isBlank()) {
            throw new InvalidDescriptionException("Description cannot be null or blank.");
        }
        if (description.length() > Constants.CATEGORY_DESCRIPTION_MAX_LENGTH) {
            throw new InvalidDescriptionException("Description must be less than 90 characters.");
        }

        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        // Method used to set the name of a category.
    }

    public void setDescription(String description) {
        // Method used to set the description of a category.
    }
}
