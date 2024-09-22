package com.emazon.StockMicroservice.domain.util;

import com.emazon.StockMicroservice.domain.exception.*;
import com.emazon.StockMicroservice.domain.model.Brand;
import com.emazon.StockMicroservice.domain.model.Category;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Validator {
    public static void validateQuantity(int quantity) {
        if (quantity <= Constants.MIN_QUANTITY) {
            throw new InvalidQuantityException(Constants.QUANTITY_CANNOT_BE_NEGATIVE);
        }
    }
    public static void validatePrice(double price) {
        if (price <= Constants.MIN_PRICE) {
            throw new InvalidPriceException(Constants.PRICE_CANNOT_BE_NEGATIVE);
        }
    }
    public static void validateCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new InvalidCategoryException(Constants.AT_LEAST_ONE_CATEGORY);
        }
        if (categories.size() > Constants.MAX_CATEGORIES) {
            throw new InvalidCategoryException(Constants.MAX_THREE_CATEGORIES);
        }
        Set<Category> categorySet = new HashSet<>(categories);
        if (categorySet.size() != categories.size()) {
            throw new InvalidCategoryException(Constants.CATEGORIES_CANNOT_BE_DUPLICATED);
        }
    }
    public static void validateBrand(Brand brand) {
        if (brand == null) {
            throw new InvalidBrandException(Constants.BRAND_MUST_BE_PROVIDED);
        }
    }

    public static void validateCategory(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new InvalidNameException(Constants.NAME_CANNOT_BE_NULL_OR_BLANK);
        }
        if (name.length() > Constants.NAME_MAX_LENGTH) {
            throw new InvalidNameException(Constants.NAME_TOO_LONG);
        }
        if (description == null || description.isBlank()) {
            throw new InvalidDescriptionException(Constants.DESCRIPTION_CANNOT_BE_NULL_OR_BLANK);
        }
        if (description.length() > Constants.CATEGORY_DESCRIPTION_MAX_LENGTH) {
            throw new InvalidDescriptionException(Constants.CATEGORY_DESCRIPTION_TOO_LONG);
        }
    }

    public static void validateBrand (String name, String description){
        if (name == null || name.isBlank()) {
            throw new InvalidNameException(Constants.NAME_CANNOT_BE_NULL_OR_BLANK);
        }
        if (name.length() > Constants.NAME_MAX_LENGTH) {
            throw new InvalidNameException(Constants.NAME_TOO_LONG);
        }
        if (description == null || description.isBlank()) {
            throw new InvalidDescriptionException(Constants.DESCRIPTION_CANNOT_BE_NULL_OR_BLANK);
        }
        if (description.length() > Constants.BRAND_DESCRIPTION_MAX_LENGTH) {
            throw new InvalidDescriptionException(Constants.BRAND_DESCRIPTION_TOO_LONG);
        }
    }
}
