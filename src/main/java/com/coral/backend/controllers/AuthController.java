package com.coral.backend.controllers;

import com.coral.backend.dtos.CheckSessionDTO;
import com.coral.backend.dtos.LoginDTO;
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
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO userDTO) {
        return authService.login(userDTO);
    }

    @PostMapping("/check-user")
    public ResponseEntity<Object> checkUser(@RequestBody CheckSessionDTO checkSessionDTO) {
        System.out.println(checkSessionDTO.getSessionToken());
        return authService.checkUser(checkSessionDTO);
    }
}
