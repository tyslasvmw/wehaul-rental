package com.example.springboot.domain;

import com.example.springboot.persistence.ReservationDatasourceAdapter;
import com.example.springboot.web.AddReservationCommand;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AddReservationService {

    private final ReservationDatasourceAdapter datasource;
    private final StreamBridge streamBridge;

    public AddReservationService(ReservationDatasourceAdapter datasource,
                                 StreamBridge streamBridge) {
        this.datasource = datasource;
        this.streamBridge = streamBridge;
    }

    @Transactional
    public Long addReservation(AddReservationCommand addReservationCommand) {
        Reservation reservation = Reservation.makeNewReservation(addReservationCommand.getTruckId());
        Long reservationId = datasource.addReservation(reservation);

        streamBridge.send("reservationCreated-out-0", reservationId);

        return reservationId;
    }
}
