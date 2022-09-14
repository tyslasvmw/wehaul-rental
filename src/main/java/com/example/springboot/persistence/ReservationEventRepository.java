package com.example.springboot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationEventRepository extends JpaRepository<ReservationEventEntity, Long> {

}
