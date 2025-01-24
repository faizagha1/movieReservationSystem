package com.movieReservationSystem.Movies.Dtos.Response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieAdded {
    private String title;
    private String description;
    private String posterImage;
    private List<String> genres;
    private String message;
}
