package com.example.springboot.port.outgoing;

import com.example.springboot.domain.Reservation;

import java.util.List;

public interface GetReservationsDatasourcePort {
    List<Reservation> getReservations();
}
