package com.srikanth.fitnesstrackerbe.service.workout;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.domain.workout.CardioSet;
import com.srikanth.fitnesstrackerbe.domain.workout.Exercise;
import com.srikanth.fitnesstrackerbe.domain.workout.ExerciseSet;
import com.srikanth.fitnesstrackerbe.domain.workout.StrengthSet;
import com.srikanth.fitnesstrackerbe.domain.workout.StretchSet;
import com.srikanth.fitnesstrackerbe.domain.workout.TodaysWorkout;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;
import com.srikanth.fitnesstrackerbe.repository.workout.TodaysWorkoutRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
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
		System.out.println("final exerciseDTO: " + exerciseDTO + " exerciseDTO's userId: " + exerciseDTO.getUserId());
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

	public TodaysWorkoutDTO deleteExerciseFromTodaysWorkout(Integer userId, Integer exerciseId) {
		Long exerciseIdLong = Long.valueOf(exerciseId);
		Optional<TodaysWorkout> todaysWorkoutOpt = findTodaysWorkoutByUser(userId);

		if (todaysWorkoutOpt.isPresent()) {
			TodaysWorkout todaysWorkout = todaysWorkoutOpt.get();
			boolean removed = todaysWorkout.getExercises()
					.removeIf(exercise -> exercise.getId().equals(exerciseIdLong));

			if (!removed) {
				throw new ExerciseNotFoundException("Exercise ID " + exerciseId + " not found");
			}

			TodaysWorkout savedTodaysWorkout = todaysWorkoutRepository.save(todaysWorkout);
			return returnTodaysWorkoutData(savedTodaysWorkout);
		} else {
			throw new NoWorkoutFoundException("No workout found for user ID " + userId);
		}
	}

	public class NoWorkoutFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public NoWorkoutFoundException(String message) {
			super(message);
		}
	}

	public class ExerciseNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ExerciseNotFoundException(String message) {
			super(message);
		}
	}

	public TodaysWorkoutDTO processTodaysWorkoutUpdateData(Map<String, Object> fullExerciseData, Integer userId, Integer exerciseId) {
		// Make Data to Exercise:
		ExerciseDTO exerciseDTO = exerciseService.convertDataToExerciseDTO(fullExerciseData);
		System.out.println(
				"UPDATE final exerciseDTO: " + exerciseDTO + " exerciseDTO's userId: " + exerciseDTO.getUserId());
		Exercise exerciseWithNewData = exerciseService.convertExerciseDTOToExercise(exerciseDTO);
		System.out.println("UPDATE exercise domain: " + exerciseWithNewData);

		java.util.Date todayDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(todayDate.getTime());

		// Get Saved Exercise data to update:
		System.out.println("User Id is: " + userId);
		Optional<TodaysWorkout> existingWorkout = todaysWorkoutRepository.findByUserIdAndDate(userId, sqlDate);
		TodaysWorkout todaysWorkout = null;
		if (existingWorkout.isPresent()) {
			todaysWorkout = existingWorkout.get();
		}
		System.out.println("Returned Repo with userId Id is: " + userId + todaysWorkout);

		// Add the exercise to today's workout
//		exercise.setWorkout(todaysWorkout);
//		todaysWorkout.getExercises().add(exercise);
		// Update data:
		TodaysWorkout updatedTodaysWorkout = updateExerciseInTodaysWorkout(exerciseWithNewData, todaysWorkout, exerciseId);
		System.out.println("processTodaysWorkoutUpdateData: domain->repo: " + updatedTodaysWorkout);
		TodaysWorkout savedTodaysWorkout = todaysWorkoutRepository.save(updatedTodaysWorkout);
		// Convert to DTO and return
		return returnTodaysWorkoutData(savedTodaysWorkout);
	}

	private TodaysWorkout updateExerciseInTodaysWorkout(Exercise exerciseWithNewData, TodaysWorkout todaysWorkout, Integer exerciseId) {
	    List<Exercise> todaysExercises = todaysWorkout.getExercises();
	    Long exerciseIdLong = Long.valueOf(exerciseId);
	    System.out.println("-----updateExerciseInTodaysWorkout-----");
	    System.out.println("REPO todaysExercises: " + todaysWorkout + exerciseIdLong);

	    Exercise exerciseToUpdate = todaysExercises.stream()
	            .peek(exercise -> System.out.println("Checking exercise with ID: " + exercise.getId()))
	            .filter(exercise -> {
	                System.out.println("Comparing " + exercise.getId() + " with " + exerciseId);
	                return exercise.getId().equals(exerciseIdLong);
	            })
	            .findFirst()
	            .orElse(null);

	    System.out.println("Exercise found in updateExerciseInTodaysWorkout: " + exerciseToUpdate);
	    
	    if (exerciseToUpdate != null) {
	        exerciseToUpdate.setExerciseDetail(exerciseWithNewData.getExerciseDetail());
	        exerciseToUpdate.setExerciseName(exerciseWithNewData.getExerciseName());
	        exerciseToUpdate.setType(exerciseWithNewData.getType());
//	        exerciseToUpdate.setSets(exerciseWithNewData.getSets());
	        String exerciseType = exerciseToUpdate.getType();
	        System.out.println("The type is: " + exerciseType);
	        updateExerciseSets(exerciseToUpdate, exerciseWithNewData, exerciseType);
	    } else {
	        System.out.println("No exercise found with ID: " + exerciseId);
	    }

	    System.out.println("FINISH updateExerciseInTodaysWorkout: " + todaysWorkout);
	    return todaysWorkout;
	}



	private void updateExerciseSets(Exercise exerciseToUpdate, Exercise exerciseWithUpdatedData, String exerciseType) {
		for (ExerciseSet existingSet : exerciseToUpdate.getSets()) {
			ExerciseSet sourceSetForUpdate = findMatchingSet(existingSet, exerciseWithUpdatedData.getSets());
			if (sourceSetForUpdate != null) {
				switch (exerciseType) {
				case "Strength":
					updateStrengthSetDetails((StrengthSet) existingSet, (StrengthSet) sourceSetForUpdate);
					break;
				case "Cardio":
					updateCardioSetDetails((CardioSet) existingSet, (CardioSet) sourceSetForUpdate);
					break;
				case "Stretch":
					updateStretchSetDetails((StretchSet) existingSet, (StretchSet) sourceSetForUpdate);
					break;
				default:
					throw new IllegalArgumentException("Unknown exercise type: " + exerciseType);
				}
			}
		}
	}

	private ExerciseSet findMatchingSet(ExerciseSet existingSet, List<ExerciseSet> sourceSetsWithUpdates) {
		return sourceSetsWithUpdates.stream().filter(sourceSet -> sourceSet.getId().equals(existingSet.getId()))
				.findFirst().orElse(null);
	}

	private void updateStrengthSetDetails(StrengthSet existingSet, StrengthSet sourceSet) {
		existingSet.setReps(sourceSet.getReps());
		existingSet.setWeight(sourceSet.getWeight());
	}

	private void updateCardioSetDetails(CardioSet existingSet, CardioSet sourceSet) {
		existingSet.setDistance(sourceSet.getDistance());
	}

	private void updateStretchSetDetails(StretchSet existingSet, StretchSet sourceSet) {
		existingSet.setSeconds(sourceSet.getSeconds());
	}

}