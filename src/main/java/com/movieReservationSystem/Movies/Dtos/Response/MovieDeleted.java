package com.movieReservationSystem.Movies.Dtos.Response;

import java.time.LocalDateTime;
import java.util.List;

import com.movieReservationSystem.UserAndAuth.Entity.UserEntity;

import jakarta.persistence.PreUpdate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDeleted {
    private String title;
    private UserEntity deletedBy;
    private String description;
    private String posterImage;
    private List<String> genres;
    private String message;

    private LocalDateTime deletedAt;

    @PreUpdate
    public void setUpdatedAt() {
        this.deletedAt = LocalDateTime.now();
    }
}
