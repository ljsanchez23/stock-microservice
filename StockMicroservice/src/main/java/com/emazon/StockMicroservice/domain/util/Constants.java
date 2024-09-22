package com.emazon.StockMicroservice.domain.util;

public class Constants {
    public static final String CHARACTERS = " characters.";
    public static final String AT_LEAST_ONE_CATEGORY = "At least one category.";
    public static final String MAX_THREE_CATEGORIES = "Max three categories";
    public static final String BRAND_MUST_BE_PROVIDED = "Brand must be provided";
    public static final int MIN_QUANTITY = 0;
    public static final String QUANTITY_CANNOT_BE_NEGATIVE = "Quantity cannot be negative";
    public static final double MIN_PRICE = 0;
    public static final String PRICE_CANNOT_BE_NEGATIVE = "Price cannot be negative";
    public static final int MAX_CATEGORIES = 3;
    public static final String CATEGORIES_CANNOT_BE_DUPLICATED = "Categories cannot be duplicated";
    public static final String NAME_CANNOT_BE_NULL_OR_BLANK = "Name cannot be null or blank";
    public static final String NAME_TOO_LONG = "Name too long";
    public static final String DESCRIPTION_CANNOT_BE_NULL_OR_BLANK = "Description cannot be null or blank";
    public static final String BRAND_DESCRIPTION_TOO_LONG = "Brand description too long";
    public static final String CATEGORY_DESCRIPTION_TOO_LONG = "Category description too long";
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 10;
    public static final String DEFAULT_DIRECTION = "ASC";
    public static final String DEFAULT_SORT_FIELD = "product";
    public static final String BRAND_ALREADY_EXISTS = "Brand already exists";
    public static final String CATEGORY_ALREADY_EXISTS = "Category already exists";
    public static final String PRODUCT_ALREADY_EXISTS = "Product already exists";
    public static final String DESC_SORT_FIELD = "DESC";
    public static final String CATEGORY_SORTING = "categories.name";
    public static final String BRAND_SORTING = "brand.name";
    public static final String DEFAULT_SORTING = "name";
    public static final String CATEGORY = "category";
    public static final String BRAND = "brand";


    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static final int NAME_MAX_LENGTH = 50;
    public static final int CATEGORY_DESCRIPTION_MAX_LENGTH = 90;
    public static final int BRAND_DESCRIPTION_MAX_LENGTH = 120;
}
