package com.coral.backend.controllers;

import com.coral.backend.dtos.RegisterDTO;
import com.coral.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO user) {
        return authService.register(user);
    }

    /*@PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO userDTO) {
        return authService.login(userDTO);
    }*/

    /*@PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody Map<String, String> requestBody) {
        String sessionId = requestBody.get("sessionId");
        if (!sessionId.isEmpty()) {
            return sessionService.refresh(sessionId);
        }
        return null;
    }*/
}
