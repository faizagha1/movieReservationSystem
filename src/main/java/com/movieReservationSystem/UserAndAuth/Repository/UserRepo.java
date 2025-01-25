package com.movieReservationSystem.UserAndAuth.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieReservationSystem.UserAndAuth.Entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByusername(String UserName);

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByusername(String UserName);

}
