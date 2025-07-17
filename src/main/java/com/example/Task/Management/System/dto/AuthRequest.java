package com.example.Task.Management.System.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for login request.
 * Captures username and password from the client.
 */
@Data // Includes getter, setter, toString, equals, hashCode, etc.
public class AuthRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
