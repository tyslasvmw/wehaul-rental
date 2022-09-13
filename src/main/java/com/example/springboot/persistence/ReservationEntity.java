package com.example.springboot.persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "reservation")
@Data
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long truckId;
    private String status;

    public ReservationEntity() {}

    public Long getId() {
        return id;
    }

    public Long getTruckId() {
        return truckId;
    }

    public String getStatus() {
        return status;
    }


}
