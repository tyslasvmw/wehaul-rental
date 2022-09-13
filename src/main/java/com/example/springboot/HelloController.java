package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {


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
	public void createReservation(@RequestBody ReservationDTO reservation){
		reservationService.saveReservation();
	}

	// Truck picked up for specific reservation
	//status = "rented" 


	//  returned call.
	//status= "rentable"
}