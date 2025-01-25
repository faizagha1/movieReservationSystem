package com.movieReservationSystem.Reservations.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieReservationSystem.Reservations.Model.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

}
