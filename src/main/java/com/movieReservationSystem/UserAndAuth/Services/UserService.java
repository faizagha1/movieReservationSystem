package com.movieReservationSystem.UserAndAuth.Services;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movieReservationSystem.UserAndAuth.Dtos.AuthResponse;
import com.movieReservationSystem.UserAndAuth.Dtos.LoginRequest;
import com.movieReservationSystem.UserAndAuth.Dtos.SignupRequest;
import com.movieReservationSystem.UserAndAuth.Entity.Role;
import com.movieReservationSystem.UserAndAuth.Entity.UserEntity;
import com.movieReservationSystem.UserAndAuth.Exceptions.NoUserFound;
import com.movieReservationSystem.UserAndAuth.Repository.UserRepo;
import com.movieReservationSystem.UserAndAuth.Utils.JwtGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepository;
    private final JwtGenerator jwtGenerator;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse registerUser(SignupRequest signupRequest) {
        Boolean existsByUserName = userRepository.existsByusername(signupRequest.getUserName());
        if (existsByUserName) {
            throw new NoUserFound("Username already exists");
        }

        UserEntity user = UserEntity
                .builder()
                .fullname(signupRequest.getFullName())
                .email(signupRequest.getEmail())
                .username(signupRequest.getUserName())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(new HashSet<>(Arrays.asList(Role.USER)))
                .build();
        userRepository.save(user);
        String token = jwtGenerator.generateToken(signupRequest.getUserName());
        return AuthResponse.builder()
                .JwtToken(token)
                .message("User registered successfully")
                .build();
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByusername(loginRequest.getUserName())
                .orElseThrow(() -> new NoUserFound("User not found"));
        String token = jwtGenerator.generateToken(loginRequest.getUserName(), loginRequest.getRole());
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return AuthResponse.builder()
                    .JwtToken(token)
                    .message("Login successful")
                    .build();
        } else {
            throw new NoUserFound("Invalid password");
        }
    }

    public AuthResponse promoteUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoUserFound("User not found"));
        user.getRole().add(Role.ADMIN);
        userRepository.save(user);
        return AuthResponse.builder()
                .message("User promoted to admin")
                .build();
    }

    public UserEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByusername(username)
                    .orElseThrow(() -> new NoUserFound("No user found with username: " + username));
        } else {
            throw new NoUserFound("User not authenticated");
        }
    }
}
