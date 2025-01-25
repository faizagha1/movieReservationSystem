package com.movieReservationSystem.Movies.Controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieReservationSystem.Movies.Dtos.Request.MovieAddRequest;
import com.movieReservationSystem.Movies.Dtos.Request.MovieUpdateRequest;
import com.movieReservationSystem.Movies.Dtos.Request.ShowTimeRequest;
import com.movieReservationSystem.Movies.Dtos.Response.MovieAdded;
import com.movieReservationSystem.Movies.Dtos.Response.MovieDeleted;
import com.movieReservationSystem.Movies.Dtos.Response.MovieUpdated;
import com.movieReservationSystem.Movies.Exceptions.MovieNotFound;
import com.movieReservationSystem.Movies.Services.MovieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addMovie(@RequestBody MovieAddRequest movieRequest) {
        MovieAdded movieAdded = movieService.addMovie(movieRequest);
        return ResponseEntity.ok(movieAdded);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long movieId) throws MovieNotFound {
        MovieDeleted movieDeleted = movieService.deleteMovie(movieId);
        return ResponseEntity.ok(movieDeleted);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{movieId}")
    public ResponseEntity<?> updateMovie(
            @RequestBody MovieUpdateRequest movieUpdateRequest,
            @PathVariable Long movieId) throws MovieNotFound {
        MovieUpdated movieUpdated = movieService.updateMovie(movieUpdateRequest, movieId);
        return ResponseEntity.ok(movieUpdated);
    }

    @GetMapping("/all/{movieId}")
    public ResponseEntity<?> getAllShowtimes(@PathVariable Long movieId) throws MovieNotFound {
        return ResponseEntity.ok(movieService.getAllShowtimes(movieId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/showtimes/{movieId}")
    public ResponseEntity<?> addShowtime(@PathVariable Long movieId, @RequestBody ShowTimeRequest request)
            throws MovieNotFound {
        return ResponseEntity.ok(movieService.addShowtime(movieId, request));
    }

    @GetMapping("/showtimes/{movieId}")
    public ResponseEntity<?> getShowtimes(@PathVariable Long movieId) throws MovieNotFound {
        return ResponseEntity.ok(movieService.getAllShowtimes(movieId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMovies(@RequestParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(movieService.getAllMovies(localDate));
    }

}
