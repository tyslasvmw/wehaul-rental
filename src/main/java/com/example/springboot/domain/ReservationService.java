package com.example.springboot.domain;

import java.util.List;
import java.util.Optional;

import com.example.springboot.TruckEvent;
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
    private final ReservationRepository reservationRepository;
    private final ReservationEntityMapper reservationEntityMapper;

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
        streamBridge.send("reservationStarted-out-0", new TruckEvent(reservation.getTruckId()));
    }

    public void completeReservation(Long reservationId) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.findById(reservationId);
        if (reservationEntity.isEmpty()) throw new IllegalArgumentException();

        Reservation reservation = reservationEntityMapper.getReservation(reservationEntity.get());
        reservation.completeReservation();

        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
        streamBridge.send("reservationEnded-out-0", new TruckEvent(reservation.getTruckId()));
    }

    public void addReservation(Long truckId) {
        // check that reservation does not already exist
        List<ReservationEntity> reservationEntities = reservationRepository.findByTruckId(truckId);
        
        Reservation reservation = Reservation.makeNewReservation(truckId);

        ReservationEntity newReservation = reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));

        streamBridge.send("reservationCreated-out-0", new TruckEvent(newReservation.getTruckId()));
    }

}
