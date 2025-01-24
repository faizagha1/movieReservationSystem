package com.movieReservationSystem.UserAndAuth.Dtos;

import java.util.Set;

import com.movieReservationSystem.UserAndAuth.Entity.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String userName;
    private String password;
    private Set<Role> role;
}
