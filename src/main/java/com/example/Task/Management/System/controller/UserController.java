package com.example.Task.Management.System.controller;


import com.example.Task.Management.System.Repositry.UserRepository;
import com.example.Task.Management.System.entity.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;

    @GetMapping("/me")
    public User getLoggedInUser(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow();
    }
}
