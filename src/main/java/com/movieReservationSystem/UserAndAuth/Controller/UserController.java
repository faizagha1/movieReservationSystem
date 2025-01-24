package com.movieReservationSystem.UserAndAuth.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieReservationSystem.UserAndAuth.Dtos.AuthResponse;
import com.movieReservationSystem.UserAndAuth.Dtos.LoginRequest;
import com.movieReservationSystem.UserAndAuth.Dtos.SignupRequest;
import com.movieReservationSystem.UserAndAuth.Services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(
            @Valid @RequestBody SignupRequest signupRequest) {
        AuthResponse authResponse = userService.registerUser(signupRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest signupRequest) {
        AuthResponse authResponse = userService.loginUser(signupRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/promote/{userId}")
    public ResponseEntity<?> promoteUser(
            @Valid @RequestBody String userId) {
        AuthResponse authResponse = userService.promoteUser(userId);
        return ResponseEntity.ok(authResponse);
    }
}
