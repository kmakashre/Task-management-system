package com.example.Task.Management.System.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component // Makes this a Spring-managed bean
public class JwtHelper {

    // Secret key used for signing and validating JWT tokens
    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    // Token validity duration (5 hours in seconds)
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    /**
     * Extract username (subject) from token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extract expiration date from token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * General method to extract a specific claim from token
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieve all claims from the token using the secret key
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret) // Set the secret key
                .parseClaimsJws(token) // Parse the token
                .getBody();            // Get the claims
    }

    /**
     * Check if the token has expired
     */
    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    /**
     * Generate JWT token using user details
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); // You can put custom claims here if needed
        return Jwts.builder()
                .setClaims(claims) // Set custom claims
                .setSubject(userDetails.getUsername()) // Set username as subject
                .setIssuedAt(new Date()) // Current time
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // Expiration time
                .signWith(SignatureAlgorithm.HS512, secret) // Sign the token
                .compact(); // Build and return the token
    }

    /**
     * Validate token:
     * - Checks if the username matches
     * - Checks if the token is not expired
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        return (getUsernameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
