package com.example.springboot.exception;

public class TruckAlreadyExistsException extends RuntimeException {

    private static final String TRUCK_ALREADY_EXISTS_MESSAGE = "Truck with id: %s is already in the inventory";

    public TruckAlreadyExistsException(Long id) {
        super(String.format(TRUCK_ALREADY_EXISTS_MESSAGE, id));
    }
}
