package com.example.springboot.port.incoming;

import com.example.springboot.domain.Reservation;

import java.util.List;

public interface GetAllReservationsUseCase {

    List<Reservation> getReservations();
}
