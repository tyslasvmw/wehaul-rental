package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {



	ReservationDTOMapper reservationDTOMapper;

	ReservationService reservationService;


	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot + Tanzu!";
	}

	//TRUCK Creation Event
	//status = "rentable"

	//TRUCK inspected event
	//status = "not rentable"

	//TRUCk finished inspection event
	//status = "rentable"

	// Create a reservation
	//POST person name, status = "reserved"z
	@PostMapping()
	public void reservationCreated(@RequestBody ReservationDTO reservation){
		reservationService.saveReservation(reservationDTOMapper.getReservation(reservation));
	}

	// Truck picked up for specific reservation
	//status = "rented" 

	@PostMapping()
	public void reservationStarted(@RequestParam Long id){
		reservationService.startReservation(id);
	}


	@PostMapping()
	public void reservationCompleted(@RequestParam Long id){
		reservationService.completeReservation(id);
	}


	//  returned call.
	//status= "rentable"
}