package com.example.springboot.domain;

import com.example.springboot.persistence.ReservationDatasourceAdapter;
import com.example.springboot.port.incoming.AddReservationUseCase;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class AddReservationService implements AddReservationUseCase {

    private final ReservationDatasourceAdapter datasource;
    private final StreamBridge streamBridge;

    public AddReservationService(ReservationDatasourceAdapter datasource,
                                 StreamBridge streamBridge) {
        this.datasource = datasource;
        this.streamBridge = streamBridge;
    }

    @Override
    public Long addReservation(AddReservationCommand addReservationCommand) {
        Reservation reservation = Reservation.makeNewReservation(addReservationCommand.getTruckId());
        Long reservationId = datasource.addReservation(reservation);

//        streamBridge.send("reservationCreated", reservationId);

        return reservationId;
    }
}
