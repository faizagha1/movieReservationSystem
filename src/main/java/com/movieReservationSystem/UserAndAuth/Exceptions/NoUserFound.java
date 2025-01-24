package com.movieReservationSystem.UserAndAuth.Exceptions;

public class NoUserFound extends RuntimeException {
    public NoUserFound(String message) {
        super(message);
    }
}
