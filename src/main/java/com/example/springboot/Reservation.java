package com.example.springboot;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

@Entity
public class Reservation {
    
    @Id
    private Long id;
    private Long truckId;
    private String reservationStatus;

    

    public Reservation(Long truckId, String reservationStatus) {
        this.truckId = truckId;
        this.reservationStatus = reservationStatus;
    }


    public static Reservation makeNewReservation(Long truckId) {
        Reservation reservation = new Reservation(truckId, "PENDING");
        return reservation;
    }

    public void startReservation(){
        this.reservationStatus = "STARTED";
    }

    public void completeReservation(){
        this.reservationStatus = "COMPLETED";
    }

    public Long getId() {
        return id;
    }

    public Long getTruckId() {
        return truckId;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }


}
