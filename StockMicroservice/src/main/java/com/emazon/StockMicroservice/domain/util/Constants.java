package com.emazon.StockMicroservice.domain.util;

/**
 * Contains constants used throughout the domain,
 * such as maximum lengths for names and descriptions.
 * This is a utility class and cannot be instantiated.
 */
public class Constants {
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static final int NAME_MAX_LENGTH = 50;
    public static final int CATEGORY_DESCRIPTION_MAX_LENGTH = 90;
    public static final int BRAND_DESCRIPTION_MAX_LENGTH = 120;
}
