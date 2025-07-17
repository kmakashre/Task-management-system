package com.example.Task.Management.System.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration // Marks this as a Spring configuration class
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                // API metadata: title and version
                .info(new Info().title("Task Management API").version("1.0"))

                // Define the security scheme for JWT
                .components(new Components().addSecuritySchemes("bearer-key",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)      // HTTP-based authentication
                                .scheme("bearer")                   // Use bearer tokens
                                .bearerFormat("JWT")                // Specify token format
                ))

                // Apply the security requirement globally (applies to all endpoints unless overridden)
                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-key", List.of("read", "write"))
                );
    }
}
