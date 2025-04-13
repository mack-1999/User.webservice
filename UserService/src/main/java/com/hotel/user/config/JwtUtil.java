package com.hotel.user.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

    private static final String SECRET_KEY = "YzNxd2VydGV5dTNrbDJ4ZmhoYjM0amtlNndxMzJ1d2tzZTM1ZnA1eg==";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                   .verifyWith(getSigningKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Method to generate JWT token (you can add expiration and other claims here)
    public String generateToken(String username) {
        return Jwts.builder()
                   .setSubject(username)  // Set the subject (username) claim
                   .setIssuedAt(new Date(System.currentTimeMillis()))  // Set issued date
                   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Set expiration date (e.g., 10 hours)
                   .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // Sign the JWT with the signing key and algorithm
                   .compact();  // Generate the JWT string
    }
}