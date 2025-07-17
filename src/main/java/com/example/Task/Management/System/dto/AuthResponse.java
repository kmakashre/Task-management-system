package com.example.Task.Management.System.dto;

/**
 * DTO for sending JWT token back to the client after successful login.
 */
public class AuthResponse {

    private String token; // The generated JWT token

    // Constructor to initialize the token
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter method to retrieve the token
    public String getToken() {
        return token;
    }
}
