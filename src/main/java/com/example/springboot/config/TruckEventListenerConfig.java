package com.example.springboot.config;

import com.example.springboot.domain.FleetEvent;
import com.example.springboot.domain.ReservationService;
import com.example.springboot.domain.RentalEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@AllArgsConstructor
public class TruckEventListenerConfig {

    ReservationService reservationService;

    @Bean
    public Consumer<FleetEvent> truckAdded(){
        return fleetEvent -> reservationService.addReservation(fleetEvent.getTruckId());
    }

    @Bean
    public Consumer<FleetEvent> truckInspectionStarted(){
        return fleetEvent -> reservationService.makeReservationNotRentable(fleetEvent.getTruckId());
    }

    @Bean
    public Consumer<FleetEvent> truckInspectionCompleted(){
        return fleetEvent -> reservationService.makeReservationRentable(fleetEvent.getTruckId());
    }
}

