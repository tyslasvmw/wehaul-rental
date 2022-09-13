package com.example.springboot.web;

import com.example.springboot.domain.GetReservationsService;
import com.example.springboot.domain.ReservationService;
import com.example.springboot.port.incoming.AddReservationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

	private final ReservationService reservationService;
	private final AddReservationUseCase addReservationUseCase;
	private final GetReservationsService getReservationsService;
	private final ReservationDTOMapper mapper;

	@Autowired
	public ReservationController(ReservationService reservationService, AddReservationUseCase addReservationUseCase, GetReservationsService getReservationsService, ReservationDTOMapper mapper) {
		this.reservationService = reservationService;
		this.addReservationUseCase = addReservationUseCase;
		this.getReservationsService = getReservationsService;
		this.mapper = mapper;
	}

	//TRUCK Creation Event
	//status = "rentable"

	//TRUCK inspected event
	//status = "not rentable"

	//TRUCk finished inspection event
	//status = "rentable"

	// Create a reservation
	//POST person name, status = "reserved"z
	@PostMapping
	public ResponseEntity<Void> reservationCreated(@RequestBody AddReservationUseCase.AddReservationCommand cmd) {
		Long reservationId = addReservationUseCase.addReservation(cmd);
		return ResponseEntity.created(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}").buildAndExpand(reservationId).toUri()).build();
	}

	// Truck picked up for specific reservation
	//status = "rented"

	@PostMapping("/start/{id}")
	public void reservationStarted(@PathVariable Long id){
		reservationService.startReservation(id);
	}


	@PostMapping("/complete/{id}")
	public void reservationCompleted(@PathVariable Long id){
		reservationService.completeReservation(id);
	}

	@GetMapping
	public ResponseEntity<List<ReservationDTO>> getAllReservations() {
		List<ReservationDTO> reservations = getReservationsService.getReservations().stream()
				.map(mapper::getReservationDTO)
				.collect(Collectors.toList());

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(reservations);
	}


	//  returned call.
	//status= "rentable"
}