package com.example.springboot.persistence;

import com.example.springboot.domain.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReservationEntityMapper {

    @Mapping(source = "status", target = "status", qualifiedByName = "reservationStatusToString")
    ReservationEntity getReservationEntity(Reservation reservation);

    @Mapping(source = "status", target = "status", qualifiedByName = "stringToReservationStatus")
    Reservation getReservation(ReservationEntity reservationEntity);

    @Named("reservationStatusToString")
    static String reservationStatusToString(Reservation.ReservationStatus status) {
        return status.name();
    }

    @Named("stringToReservationStatus")
    static Reservation.ReservationStatus stringToReservationStatus(String status) {
        return Reservation.ReservationStatus.valueOf(status);
    }
}
