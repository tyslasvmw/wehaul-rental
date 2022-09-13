package com.example.springboot.persistence;

import com.example.springboot.domain.Reservation;
import com.example.springboot.port.outgoing.AddReservationDatasourcePort;
import com.example.springboot.port.outgoing.GetReservationsDatasourcePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationDatasourceAdapter implements AddReservationDatasourcePort,
        GetReservationsDatasourcePort {
    private ReservationRepository reservationRepository;
    private ReservationEntityMapper mapper;

    public ReservationDatasourceAdapter(ReservationRepository reservationRepository, ReservationEntityMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.mapper = mapper;
    }

    @Override
    public Long addReservation(Reservation reservation) {
        ReservationEntity entity = reservationRepository.save(mapper.getReservationEntity(reservation));
        return entity.getId();
    }

    @Override
    public List<Reservation> getReservations() {
        return reservationRepository.findAll().stream()
                .map(mapper::getReservation)
                .collect(Collectors.toList());
    }
}
