package com.example.Task.Management.System.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header from the request
        String requestHeader = request.getHeader("Authorization");
        log.info("Header: {}", requestHeader);

        String username = null;
        String token = null;

        // Check if the header contains a Bearer token
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7); // Extract token from header
            try {
                // Extract username from token
                username = jwtHelper.getUsernameFromToken(token);
            } catch (Exception e) {
                log.warn("Invalid token", e); // Log warning if token is invalid
            }
        }

        // Proceed only if username is not null and the user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate the token with user details
            if (jwtHelper.validateToken(token, userDetails)) {
                // Create authentication token
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set additional details from request
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
