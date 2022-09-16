package com.example.springboot.domain;

import java.util.List;
import java.util.Optional;

import com.example.springboot.exception.NoReservationsAvailableException;
import com.example.springboot.exception.TruckAlreadyExistsException;
import com.example.springboot.persistence.ReservationEntity;
import com.example.springboot.persistence.ReservationEntityMapper;
import com.example.springboot.persistence.ReservationRepository;
import com.example.springboot.persistence.RentalEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.springboot.persistence.RentalEventEntity.makeRentalEventEntity;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RentalEventRepository rentalEventRepository;
    private final ReservationEntityMapper reservationEntityMapper;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              RentalEventRepository rentalEventRepository,
                              ReservationEntityMapper reservationEntityMapper) {
        this.reservationRepository = reservationRepository;
        this.rentalEventRepository = rentalEventRepository;
        this.reservationEntityMapper = reservationEntityMapper;
    }

    public Reservation bookReservation() {
        Optional<List<ReservationEntity>> availableReservations =
                reservationRepository.findByStatus(Reservation.ReservationStatus.RENTABLE.name());
        if (availableReservations.isEmpty()) throw new NoReservationsAvailableException();

        Reservation reservation = reservationEntityMapper.getReservation(availableReservations.get().get(0));
        reservation.bookReservation();
        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
        saveRentalEvent(reservation);

        return reservation;
    }

    public void startReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservation.startReservation();

        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
        saveRentalEvent(reservation);
    }

    public void completeReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservation.completeReservation();

        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
        saveRentalEvent(reservation);
    }

    public void addReservation(Long truckId) {
        Optional<List<ReservationEntity>> reservationEntities = reservationRepository.findByTruckId(truckId);
        if (reservationEntities.isPresent() && reservationEntities.get().size() > 0) {
            throw new TruckAlreadyExistsException(truckId);
        }

        Reservation reservation = Reservation.makeNewReservation(truckId);
        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
        rentalEventRepository.save(makeRentalEventEntity(reservation.getTruckId(), "CREATED"));
    }
    public void makeReservationNotRentable(Long truckId) {
        Reservation reservation = getReservationByTruckId(truckId);
        reservation.makeReservationNotRentable();
        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
    }

    public void makeReservationAvailable(Long truckId) {
        Reservation reservation = getReservationByTruckId(truckId);
        reservation.makeReservationAvailable();
        reservationRepository.save(reservationEntityMapper.getReservationEntity(reservation));
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

    private void saveRentalEvent(Reservation reservation) {
        rentalEventRepository.save(makeRentalEventEntity(reservation.getTruckId(), reservation.getStatus().name()));
    }
}
