package com.movieReservationSystem.Movies.Dtos.Response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoviesAndShowTImes {
    private String title;
    private String description;
    private String posterImage;
    private List<String> genre;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
}
