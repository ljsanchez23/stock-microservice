package com.emazon.StockMicroservice.domain.model;

/**
 * Represents a brand with an ID, name, and description.
 * Used to identify and describe brands in the system.
 */
public class Brand {
    private final Long id;
    private final String name;
    private final String description;

    public Brand(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters and setters

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
        // Method to set the name of a brand.
    }

    public void setDescription(String description) {
        // Method to set the description of a brand.
    }
}
