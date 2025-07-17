package com.example.Task.Management.System.config;

import com.example.Task.Management.System.Jwt.JWTAuthenticationFilter;
import com.example.Task.Management.System.Jwt.JwtAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Marks this as a configuration class for Spring Security
@AllArgsConstructor // Lombok: generates a constructor for the final fields
public class SecurityFilterConfig {

    private JwtAuthenticationEntryPoint point; // Handles unauthorized access (401)
    private JWTAuthenticationFilter filter;    // Custom filter to validate JWT tokens

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disable CSRF (not needed for JWT stateless APIs)

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (no auth required)
                        .requestMatchers(
                                "/api/auth/**",         // Register/Login
                                "/v3/api-docs/**",      // Swagger docs
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).permitAll()
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )

                // Set session management to stateless since we are using JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add JWT filter before Spring Security's built-in authentication filter
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)

                // Handle unauthorized access with custom entry point
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))

                .build();
    }
}
