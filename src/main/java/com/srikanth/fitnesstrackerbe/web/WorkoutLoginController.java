package com.srikanth.fitnesstrackerbe.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srikanth.fitnesstrackerbe.dao.workout.ExerciseDTO;
import com.srikanth.fitnesstrackerbe.dao.workout.TodaysWorkoutDTO;
import com.srikanth.fitnesstrackerbe.domain.Product;
import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.domain.workout.TodaysWorkout;
import com.srikanth.fitnesstrackerbe.service.UserService;
import com.srikanth.fitnesstrackerbe.service.workout.ExerciseService;
import com.srikanth.fitnesstrackerbe.service.workout.TodaysWorkoutTableService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/workouts")
public class WorkoutLoginController {

	@Autowired
	private TodaysWorkoutTableService todaysWorkoutTableService;
	@Autowired
	private ExerciseService exerciseService;
	@Autowired
	private UserService userService;

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

	@GetMapping("/workoutlogins")
	public ResponseEntity<TodaysWorkoutDTO> getTodaysWorkout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String userName = authentication.getName();
			User loggedInUser = (User) userService.loadUserByUsername(userName);
			Integer userId = loggedInUser.getId();
			Map<String, Object> accountDetails = new HashMap<>();
			accountDetails.put("username", userName);
			accountDetails.put("userId", userId);
			System.out.println("@GetMapping(workoutlogins): " + userName + userId);
			Optional<TodaysWorkout> savedTodaysWorkoutOpt = todaysWorkoutTableService.findTodaysWorkoutByUser(userId);
			System.out.println("Get workoutlogins savedTodaysWorkoutOpt: " + savedTodaysWorkoutOpt);

			if (savedTodaysWorkoutOpt.isPresent()) {
				TodaysWorkout savedTodaysWorkout = savedTodaysWorkoutOpt.get();
				TodaysWorkoutDTO todaysWorkoutDTO = todaysWorkoutTableService
						.returnTodaysWorkoutData(savedTodaysWorkout);
				System.out.println("In GET todays workout: " + todaysWorkoutDTO);
				return ResponseEntity.ok(todaysWorkoutDTO);
			} else {
				System.out.println("No Today Records Found or not Present?");
				return ResponseEntity.ok(new TodaysWorkoutDTO());

			}
		} else {
			System.out.println("REJECTED @GetMapping(workoutlogins): ");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping("/workoutlogins")
	public ResponseEntity<TodaysWorkoutDTO> addWorkout(@RequestBody Map<String, Object> workoutData) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Received workout data: " + workoutData);

		if (authentication != null && authentication.isAuthenticated()) {
			System.out.println("in @PostMapping(workoutlogins): ");
			@SuppressWarnings("unchecked")
			Map<String, Object> exerciseData = (Map<String, Object>) workoutData.get("exerciseData");
			Integer userId = (Integer) workoutData.get("userId");

			// Print the extracted data
			System.out.println("User ID: " + userId);
			System.out.println("Exercise Data: " + exerciseData);
			TodaysWorkout savedTodaysWorkout = todaysWorkoutTableService.processTodaysWorkoutData(workoutData);
			TodaysWorkoutDTO todaysWorkoutDTO = todaysWorkoutTableService.returnTodaysWorkoutData(savedTodaysWorkout);

			System.out.println("Client Sent DTO: " + todaysWorkoutDTO);
			return ResponseEntity.ok(todaysWorkoutDTO);
//        return ResponseEntity.ok(new TodaysWorkout());
		} else {
			System.out.println("REJECTED @PostMapping(workoutlogins): ");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
