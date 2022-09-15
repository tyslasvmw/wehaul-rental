package com.example.springboot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalEventRepository extends JpaRepository<RentalEventEntity, Long> {
    Optional<List<RentalEventEntity>> findByPublishedIsNull();
}
