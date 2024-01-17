package com.srikanth.fitnesstrackerbe.service.workout;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.domain.workout.Exercise;
import com.srikanth.fitnesstrackerbe.domain.workout.TodaysWorkout;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;
import com.srikanth.fitnesstrackerbe.repository.workout.TodaysWorkoutRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import com.srikanth.fitnesstrackerbe.dao.workout.*;

@Service
public class TodaysWorkoutTableService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TodaysWorkoutRepository todaysWorkoutRepository;
	@Autowired
	private ExerciseService exerciseService;

	public TodaysWorkout processTodaysWorkoutData(Map<String, Object> fullExerciseData) {
		ExerciseDTO exerciseDTO = exerciseService.convertDataToExerciseDTO(fullExerciseData);
		System.out.println("final exerciseDTO: " + exerciseDTO + " exerciseDTO's userId: "
				+ exerciseDTO.getUserId());
		Exercise exercise = exerciseService.convertExerciseDTOToExercise(exerciseDTO);
		System.out.println("exercise domain: " + exercise);
		User user = userRepository.findById(exerciseDTO.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		java.sql.Date todaySqlDate = new java.sql.Date(System.currentTimeMillis());


		Optional<TodaysWorkout> existingWorkout = todaysWorkoutRepository.findByUserIdAndDate(user.getId(), todaySqlDate);

		TodaysWorkout todaysWorkout;
		if (existingWorkout.isPresent()) {
			todaysWorkout = existingWorkout.get();
		} else {
			todaysWorkout = new TodaysWorkout();
			todaysWorkout.setUserId(user.getId());
			todaysWorkout.setDate(todaySqlDate);
			todaysWorkout.setExercises(new ArrayList<Exercise>());
		}
		
		// Add the exercise to today's workout
		exercise.setWorkout(todaysWorkout);
		todaysWorkout.getExercises().add(exercise);

		System.out.println("Before repo saved todays workout is: " + todaysWorkout);
		// Save today's workout
		todaysWorkoutRepository.save(todaysWorkout);

//		return todaysWorkout;
		return new TodaysWorkout();
	}

	public TodaysWorkout addExerciseToTodayWorkout(Map<String, Object> workoutData) {

		return new TodaysWorkout();
	}

//	public TodaysWorkoutDTO addExercise(TodaysWorkoutDTO todaysWorkoutDTO) {
//		// Convert exerciseDTO to domain entity if necessary
//		TodaysWorkout todayWorkout = convertToEntity(todaysWorkoutDTO);
//		// Check if there's an existing workout for today
//		Optional<TodaysWorkout> existingWorkout = todaysWorkoutRepository
//				.findByUserIdAndDate(todaysWorkoutDTO.getUserId(), todaysWorkoutDTO.getDate());
//
//		System.out.println("Returned workout is: " + existingWorkout);
//
//		if (existingWorkout.isPresent()) {
//			// Logic to add the exercise to the existing workout
//		} else {
//			// Logic to create a new workout
//		}
//		// Add exercise to the workout
//		// Save updated workout in the database
//		// Convert the updated workout to TodaysWorkoutDTO
//		// Return the DTO
//
//		// Delete Line
//		return new TodaysWorkoutDTO(null, null, null);
//
//	}

	private TodaysWorkout convertToEntity(TodaysWorkoutDTO todaysWorkoutDTO) {
		TodaysWorkout todayWorkout = new TodaysWorkout();
		todayWorkout.setUserId(todaysWorkoutDTO.getUserId());
		Date sqlDate = new Date(todaysWorkoutDTO.getDate().getTime());
		todayWorkout.setDate(sqlDate);

		// Convert each ExerciseDTO to an Exercise entity and add it to the workout
//	    List<Exercise> exercises = todaysWorkoutDTO.getExercises().stream()
//	        .map(this::convertExerciseDtoToEntity)
//	        .collect(Collectors.toList());
//	    todayWorkout.setExercises(exercises);

		return new TodaysWorkout();
	}
}