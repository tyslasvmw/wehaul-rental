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
        return new Reservation(truckId, ReservationStatus.CREATED);
    }

    public void startReservation() {
        this.status = ReservationStatus.STARTED;
    }

    public void completeReservation() {
        this.status = ReservationStatus.COMPLETED;
    }
}
