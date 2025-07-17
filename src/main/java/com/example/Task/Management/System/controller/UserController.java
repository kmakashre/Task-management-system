package com.example.Task.Management.System.controller;

import com.example.Task.Management.System.Repositry.UserRepository;
import com.example.Task.Management.System.entity.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearer-key") // Tells Swagger that this endpoint requires JWT Bearer token
@RestController // Marks this as a REST controller
@RequestMapping("/api/users") // Base path for user-related endpoints
@AllArgsConstructor // Lombok: generates constructor with all required dependencies
public class UserController {

    private UserRepository userRepository;

    /**
     * Get the currently logged-in user's profile
     * URL: GET /api/users/me
     * Secured: Requires valid JWT token
     */
    @GetMapping("/me")
    public User getLoggedInUser(Authentication authentication) {
        // Get username from JWT token
        String username = authentication.getName();

        // Find and return the user from DB
        return userRepository.findByUsername(username).orElseThrow();
    }
}
