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
		java.util.Date todayDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(todayDate.getTime());




		Optional<TodaysWorkout> existingWorkout = todaysWorkoutRepository.findByUserIdAndDate(user.getId(), sqlDate);

		TodaysWorkout todaysWorkout;
		if (existingWorkout.isPresent()) {
			todaysWorkout = existingWorkout.get();
		} else {
			todaysWorkout = new TodaysWorkout();
			todaysWorkout.setUserId(user.getId());
			todaysWorkout.setDate(sqlDate);
			todaysWorkout.setExercises(new ArrayList<Exercise>());
		}
		
		// Add the exercise to today's workout
		exercise.setWorkout(todaysWorkout);
		todaysWorkout.getExercises().add(exercise);

		System.out.println("Before repo saved todays workout is: " + todaysWorkout);
		// Save today's workout
		TodaysWorkout savedTodaysWorkout = todaysWorkoutRepository.save(todaysWorkout);

		return savedTodaysWorkout;
	}

	public TodaysWorkout addExerciseToTodayWorkout(Map<String, Object> workoutData) {

		return new TodaysWorkout();
	}

	public TodaysWorkoutDTO returnTodaysWorkoutData(TodaysWorkout workoutData) {
		
		// Get the TodaysWorkout from Repo
		java.sql.Date utilDate = workoutData.getDate();
		Integer userId = workoutData.getUserId();
		System.out.println("preREPO Todays Workout Query: " + utilDate);
		Optional<TodaysWorkout> databaseTodaysWorkout = todaysWorkoutRepository.findByUserIdAndDate(userId, utilDate);
		TodaysWorkoutDTO todaysWorkoutDTO = null;
		System.out.println("postREPO Todays Workout: " + databaseTodaysWorkout);
		
		// Change it to DTO
		if (databaseTodaysWorkout.isPresent()) {
			todaysWorkoutDTO = exerciseService.convertDomainToDTO(databaseTodaysWorkout.get());
		}
		
		System.out.println("Finished TodaysWorkoutDTO: " + databaseTodaysWorkout);
		// Return DTO
		return todaysWorkoutDTO;
	}

	public Optional<TodaysWorkout> findTodaysWorkoutByUser(Integer userId) {
		return todaysWorkoutRepository.findByUserId(userId);
	
	}

	
}