package com.example.Task.Management.System.controller;

import com.example.Task.Management.System.Jwt.JwtHelper;
import com.example.Task.Management.System.Repositry.UserRepository;
import com.example.Task.Management.System.dto.AuthRequest;
import com.example.Task.Management.System.dto.AuthResponse;
import com.example.Task.Management.System.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final JwtHelper helper;
    private UserDetailsService userDetailsService;

    // Endpoint for registering a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        try {
            // Authenticate user credentials
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials!");
        }

        // Load user details and generate JWT token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);

        // Return token in response
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
