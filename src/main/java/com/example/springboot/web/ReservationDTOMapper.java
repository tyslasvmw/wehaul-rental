package com.example.springboot.web;

import com.example.springboot.domain.Reservation;
import com.example.springboot.persistence.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReservationDTOMapper {

    @Mapping(source = "status", target = "status", qualifiedByName = "reservationStatusToString")
    ReservationDTO getReservationDTO(Reservation reservation);

    Reservation getReservation(ReservationDTO reservationDTO);

    @Named("reservationStatusToString")
    static String reservationStatusToString(Reservation.ReservationStatus status) {
        return status.name();
    }
}
