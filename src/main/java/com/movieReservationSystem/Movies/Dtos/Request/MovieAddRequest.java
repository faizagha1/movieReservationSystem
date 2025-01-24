package com.movieReservationSystem.Movies.Dtos.Request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieAddRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String posterImage;

    private List<String> genres;
}
