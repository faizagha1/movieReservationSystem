package com.movieReservationSystem.Movies.Dtos.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowTimeResponse {
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String movieName;
    private String message;
}
