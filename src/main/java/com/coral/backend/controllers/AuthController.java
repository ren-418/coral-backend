package com.coral.backend.controllers;

import com.coral.backend.dtos.*;
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

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody LogoutDTO logoutDTO) {
        return authService.logout(logoutDTO);
    }

    @PostMapping("/check-user")
    public ResponseEntity<Object> checkUser(@RequestBody CheckSessionDTO checkSessionDTO) {
        return authService.checkUser(checkSessionDTO);
    }
    @PostMapping("/reset-password/send-email")
    public ResponseEntity<Object> resetPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        return authService.resetPassword(forgotPasswordDTO);
    }

    @PostMapping("/reset-password/verify-token")
    public ResponseEntity<Object> verifyToken(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        return authService.checkIfTokenCoincidesWithUser(forgotPasswordDTO);
    }

    @PostMapping("/reset-password/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        return authService.changePassword(forgotPasswordDTO);
    }

    @PostMapping("/delete-user")
    public ResponseEntity<Object> changePassword(@RequestBody CheckSessionDTO sessionDTO) {
        return authService.deleteUser(sessionDTO);
    }
}
