package com.example.Task.Management.System.controller;

import com.example.Task.Management.System.Jwt.JwtHelper;
import com.example.Task.Management.System.Repositry.UserRepository;
import com.example.Task.Management.System.dto.AuthRequest;
import com.example.Task.Management.System.dto.AuthResponse;
import com.example.Task.Management.System.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the AuthController using MockMvc and WebMvcTest.
 */
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager manager;

    @MockBean
    private JwtHelper helper;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private AuthRequest authRequest;

    /**
     * Set up test data before each test case.
     */
    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("password123");
    }

    /**
     * Test successful registration of a new user.
     */
    @Test
    void register_shouldReturnSuccessMessage() throws Exception {
        // Mock repository behavior
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Perform POST /api/auth/register
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));
    }

    /**
     * Test registration fails if username already exists.
     */
    @Test
    void register_shouldReturnBadRequestIfUserExists() throws Exception {
        // Mock existing user
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Perform POST /api/auth/register
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists!"));
    }

    /**
     * Test successful login and JWT generation.
     */
    @Test
    void login_shouldReturnJwtToken() throws Exception {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("testuser")
                .password("encodedPassword")
                .roles("USER")
                .build();

        // Mock user details and JWT token
        Mockito.when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        Mockito.when(helper.generateToken(userDetails)).thenReturn("mock-jwt-token");

        // Perform POST /api/auth/login
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }

    /**
     * Test login failure with invalid credentials.
     */
    @Test
    void login_shouldThrowBadCredentialsIfInvalid() throws Exception {
        // Simulate bad credentials during authentication
        Mockito.doThrow(new BadCredentialsException("Invalid credentials!"))
                .when(manager)
                .authenticate(any());

        // Perform POST /api/auth/login
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized());
    }
}
