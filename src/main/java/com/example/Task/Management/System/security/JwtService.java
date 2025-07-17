package com.example.Task.Management.System.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service // Registers this as a Spring service bean
public class JwtService {

    // Secret key used for signing the JWT (uses secure 256-bit key)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Generates a JWT token for the given username.
     * Token expires in 1 day (86400000 ms).
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)                        // Set username as subject
                .setIssuedAt(new Date())                     // Current time
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expiration time (1 day)
                .signWith(key)                               // Sign using HS256 algorithm
                .compact();                                  // Build and return token
    }

    /**
     * Extracts the username from a JWT token.
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)                          // Set the signing key
                .build()
                .parseClaimsJws(token)                       // Parse the token
                .getBody()
                .getSubject();                               // Return the username
    }

    /**
     * Validates that the token's username matches the provided UserDetails.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }
}
