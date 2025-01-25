package com.movieReservationSystem.Reservations.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.movieReservationSystem.Movies.Entities.MovieEntity;
import com.movieReservationSystem.Movies.Entities.ShowTimeEntity;
import com.movieReservationSystem.Movies.Repository.MovieRepository;
import com.movieReservationSystem.Movies.Repository.ShowTimeRepository;
import com.movieReservationSystem.Movies.Services.MovieService;
import com.movieReservationSystem.Reservations.Dtos.Request.ReservationRequest;
import com.movieReservationSystem.Reservations.Dtos.Response.ReservationResponse;
import com.movieReservationSystem.Reservations.Model.ReservationEntity;
import com.movieReservationSystem.Reservations.Repository.ReservationRepository;
import com.movieReservationSystem.UserAndAuth.Entity.UserEntity;
import com.movieReservationSystem.UserAndAuth.Repository.UserRepo;
import com.movieReservationSystem.UserAndAuth.Services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepo userRepo;
    private final MovieRepository movieRepository;
    private final ShowTimeRepository showTimeRepository;

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        UserEntity user = userRepo.findById(reservationRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ShowTimeEntity showTime = showTimeRepository.findById(reservationRequest.getShowTimeId())
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));
        MovieEntity movie = movieRepository.findById(showTime.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        if (showTime.getAvailableSeats() < reservationRequest.getNumberOfSeats()) {
            throw new RuntimeException("Not enough seats available");
        }
        ReservationEntity reservation = ReservationEntity
                .builder()
                .user(user)
                .showTime(showTime)
                .seats(reservationRequest.getNumberOfSeats())
                .build();
        showTime.setAvailableSeats(showTime.getAvailableSeats() - reservationRequest.getNumberOfSeats());
        showTimeRepository.save(showTime);
        reservationRepository.save(reservation);
        String message = "Reservation for movie " + movie.getTitle() + " on " + showTime.getDate() + " at "
                + showTime.getStartTime() + " for " + reservationRequest.getNumberOfSeats() + " seats is successful";
        return ReservationResponse
                .builder()
                .movieTitle(movie.getTitle())
                .movieDescription(movie.getDescription())
                .showDate(showTime.getDate())
                .startTime(showTime.getStartTime())
                .endTime(showTime.getEndTime())
                .message(message)
                .build();
    }

    public List<ReservationResponse> getAllReservations() {
        List<ReservationEntity> reservations = reservationRepository.findAll();
        List<ReservationResponse> reservationResponses = reservations.stream().map(
                reservation -> {
                    MovieEntity movie = movieRepository.findById(reservation.getShowTime().getMovie().getId())
                            .orElseThrow(() -> new RuntimeException("Movie not found"));
                    return ReservationResponse
                            .builder()
                            .movieTitle(movie.getTitle())
                            .movieDescription(movie.getDescription())
                            .showDate(reservation.getShowTime().getDate())
                            .startTime(reservation.getShowTime().getStartTime())
                            .endTime(reservation.getShowTime().getEndTime())
                            .message("Reservation for movie " + movie.getTitle() + " on "
                                    + reservation.getShowTime().getDate() + " at "
                                    + reservation.getShowTime().getStartTime() + " for " + reservation.getSeats()
                                    + " seats is successful")
                            .build();
                }).toList();
        return reservationResponses;
    }

    public ReservationResponse cancelReservation(Long reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        ShowTimeEntity showTime = showTimeRepository.findById(reservation.getShowTime().getId())
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));
        reservation.setCancelled(true);
        showTime.setAvailableSeats(showTime.getAvailableSeats() + reservation.getSeats());
        showTimeRepository.save(showTime);
        reservationRepository.save(reservation);
        MovieEntity movie = movieRepository.findById(showTime.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return ReservationResponse
                .builder()
                .movieTitle(movie.getTitle())
                .movieDescription(movie.getDescription())
                .showDate(showTime.getDate())
                .startTime(showTime.getStartTime())
                .endTime(showTime.getEndTime())
                .message("Reservation for movie " + movie.getTitle() + " on " + showTime.getDate() + " at "
                        + showTime.getStartTime() + " for " + reservation.getSeats() + " seats is cancelled")
                .build();
    }
}
