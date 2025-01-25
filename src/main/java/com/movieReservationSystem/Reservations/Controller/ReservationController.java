package com.movieReservationSystem.Reservations.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieReservationSystem.Reservations.Dtos.Request.ReservationRequest;
import com.movieReservationSystem.Reservations.Dtos.Response.ReservationResponse;
import com.movieReservationSystem.Reservations.Service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PostMapping("/makeReservation")
    public ResponseEntity<?> createReservation(
            @RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);
        return ResponseEntity.ok(reservationResponse);
    }

    @PutMapping("/cancelReservation/{reservationId}")
    public ResponseEntity<?> cancelReservation(
            @PathVariable Long reservationId) {
        ReservationResponse reservationResponse = reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok(reservationResponse);
    }
}
