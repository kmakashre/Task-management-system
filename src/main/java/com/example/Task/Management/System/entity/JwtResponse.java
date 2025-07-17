package com.example.Task.Management.System.entity;

import lombok.Data;
import lombok.Builder;

/**
 * Response DTO for sending JWT token and username after successful login.
 */
@Data        // Generates getters, setters, toString, equals, hashCode, etc.
@Builder     // Enables builder pattern for object creation
public class JwtResponse {
    private String username;   // Authenticated user's username
    private String jwtToken;   // JWT token to be returned to the client
}
