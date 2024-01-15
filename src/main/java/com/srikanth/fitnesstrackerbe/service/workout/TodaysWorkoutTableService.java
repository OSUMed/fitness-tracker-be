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
		 ExerciseDTO exerciseDTO = exerciseService.processExerciseData(fullExerciseData);
		 System.out.println("@PostMapping(\"/workoutlogins\") Mapped DTO: " + exerciseDTO + " userId: " + exerciseDTO.getUserId());
		 Exercise exercise = exerciseService.convertExerciseDTOToExercise(exerciseDTO);
//		 todaysWorkoutRepository.findByUserId(exercise.getUserId());
		 todaysWorkoutRepository.findByUserId(exerciseDTO.getUserId());
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