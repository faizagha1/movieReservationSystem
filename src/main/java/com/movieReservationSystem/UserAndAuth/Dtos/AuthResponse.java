package com.movieReservationSystem.UserAndAuth.Dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String JwtToken;
    private String message;
}
