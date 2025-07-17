package com.example.Task.Management.System.entity;

import lombok.Data;

/**
 * Request DTO for login (used to receive username and password).
 */
@Data // Includes @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
public class JwtRequest {
    private String username;
    private String password;
}
