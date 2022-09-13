package com.example.springboot.port.incoming;

import lombok.Getter;

public interface StartReservationUseCase {

    Long startReservation(StartReservationCommand startReservationCommand);

    @Getter
    class StartReservationCommand {
        Long reservationId;
    }
}
