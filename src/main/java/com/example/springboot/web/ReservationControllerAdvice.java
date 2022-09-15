package com.example.springboot.web;

import com.example.springboot.exception.NoReservationsAvailableException;
import com.example.springboot.exception.TruckAlreadyExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReservationControllerAdvice {

    @ExceptionHandler(NoReservationsAvailableException.class)
    public ResponseEntity<String> handleNoReservationsAvailable(
            NoReservationsAvailableException noReservationsAvailableException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_PLAIN)
                .body((noReservationsAvailableException.getMessage()));
    }

    @ExceptionHandler(TruckAlreadyExistsException.class)
    public ResponseEntity<String> handleTruckAlreadyExists(
            TruckAlreadyExistsException truckAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_PLAIN)
                .body((truckAlreadyExistsException.getMessage()));
    }
}
