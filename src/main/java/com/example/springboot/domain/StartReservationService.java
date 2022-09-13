package com.example.springboot.domain;

import com.example.springboot.persistence.ReservationDatasourceAdapter;
import com.example.springboot.port.incoming.AddReservationUseCase;
import com.example.springboot.port.incoming.StartReservationUseCase;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class StartReservationService implements StartReservationUseCase {

    private final ReservationDatasourceAdapter datasource;
    private final StreamBridge streamBridge;

    public StartReservationService(ReservationDatasourceAdapter datasource,
                                   StreamBridge streamBridge) {
        this.datasource = datasource;
        this.streamBridge = streamBridge;
    }

    @Override
    public Long startReservation(StartReservationCommand startReservationCommand) {

        return null;
    }

//    @Override
//    public Long addReservation(AddReservationCommand addReservationCommand) {
//        Reservation reservation = Reservation.makeNewReservation(addReservationCommand.getTruckId());
//        Long reservationId = datasource.addReservation(reservation);
//
////        streamBridge.send("reservationCreated", reservationId);
//
//        return reservationId;
//    }
}
