package com.example.springboot;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.function.StreamBridge;

@Service
public class ReservationService {

    private final StreamBridge streamBridge;
    ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, StreamBridge streamBridge) {
        this.reservationRepository = reservationRepository;
        this.streamBridge = streamBridge;
    }

    public Long saveReservation(Reservation reservation){
        streamBridge.send("reservationCreated", "this is our fake reservation string");
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
