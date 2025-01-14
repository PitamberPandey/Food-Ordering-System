package com.example.Food.Ordering.System.config;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {

    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
    private static final long CLOCK_SKEW = 5; // Allow 5 seconds of clock skew
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Generates a JWT token
    public String generateToken(Authentication authentication) {
        // Extract roles/authorities
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = authorities.stream()
                                   .map(GrantedAuthority::getAuthority)
                                   .collect(Collectors.joining(","));

        // Build and return the token
        return Jwts.builder()
                   .setSubject(authentication.getName()) // Username or identifier
                   .claim("roles", roles) // Add roles as a claim
                   .setIssuedAt(new Date()) // Token issue time
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration time
                   .signWith(key) // Sign with the secret key
                   .compact(); // Build the token
    }

    // Extracts email from the JWT token
    public String getEmailFromJwtToken(String jwt) {
        try {
            // Remove "Bearer " prefix if present
            if (jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7);
            }

            // Parse the JWT token
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(jwt)
                                .getBody();

            // Allow for small clock skew tolerance
            Date expiration = claims.getExpiration();
            Date now = Date.from(Instant.now());
            if (expiration.before(now)) {
                long diff = now.getTime() - expiration.getTime();
                if (diff <= CLOCK_SKEW * 1000) {
                    // Allow small clock skew difference
                } else {
                    throw new ExpiredJwtException(null, claims, "Token has expired");
                }
            }

            // Return the email or subject (username) from the token
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Expired token");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    // Validates a JWT token
    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
        }
        return false; // Invalid token
    }
}
