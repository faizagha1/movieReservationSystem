package com.movieReservationSystem.Movies.Dtos.Request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowTimeRequest {
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
