package com.example.springboot.persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity(name = "reservationEvent")
@Data
public class ReservationEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long truckId;
    private EventType eventType;
    private Instant createdDate;
    private Instant publishedDate;

    public enum EventType {
        ADDED
    }
}
