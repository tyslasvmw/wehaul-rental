package com.example.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Reservation {
    private Long id;
    private Long truckId;
    private ReservationStatus status;

    public enum ReservationStatus {
        CREATED, STARTED, COMPLETED
    }

    private Reservation(Long truckId, ReservationStatus status) {
        this.truckId = truckId;
        this.status = status;
    }

    public static Reservation makeNewReservation(Long truckId) {
        // TODO: check that this is a valid state transition
        return new Reservation(truckId, ReservationStatus.CREATED);
    }

    public void startReservation() {
        // TODO: check that this is a valid state transition
        this.status = ReservationStatus.STARTED;
    }

    public void completeReservation() {
        // TODO: check that this is a valid state transition
        this.status = ReservationStatus.COMPLETED;
    }
}
