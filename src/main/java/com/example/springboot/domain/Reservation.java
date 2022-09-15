package com.example.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Reservation {
    private Long id;
    private Long truckId;
    private ReservationStatus status;

    public enum ReservationStatus {
        RENTABLE, RESERVED, RENTED, NOT_RENTABLE
    }

    private Reservation(Long truckId, ReservationStatus status) {
        this.truckId = truckId;
        this.status = status;
    }

    public static Reservation makeNewReservation(Long truckId) {
        return new Reservation(truckId, ReservationStatus.RENTABLE);
    }

    public void bookReservation() {
       if (this.status != ReservationStatus.RENTABLE) throw new IllegalStateException();
       this.status = ReservationStatus.RESERVED;
    }

    public void startReservation() {
        if (this.status != ReservationStatus.RESERVED) throw new IllegalStateException();
        this.status = ReservationStatus.RENTED;
    }

    public void completeReservation() {
        if (this.status != ReservationStatus.RENTED) throw new IllegalStateException();
        this.status = ReservationStatus.RENTABLE;
    }

    public void makeReservationNotRentable() {
        this.status = ReservationStatus.NOT_RENTABLE;
    }

    public void makeReservationAvailable() {
        this.status = ReservationStatus.RENTABLE;
    }
}
