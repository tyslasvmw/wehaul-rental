package com.example.springboot.port.outgoing;

import com.example.springboot.domain.Reservation;

public interface AddReservationDatasourcePort {
    Long addReservation(Reservation reservation);
}
