package com.emazon.StockMicroservice.domain.util;

public class Constants {
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static final int NAME_MAX_LENGTH = 50;
    public static final int CATEGORY_DESCRIPTION_MAX_LENGTH = 90;
    public static final int BRAND_DESCRIPTION_MAX_LENGTH = 120;
    public static final String BAD_REQUEST = "Bad request";
    public static final String DEFAULT_PATH = "/";
}
