package com.movieReservationSystem.Reservations.Dtos.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationRequest {
    private Long showTimeId;
    private Long userId;
    private Integer numberOfSeats;
}
