package com.emazon.StockMicroservice.configuration.security.jwt.filters;

import com.emazon.StockMicroservice.configuration.security.util.SecurityConstants;
import com.emazon.StockMicroservice.configuration.security.jwt.JwtValidate;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    Logger log = Logger.getLogger(getClass().getName());

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        try {
            if (JwtValidate.tokenExists(request)) {
                Claims claims = JwtValidate.JwtValidation(request);

                if (claims != null) {
                    String username = claims.getSubject();
                    Collection<? extends GrantedAuthority> authorities = getAuthorities(claims);

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (SignatureException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(SecurityConstants.INVALID_JWT_SIGNATURE);
            response.getWriter().flush();
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(SecurityConstants.AUTHENTICATION_FAILED);
            response.getWriter().flush();
            return;
        }

        chain.doFilter(request, response);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        Object authoritiesObject = claims.get(SecurityConstants.JWT_AUTHORITIES);
        List<String> authorities;

        if (authoritiesObject instanceof List<?>) {
            @SuppressWarnings(SecurityConstants.UNCHECKED)
            List<String> tempList = (List<String>) authoritiesObject;
            authorities = new ArrayList<>(tempList);
        } else {
            authorities = Collections.emptyList();
        }

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
