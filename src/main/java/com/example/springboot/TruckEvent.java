package com.example.springboot;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class TruckEvent {
    private Long truckId;
    private Instant created;
}

