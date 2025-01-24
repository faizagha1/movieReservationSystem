package com.movieReservationSystem.UserAndAuth.Dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupRequest {
    private String fullName;
    private String userName;
    private String email;
    private String password;
}
