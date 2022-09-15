package com.example.springboot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalEventRepository extends JpaRepository<RentalEventEntity, Long> {
}
