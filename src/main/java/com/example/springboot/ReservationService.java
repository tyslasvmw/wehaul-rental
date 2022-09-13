package com.example.springboot;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long saveReservation(Reservation reservation){
        return reservationRepository.save(reservation).getId();
    }

    public void startReservation(Long reservationId){
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isEmpty()) throw new IllegalArgumentException();

        reservation.get().startReservation();
    }

    public void completeReservation(Long reservationId){
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isEmpty()) throw new IllegalArgumentException();

        reservation.get().completeReservation();
    }
    
}
