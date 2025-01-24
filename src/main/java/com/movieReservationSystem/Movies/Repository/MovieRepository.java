package com.movieReservationSystem.Movies.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieReservationSystem.Movies.Entities.MovieEntity;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    MovieEntity findByTitle(String title);

}
