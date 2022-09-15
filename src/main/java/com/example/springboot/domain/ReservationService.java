package com.example.springboot.domain;

import java.util.List;
import java.util.Optional;

import com.example.springboot.TruckEvent;
import com.example.springboot.exception.NoReservationsAvailableException;
import com.example.springboot.exception.TruckAlreadyExistsException;
import com.example.springboot.persistence.ReservationEntity;
import com.example.springboot.persistence.ReservationEntityMapper;
import com.example.springboot.persistence.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.function.StreamBridge;

@Service
public class ReservationService {

    private final StreamBridge streamBridge;
    private final ReservationRepository reservationRepository;
    private final ReservationEntityMapper reservationEntityMapper;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              ReservationEntityMapper reservationEntityMapper,
                              StreamBridge streamBridge) {
        this.reservationRepository = reservationRepository;
        this.reservationEntityMapper = reservationEntityMapper;
        this.streamBridge = streamBridge;
    }

    public Reservation bookReservation() {
        Optional<List<ReservationEntity>> availableReservations =
                reservationRepository.findByStatus(Reservation.ReservationStatus.RENTABLE);
        if (availableReservations.isEmpty()) throw new NoReservationsAvailableException();

        Reservation reservation = reservationEntityMapper.getReservation(availableReservations.get().get(0));
        reservation.bookReservation();
        streamBridge.send("reservationBooked-out-0", new TruckEvent(reservation.getTruckId()));
        return reservation;
    }

    public void startReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservation.startReservation();

        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
        streamBridge.send("reservationStarted-out-0", new TruckEvent(reservation.getTruckId()));
    }

    public void completeReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservation.completeReservation();

        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
        streamBridge.send("reservationEnded-out-0", new TruckEvent(reservation.getTruckId()));
    }

    public void addReservation(Long truckId) {
        Optional<List<ReservationEntity>> reservationEntities = reservationRepository.findByTruckId(truckId);
        if (reservationEntities.isPresent() && reservationEntities.get().size() > 0) {
            throw new TruckAlreadyExistsException(truckId);
        }

        Reservation reservation = Reservation.makeNewReservation(truckId);
        ReservationEntity newReservation = reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));

        streamBridge.send("reservationCreated-out-0", new TruckEvent(newReservation.getTruckId()));
    }
    public void makeReservationNotRentable(Long truckId) {
        Reservation reservation = getReservationByTruckId(truckId);
        reservation.makeReservationNotRentable();
    }

    public void makeReservationAvailable(Long truckId) {
        Reservation reservation = getReservationByTruckId(truckId);
        reservation.makeReservationAvailable();
    }

    private Reservation getReservationById(Long reservationId) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.findById(reservationId);
        if (reservationEntity.isEmpty()) throw new IllegalArgumentException();

        return reservationEntityMapper.getReservation(reservationEntity.get());
    }

    private Reservation getReservationByTruckId(Long truckId) {
        Optional<List<ReservationEntity>> reservationEntity = reservationRepository.findByTruckId(truckId);
        if (reservationEntity.isEmpty()) throw new IllegalArgumentException();

        return reservationEntityMapper.getReservation(reservationEntity.get().get(0));
    }
}
