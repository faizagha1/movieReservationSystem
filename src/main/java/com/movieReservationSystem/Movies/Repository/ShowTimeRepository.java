package com.movieReservationSystem.Movies.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieReservationSystem.Movies.Entities.MovieEntity;
import com.movieReservationSystem.Movies.Entities.ShowTimeEntity;

public interface ShowTimeRepository extends JpaRepository<ShowTimeEntity, Long> {
    List<ShowTimeEntity> findByMovie(MovieEntity movie);

    List<ShowTimeEntity> findByDate(LocalDate date);

}
