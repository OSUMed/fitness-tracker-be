package com.srikanth.fitnesstrackerbe.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/hello")
	public ResponseEntity<String> userEndpoints() {
		System.out.println("Hello you reached admin endpoint");
	    return ResponseEntity.ok("Admin Endpoint reached");
	}
	
	@GetMapping("/users")
	public ResponseEntity<String> getAllUsers() {
		// Simple message for testing
		String message = "List of all users would be here.!!";
		
		// Return the message with an OK (200) status
		return ResponseEntity.ok(message);
	}
	@GetMapping("/private")
    public ResponseEntity<Map<String, String>> getPrivate() {
        Map<String, String> response = new HashMap<>();
        response.put("username", "George");

        return ResponseEntity.ok(response);
    }

}