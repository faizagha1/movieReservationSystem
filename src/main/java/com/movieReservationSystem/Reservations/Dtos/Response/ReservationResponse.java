package com.movieReservationSystem.Reservations.Dtos.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationResponse {
    private String movieTitle;
    private String movieDescription;
    private LocalDate showDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String message;
}
