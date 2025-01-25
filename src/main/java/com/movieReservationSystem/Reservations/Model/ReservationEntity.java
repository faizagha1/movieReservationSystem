package com.movieReservationSystem.Reservations.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.movieReservationSystem.Movies.Entities.ShowTimeEntity;
import com.movieReservationSystem.UserAndAuth.Entity.UserEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer seats;

    private boolean isCancelled;

    @ManyToOne
    private ShowTimeEntity showTime;

    @ManyToOne
    private UserEntity user;
}
