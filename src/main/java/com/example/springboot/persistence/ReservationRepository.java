package com.example.springboot.persistence;
import com.example.springboot.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    Optional<List<ReservationEntity>> findByTruckId(Long truckId);

    Optional<List<ReservationEntity>> findByStatus(Reservation.ReservationStatus status);
}
