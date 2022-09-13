package com.example.springboot;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationDTO {

    public ReservationDTO(Long truckId, String reservationStatus) {
        this.truckId = truckId;
        this.reservationStatus = reservationStatus;
    }
    private Long truckId;
    private String reservationStatus;
}
