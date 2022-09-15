package com.example.springboot.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class FleetEvent {
    private Long truckId;
    private Instant created;
}
