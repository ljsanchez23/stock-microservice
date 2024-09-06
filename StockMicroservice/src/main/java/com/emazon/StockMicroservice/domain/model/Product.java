package com.emazon.StockMicroservice.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private final Long id;
    private final String name;
    private final String description;
    private final int quantity;
    private final double price;
    private final List<Category> categories;
    private final Brand brand;

    public Product(Long id, String name, String description, int quantity, double price, List<Category> categories, Brand brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.brand = brand;
        this.categories = categories;
    }


    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }
    public Brand getBrand() {
        return brand;
    }
}
