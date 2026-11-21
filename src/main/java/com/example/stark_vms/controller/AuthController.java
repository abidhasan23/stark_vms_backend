package com.example.stark_vms.controller;

import com.example.stark_vms.dto.LoginRequest;
import com.example.stark_vms.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Simple hardcoded auth endpoint for Assignment 1.
 * In production, replace with JWT + UserDetailsService.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Map<String, String[]> USERS = Map.of(
            "admin", new String[]{"admin123", "ADMIN"},
            "user",  new String[]{"user123",  "USER"}
    );

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.username() == null ? "" : request.username().trim().toLowerCase();
        String password = request.password() == null ? "" : request.password();

        String[] creds = USERS.get(username);
        if (creds == null || !creds[0].equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }


        String token = "stark-vms-token-" + username + "-" + System.currentTimeMillis();
        return ResponseEntity.ok(new LoginResponse(token, username, creds[1]));
    }
}
