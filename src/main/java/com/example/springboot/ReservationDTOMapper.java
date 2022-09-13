package com.example.springboot;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationDTOMapper {

    ReservationDTO getReservationDTO(Reservation reservation);

    Reservation getReservation(ReservationDTO reservationDTO);

}
