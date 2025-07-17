package com.example.Task.Management.System.Jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // Registers this class as a Spring bean
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is triggered when an unauthenticated user tries to access a secured endpoint.
     * It sends a 401 Unauthorized error response.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Sends HTTP 401 response with error message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized: " + authException.getMessage());
    }
}
