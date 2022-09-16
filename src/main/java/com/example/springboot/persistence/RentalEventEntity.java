package com.example.springboot.persistence;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity(name = "rental_event")
@Getter
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

    private RentalEventEntity(Long truckId, String type, Instant created, Instant published) {
        this.truckId = truckId;
        this.type = type;
        this.created = created;
        this.published = published;
    }

    public static RentalEventEntity makeRentalEventEntity(Long truckId, String type) {
        return new RentalEventEntity(truckId, type, Instant.now(), null);
    }
}
