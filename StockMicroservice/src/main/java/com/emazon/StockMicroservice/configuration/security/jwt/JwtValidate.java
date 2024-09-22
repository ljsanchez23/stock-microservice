package com.emazon.StockMicroservice.configuration.security.jwt;

import com.emazon.StockMicroservice.configuration.security.util.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

public class JwtValidate {
    public static boolean tokenExists(HttpServletRequest request) {
        String header = request.getHeader(SecurityConstants.JWT_HEADER);
        return header != null && header.startsWith(SecurityConstants.JWT_BEARER);
    }

    public static Claims JwtValidation(HttpServletRequest request){
        String jwtToken = request.getHeader(SecurityConstants.JWT_HEADER).replace(SecurityConstants.JWT_BEARER, "");
        return Jwts.parser()
                .verifyWith(SecurityConstants.getSignedKey(SecurityConstants.JWT_SECRET_KEY))
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
}
