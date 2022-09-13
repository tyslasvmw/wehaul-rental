package com.example.springboot.port.incoming;

import lombok.Getter;

public interface AddReservationUseCase {

    Long addReservation(AddReservationCommand addReservationCommand);

    @Getter
    class AddReservationCommand {
        Long truckId;
    }
}
