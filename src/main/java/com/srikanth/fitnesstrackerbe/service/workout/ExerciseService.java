package com.srikanth.fitnesstrackerbe.service.workout;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.domain.workout.Exercise;
import com.srikanth.fitnesstrackerbe.domain.workout.CardioExercise;
import com.srikanth.fitnesstrackerbe.domain.workout.CardioSet;
import com.srikanth.fitnesstrackerbe.domain.workout.StretchSet;
import com.srikanth.fitnesstrackerbe.domain.workout.StrengthSet;
import com.srikanth.fitnesstrackerbe.domain.workout.StretchExercise;
import com.srikanth.fitnesstrackerbe.domain.workout.ExerciseSet;
import com.srikanth.fitnesstrackerbe.domain.workout.StrengthExercise;
import com.srikanth.fitnesstrackerbe.domain.workout.TodaysWorkout;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;
import com.srikanth.fitnesstrackerbe.repository.workout.ExerciseRepository;
import com.srikanth.fitnesstrackerbe.repository.workout.TodaysWorkoutRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import com.srikanth.fitnesstrackerbe.dao.workout.*;

@Service
public class ExerciseService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExerciseRepository exerciseRepository;

	public ExerciseDTO convertDataToExerciseDTO(Map<String, Object> fullExerciseData) {
		Integer userId = (Integer) fullExerciseData.get("userId");
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Date date = new Date((Long) fullExerciseData.get("date"));

		System.out.println("processWorkoutData: Received workout data: " + fullExerciseData);

//		Object exercisesDataObj = fullExerciseData.get("exerciseData");
//		if (!(exercisesDataObj instanceof List)) {
//			throw new IllegalArgumentException(
//					"Expected exerciseData to be a List, but was: " + exercisesDataObj.getClass().getName());
//		}
		@SuppressWarnings("unchecked")
		Map<String, Object> exerciseData = (Map<String, Object>) fullExerciseData.get("exerciseData");

		String type = (String) exerciseData.get("type");
		String exerciseName = (String) exerciseData.get("exercise_name");
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> setsData = (List<Map<String, Object>>) exerciseData.get("sets");
		System.out.println("preExerciseDTO type & exercise name data: " + type + " & " + exerciseName);
		System.out.println("preExerciseDTO set data: " + setsData);

		ExerciseDTO exerciseDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("preSwitch expression, type equals: " + type);
		switch (type) {
		case "Cardio":
			List<CardioSetDTO> cardioSets = new ArrayList<>();
			// Iterates over setsData with a for loop.
			for (Map<String, Object> setData : setsData) {
				// Converts each map in setsData into the appropriate SetDTO using ObjectMapper.
				CardioSetDTO cardioSet = mapper.convertValue(setData, CardioSetDTO.class);
				
				// Adds each converted SetDTO to a list.
				cardioSets.add(cardioSet);
			}
			System.out.println("Calling createCardioExerciseDTO...");
			// Creates an ExerciseDTO with the list of set DTOs.
			exerciseDTO = ExerciseDTO.createCardioExerciseDTO(null, exerciseName, cardioSets, userId, null);
			break;
		case "Strength":
			List<StrengthSetDTO> strengthSets = new ArrayList<>();
			for (Map<String, Object> setData : setsData) {
				StrengthSetDTO strengthSet = mapper.convertValue(setData, StrengthSetDTO.class);
				strengthSets.add(strengthSet);
			}
			System.out.println("Calling createStrengthExerciseDTO...");
			exerciseDTO = ExerciseDTO.createStrengthExerciseDTO(null, exerciseName, strengthSets, userId, null);
			break;
		case "Stretch":
			List<StretchSetDTO> stretchSets = new ArrayList<>();
			for (Map<String, Object> setData : setsData) {
				StretchSetDTO stretchSet = mapper.convertValue(setData, StretchSetDTO.class);
				stretchSets.add(stretchSet);
			}
			System.out.println("Calling createStretchExerciseDTO...");
			exerciseDTO = ExerciseDTO.createStretchExerciseDTO(null, exerciseName, stretchSets, userId, null);
			break;
		default:
			System.out.println("Unknown set type in setsData: " + type);
		}

	
		return exerciseDTO;

	}

	public Exercise convertExerciseDTOToExercise(ExerciseDTO exerciseDTO) {
		Exercise exercise;
		Optional<User> user = userRepository.findById(exerciseDTO.getUserId());
		if (!user.isPresent()) {
			throw new RuntimeException("User not found");
		}

		// Determine the type of exercise and instantiate the correct subclass
		switch (exerciseDTO.getType()) {
		case "Cardio":
			exercise = new CardioExercise();
			break;
		case "Strength":
			exercise = new StrengthExercise();
			break;
		case "Stretch":
			exercise = new StretchExercise();
			break;
		default:
			throw new RuntimeException("Unknown exercise type: " + exerciseDTO.getType());
		}

		exercise.setUser(user.get());
		exercise.setType(exerciseDTO.getType());
		exercise.setExerciseName(exerciseDTO.getExerciseName());

		List<ExerciseSet> exerciseSets = convertSetDTOsToExerciseSets(exerciseDTO.getSets(), exercise);
		exercise.setSets(exerciseSets);

		return exercise;
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