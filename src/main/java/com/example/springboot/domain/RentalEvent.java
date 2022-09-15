package com.example.springboot.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class RentalEvent {
    private Long truckId;
    private Instant created;
    private Instant published;

    private RentalEvent(Long truckId, Instant created, Instant published) {
        this.truckId = truckId;
        this.created = created;
        this.published = published;
    }

    public static RentalEvent makeRentalEvent(Reservation reservation) {
        return new RentalEvent(reservation.getTruckId(), Instant.now(), null);
    }
}
