package com.example.springboot.web;

import com.example.springboot.domain.GetReservationsService;
import com.example.springboot.domain.ReservationService;
import com.example.springboot.domain.StartReservationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

	private final ReservationService reservationService;
	private final GetReservationsService getReservationsService;
	private final StartReservationService startReservationService;
	private final ReservationDTOMapper mapper;

	public ReservationController(ReservationService reservationService,
								 GetReservationsService getReservationsService,
								 StartReservationService startReservationService,
								 ReservationDTOMapper mapper) {
		this.reservationService = reservationService;
		this.getReservationsService = getReservationsService;
		this.startReservationService = startReservationService;
		this.mapper = mapper;
	}

	@PostMapping
	public ResponseEntity<ReservationDTO> reservationBooked() {
		ReservationDTO dto = mapper.getReservationDTO(reservationService.bookReservation());
		return ResponseEntity.created(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}").buildAndExpand(dto).toUri()).build();
	}

	@PostMapping("/{id}/start")
	public void reservationStarted(@PathVariable Long id) {
		reservationService.startReservation(id);
	}

	@PostMapping("/{id}/complete")
	public void reservationCompleted(@PathVariable Long id) {
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
}
