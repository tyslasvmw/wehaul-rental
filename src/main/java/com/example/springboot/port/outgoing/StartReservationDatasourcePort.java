package com.example.springboot.port.outgoing;

import com.example.springboot.domain.Reservation;

public interface StartReservationDatasourcePort {
    Long addReservation(Reservation reservation);
}
