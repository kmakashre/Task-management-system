package com.example.Task.Management.System.service;

import com.example.Task.Management.System.Repositry.UserRepository;
import com.example.Task.Management.System.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Primary // This annotation tells Spring to use this implementation when multiple beans of the same type are present
@Service // Marks this class as a Spring service component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Injecting the user repository to fetch user data from DB

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetching the user from the database using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); // If not found, throw exception

        // Returning a Spring Security User object with username, password, and roles
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())) // e.g., ROLE_USER
        );
    }
}
