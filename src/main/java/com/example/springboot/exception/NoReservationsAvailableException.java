package com.example.springboot.exception;

public class NoReservationsAvailableException extends RuntimeException {
    private static final String NO_RESERVATIONS_AVAILABLE_MESSAGE = "There are no reservations available";

    public NoReservationsAvailableException() {
        super(NO_RESERVATIONS_AVAILABLE_MESSAGE);
    }
}
