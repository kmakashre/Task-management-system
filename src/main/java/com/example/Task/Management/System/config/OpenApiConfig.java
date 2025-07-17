package com.example.Task.Management.System.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "BearerAuth",                  // Unique name for security scheme
        type = SecuritySchemeType.HTTP,      // HTTP type
        scheme = "bearer",                   // Bearer token authentication
        bearerFormat = "JWT"                 // Format of the bearer token
)
@SecurityRequirement(name = "BearerAuth") // Applies security globally
public class OpenApiConfig {
}