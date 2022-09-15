package com.example.springboot.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity(name = "rental_event")
public class RentalEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long truckId;
    private String type;
    private Instant created;
    private Instant published;

    public RentalEventEntity() {
    }

    public RentalEventEntity(Long truckId, String type, Instant created, Instant published) {
        this.truckId = truckId;
        this.type = type;
        this.created = created;
        this.published = published;
    }
}
