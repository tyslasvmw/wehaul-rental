package com.example.springboot;

public class ReservationDTO {

    public ReservationDTO(Long truckId, String reservationStatus) {
        this.truckId = truckId;
        this.reservationStatus = reservationStatus;
    }
    private Long truckId;
    private String reservationStatus;
    
}
