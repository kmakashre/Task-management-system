package com.example.Task.Management.System.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JwtService class to test JWT token generation, extraction, and validation.
 */
class JwtServiceTest {

    private JwtService jwtService;

    /**
     * Initialize JwtService before each test.
     */
    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    /**
     * Test if generateToken() returns a non-null token that starts with "ey" (common for JWT).
     */
    @Test
    void generateToken_shouldReturnValidToken() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        assertNotNull(token); // Ensure token is not null
        assertTrue(token.startsWith("ey")); // JWT tokens usually start with "ey"
    }

    /**
     * Test if extractUsername() correctly retrieves the username from the token.
     */
    @Test
    void extractUsername_shouldReturnCorrectUsername() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(username, extractedUsername); // Check if extracted username matches original
    }

    /**
     * Test if validateToken() returns true when token and userDetails match.
     */
    @Test
    void validateToken_shouldReturnTrueForCorrectUser() {
        UserDetails userDetails = User.withUsername("testuser")
                .password("password")
                .roles("USER")
                .build();

        String token = jwtService.generateToken(userDetails.getUsername());

        assertTrue(jwtService.validateToken(token, userDetails)); // Valid token for correct user
    }

    /**
     * Test if validateToken() returns false when token and userDetails do not match.
     */
    @Test
    void validateToken_shouldReturnFalseForIncorrectUser() {
        UserDetails userDetails = User.withUsername("otheruser")
                .password("password")
                .roles("USER")
                .build();

        String token = jwtService.generateToken("testuser");

        assertFalse(jwtService.validateToken(token, userDetails)); // Invalid user, should return false
    }
}
