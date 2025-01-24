package com.movieReservationSystem.Movies.Dtos.Request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieUpdateRequest {
    private String title;
    private String description;
    private String posterImage;
    private List<String> genres;
}
