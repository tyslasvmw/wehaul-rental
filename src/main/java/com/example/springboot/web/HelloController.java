package com.example.springboot.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HelloController {

	@GetMapping
	public ResponseEntity<Map<String, String>> getAllReservations() {
		Map<String, String> helloMsg = new HashMap<>();
		helloMsg.put("message", "hello from wehaul reservations");
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(helloMsg);
	}
}