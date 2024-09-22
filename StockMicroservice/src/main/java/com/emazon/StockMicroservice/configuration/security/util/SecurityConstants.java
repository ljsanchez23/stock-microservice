package com.emazon.StockMicroservice.configuration.security.util;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class SecurityConstants {

    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_SECRET_KEY = System.getenv("SECRET_KEY");
    public static final String JWT_AUTHORITIES = "authorities";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String CREATE_BRAND_URL = "/brand/save";
    public static final String CREATE_CATEGORY_URL = "/category/save";
    public static final String CREATE_PRODUCT_URL = "/product/save";
    public static final String UNCHECKED = "unchecked";
    public static final String APPLICATION_JSON = "application/json";
    public static final String WRITER = "{\"error\": \"Unauthorized\", \"message\": \"Invalid or missing token.\"}";
    public static final String INVALID_JWT_SIGNATURE = "Invalid JWT Signature";
    public static final String AUTHENTICATION_FAILED = "Authentication failed";

    public static SecretKey getSignedKey(String secretKey){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
