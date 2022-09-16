package com.example.springboot.publisher;

import com.example.springboot.domain.RentalEvent;
import com.example.springboot.domain.Reservation;
import com.example.springboot.persistence.RentalEventEntity;
import com.example.springboot.persistence.RentalEventRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RentalEventPublisher {

    private final RentalEventRepository rentalEventRepository;
    private final StreamBridge streamBridge;
    private static Map<Reservation.ReservationStatus, String> exchangeMap;

    public RentalEventPublisher(RentalEventRepository rentalEventRepository, StreamBridge streamBridge) {
        this.rentalEventRepository = rentalEventRepository;
        this.streamBridge = streamBridge;
        exchangeMap = instantiateExchangeMap();
    }

    @Scheduled(fixedRate = 60000)
    public void publishReactionToFleetEvent() {
        Optional<List<RentalEventEntity>> unpublishedEvents = rentalEventRepository.findByPublishedIsNullAndTypeIs("CREATED");

        unpublishedEvents.ifPresent(rentalEventEntities -> rentalEventEntities
                .forEach(eventEntity -> streamBridge
                        .send("reservationCreated-out-0", RentalEvent.makeRentalEventFromEntity(eventEntity))));
    }

    @Scheduled(fixedRate = 60000)
    public void publishRentalEvent() {
        Optional<List<RentalEventEntity>> unpublishedEvents = rentalEventRepository.findByPublishedIsNullAndTypeIsNot("CREATED");

        unpublishedEvents.ifPresent(rentalEventEntities ->
                rentalEventEntities.forEach(eventEntity -> {
                    String exchangeName = exchangeMap.get(Reservation.ReservationStatus.valueOf(eventEntity.getType()));
                    streamBridge.send(exchangeName, RentalEvent.makeRentalEventFromEntity(eventEntity));
                }));
    }

    private static Map<Reservation.ReservationStatus, String> instantiateExchangeMap() {
        exchangeMap = new HashMap<>();

        exchangeMap.put(Reservation.ReservationStatus.RESERVED, "reservationBooked-out-0");
        exchangeMap.put(Reservation.ReservationStatus.RENTED, "reservationStarted-out-0");
        exchangeMap.put(Reservation.ReservationStatus.RENTABLE, "reservationEnded-out-0");

        return exchangeMap;
    }
}
