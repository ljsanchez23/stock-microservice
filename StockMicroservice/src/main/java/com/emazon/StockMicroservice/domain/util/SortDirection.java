package com.emazon.StockMicroservice.domain.util;

/**
 * Represents sorting directions: ascending (ASC) and descending (DESC).
 * Provides methods to get the direction as a string and to convert from a string.
 */
public enum SortDirection {
    ASC("asc"),
    DESC("desc");

    private final String direction;

    SortDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public static SortDirection fromString(String direction) {
        for (SortDirection sortDirection : SortDirection.values()) {
            if (sortDirection.getDirection().equalsIgnoreCase(direction)) {
                return sortDirection;
            }
        }
        throw new IllegalArgumentException("Invalid sort direction: " + direction);
    }
}
