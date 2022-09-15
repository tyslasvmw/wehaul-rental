package com.example.springboot;

import com.example.springboot.domain.AddReservationService;
import com.example.springboot.domain.Reservation;
import com.example.springboot.domain.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@AllArgsConstructor
public class TruckEventListenerConfig {

    ReservationService reservationService;

    @Bean
    public Consumer<TruckEvent> truckAdded(){

        return truckEvent -> reservationService.addReservation(truckEvent.getTruckId());
    }

}

