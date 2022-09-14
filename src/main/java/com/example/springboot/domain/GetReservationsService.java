package com.example.springboot.domain;

import com.example.springboot.persistence.ReservationDatasourceAdapter;
import com.example.springboot.port.incoming.GetAllReservationsUseCase;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetReservationsService implements GetAllReservationsUseCase {

    private final ReservationDatasourceAdapter datasource;
    private final StreamBridge streamBridge;

    public GetReservationsService(ReservationDatasourceAdapter datasource,
                                  StreamBridge streamBridge) {
        this.datasource = datasource;
        this.streamBridge = streamBridge;
    }


    @Override
    public List<Reservation> getReservations() {
        return datasource.getReservations();
    }
}
