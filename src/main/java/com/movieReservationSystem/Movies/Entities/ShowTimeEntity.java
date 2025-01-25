package com.movieReservationSystem.Movies.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.movieReservationSystem.Reservations.Model.ReservationEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShowTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @OneToMany(mappedBy = "showTime")
    @JoinColumn(name = "reservation_id")
    private Set<ReservationEntity> reservation;

    private Integer availableSeats;

    private LocalDate date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
