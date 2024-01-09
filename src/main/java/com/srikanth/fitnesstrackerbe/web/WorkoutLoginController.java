package com.srikanth.fitnesstrackerbe.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srikanth.fitnesstrackerbe.dao.workout.TodaysWorkoutDTO;
import com.srikanth.fitnesstrackerbe.domain.Product;
import com.srikanth.fitnesstrackerbe.service.workout.TodayWorkoutTableService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/workouts")
public class WorkoutLoginController {
	
    @Autowired
    private TodayWorkoutTableService todayWorkoutService;
    
    
	@GetMapping("/workoutlogins")
	public String addWorkout() {
		System.out.println("We reached addWorkout endpoint!");
		return "Reached addWorkout";

	}

//	@PostMapping("/workoutlogins")
//	public ResponseEntity<String> handleWorkoutLogin(@RequestBody String jsonInput) {
//		// Print the received JSON string
//		System.out.println("Received JSON: " + jsonInput);
//
//		// Construct the response
//		String responseMessage = "Received the following JSON: " + jsonInput;
//
//		// Return the response
//		return ResponseEntity.ok(responseMessage);
//	}

	@PostMapping("/workoutlogins")
	public ResponseEntity<?> addWorkout(@RequestBody Map<String, Object> workoutData) {
	    System.out.println("Received workout data: " + workoutData);

	    @SuppressWarnings("unchecked")
		Map<String, Object> exerciseData = (Map<String, Object>) workoutData.get("exerciseData");
	    Integer userId = (Integer) workoutData.get("userId");
	    
	    // Print the extracted data
	    System.out.println("User ID: " + userId);
	    System.out.println("Exercise Data: " + exerciseData);
	    TodaysWorkoutDTO todaysWorkoutDTO = todayWorkoutService.processWorkoutData(workoutData);
        System.out.println("Mapped DTO: " + todaysWorkoutDTO);


	    return ResponseEntity.ok("Workout data received");
	}

}