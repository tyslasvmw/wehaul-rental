package com.example.springboot.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservationDTO {
    private Long id;
    private Long truckId;
    private String status;
}
