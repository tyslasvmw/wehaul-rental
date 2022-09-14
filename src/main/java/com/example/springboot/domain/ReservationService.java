package com.example.springboot.domain;

import java.util.Optional;

import com.example.springboot.persistence.ReservationEntity;
import com.example.springboot.persistence.ReservationEntityMapper;
import com.example.springboot.persistence.ReservationRepository;
import com.example.springboot.web.AddReservationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.function.StreamBridge;

@Service
public class ReservationService {

    private final StreamBridge streamBridge;
    private ReservationRepository reservationRepository;
    private ReservationEntityMapper reservationEntityMapper;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationEntityMapper reservationEntityMapper, StreamBridge streamBridge) {
        this.reservationRepository = reservationRepository;
        this.reservationEntityMapper = reservationEntityMapper;
        this.streamBridge = streamBridge;
    }

    public void startReservation(Long reservationId) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.findById(reservationId);
        if (reservationEntity.isEmpty()) throw new IllegalArgumentException();

        Reservation reservation = reservationEntityMapper.getReservation(reservationEntity.get());
        reservation.startReservation();

        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
        streamBridge.send("reservationStarted-out-0", reservation.getTruckId());
    }

    public void completeReservation(Long reservationId) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.findById(reservationId);
        if (reservationEntity.isEmpty()) throw new IllegalArgumentException();

        Reservation reservation = reservationEntityMapper.getReservation(reservationEntity.get());
        reservation.completeReservation();

        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
        streamBridge.send("reservationEnded-out-0", reservation.getTruckId());
    }

    public Long addReservation(Long truckId) {
        Reservation reservation = Reservation.makeNewReservation(truckId);
        ReservationEntity reservationEntity = reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));

        streamBridge.send("reservationCreated-out-0", reservationEntity.getTruckId());

        return reservationEntity.getTruckId();
    }

}
