package com.example.springboot.publisher;

import com.example.springboot.domain.Reservation;
import com.example.springboot.persistence.RentalEventEntity;
import com.example.springboot.persistence.RentalEventRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalEventPublisher {

    private RentalEventRepository rentalEventRepository;
    private StreamBridge streamBridge;

    public RentalEventPublisher(RentalEventRepository rentalEventRepository, StreamBridge streamBridge) {
        this.rentalEventRepository = rentalEventRepository;
        this.streamBridge = streamBridge;
    }

    @Scheduled(fixedRate = 60000)
    public void publish() {
        List<RentalEventEntity> unpublishedEvents = rentalEventRepository.findByPublishedIsNull().get();
        unpublishedEvents.forEach(event -> {
            if(event.getType().equals(Reservation.ReservationStatus.RESERVED)) {
                streamBridge.send()
            }
        });
    }
}
