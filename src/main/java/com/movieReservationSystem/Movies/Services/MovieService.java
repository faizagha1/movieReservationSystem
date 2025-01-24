package com.movieReservationSystem.Movies.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.movieReservationSystem.Movies.Dtos.Request.MovieAddRequest;
import com.movieReservationSystem.Movies.Dtos.Request.MovieUpdateRequest;
import com.movieReservationSystem.Movies.Dtos.Response.MovieAdded;
import com.movieReservationSystem.Movies.Dtos.Response.MovieDeleted;
import com.movieReservationSystem.Movies.Dtos.Response.MovieUpdated;
import com.movieReservationSystem.Movies.Entities.MovieEntity;
import com.movieReservationSystem.Movies.Entities.ShowTimeEntity;
import com.movieReservationSystem.Movies.Exceptions.MovieNotFound;
import com.movieReservationSystem.Movies.Repository.MovieRepository;
import com.movieReservationSystem.Movies.Repository.ShowTimeRepository;
import com.movieReservationSystem.UserAndAuth.Entity.UserEntity;
import com.movieReservationSystem.UserAndAuth.Services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {
        private final MovieRepository movieRepository;
        private final ShowTimeRepository showTimeRepository;
        private final UserService userService;

        public MovieAdded addMovie(MovieAddRequest movieRequest) {
                List<ShowTimeEntity> showTimes = new ArrayList<>();
                MovieEntity movie = MovieEntity.builder()
                                .title(movieRequest.getTitle())
                                .description(movieRequest.getDescription())
                                .posterImage(movieRequest.getPosterImage())
                                .genres(movieRequest.getGenres())
                                .showTimes(showTimes)
                                .build();
                movieRepository.save(movie);
                String message = "Movie titled '" + movieRequest.getTitle() + "' with genres "
                                + movieRequest.getGenres()
                                + " and description '" + movieRequest.getDescription()
                                + "' has been successfully added.";
                MovieAdded movieAdded = MovieAdded.builder()
                                .title(movie.getTitle())
                                .description(movie.getDescription())
                                .posterImage(movie.getPosterImage())
                                .genres(movie.getGenres())
                                .message(message)
                                .build();
                return movieAdded;
        }

        public MovieDeleted deleteMovie(Long movieId) throws MovieNotFound {
                UserEntity user = userService.getCurrentUser();

                MovieEntity movie = movieRepository.findById(movieId)
                                .orElseThrow(() -> new MovieNotFound("Movie with id " + movieId + " not found"));
                String message = "Movie titled '" + movie.getTitle() + "' with genres " + movie.getGenres()
                                + " and description '" + movie.getDescription()
                                + "' has been successfully deleted by admin"
                                + user.getUsername();
                MovieDeleted movieDeleted = MovieDeleted.builder()
                                .title(movie.getTitle())
                                .deletedBy(user)
                                .description(movie.getDescription())
                                .posterImage(movie.getPosterImage())
                                .genres(movie.getGenres())
                                .message(message)
                                .build();
                movieRepository.delete(movie);
                if (movieRepository.existsById(movieId)) {
                        throw new RuntimeException("Movie with id " + movieId + " not deleted");
                }else {
                        return movieDeleted;
                }
        }

        public MovieUpdated updateMovie(MovieUpdateRequest movieUpdateRequest, Long movieId) throws MovieNotFound {
                UserEntity user = userService.getCurrentUser();

                MovieEntity movie = movieRepository.findById(movieId)
                                .orElseThrow(() -> new MovieNotFound("Movie with id " + movieId + " not found"));
                movie.setTitle(movieUpdateRequest.getTitle());
                movie.setDescription(movieUpdateRequest.getDescription());
                movie.setPosterImage(movieUpdateRequest.getPosterImage());
                movie.setGenres(movieUpdateRequest.getGenres());
                movieRepository.save(movie);
                String message = "Movie titled '" + movieUpdateRequest.getTitle() + "' with genres "
                                + movieUpdateRequest.getGenres() + " and description '"
                                + movieUpdateRequest.getDescription()
                                + "' has been successfully updated with new details by admin"
                                + userService.getCurrentUser().getUsername();
                MovieUpdated movieUpdated = MovieUpdated.builder()
                                .title(movie.getTitle())
                                .updatedBy(user)
                                .description(movie.getDescription())
                                .posterImage(movie.getPosterImage())
                                .genres(movie.getGenres())
                                .message(message)
                                .build();
                return movieUpdated;
        }
}
