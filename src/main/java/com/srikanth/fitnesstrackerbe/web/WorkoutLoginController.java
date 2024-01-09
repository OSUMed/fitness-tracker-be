package com.srikanth.fitnesstrackerbe.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srikanth.fitnesstrackerbe.domain.Product;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/workouts")
public class WorkoutLoginController {
	@GetMapping("/workoutlogins")
	public String addWorkout() {
		System.out.println("We reached addWorkout endpoint!");
		return "Reached addWorkout";

	}

	@PostMapping("/workoutlogins")
    public ResponseEntity<String> handleWorkoutLogin(@RequestBody String jsonInput) {
        // Print the received JSON string
        System.out.println("Received JSON: " + jsonInput);

        // Construct the response
        String responseMessage = "Received the following JSON: " + jsonInput;

        // Return the response
        return ResponseEntity.ok(responseMessage);
    }
}
