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

	public ExerciseDTO processExerciseData(Map<String, Object> fullExerciseData) {
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
		System.out.println("type & exercise name: " + type + exerciseName);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> setsData = (List<Map<String, Object>>) exerciseData.get("sets");
		System.out.println("processWorkoutData in set loop: " + type + " " + exerciseName + " " + setsData);

		ExerciseDTO exerciseDTO = null;
		ObjectMapper mapper = new ObjectMapper();

		switch (type) {
		case "Cardio":
			List<CardioSetDTO> cardioSets = new ArrayList<>();
			for (Map<String, Object> setData : setsData) {
				CardioSetDTO cardioSet = mapper.convertValue(setData, CardioSetDTO.class);
				cardioSets.add(cardioSet);
			}
			exerciseDTO = ExerciseDTO.createCardioExerciseDTO(null, exerciseName, cardioSets, userId, null);
			break;
		case "Strength":
			List<StrengthSetDTO> strengthSets = new ArrayList<>();
			for (Map<String, Object> setData : setsData) {
				StrengthSetDTO strengthSet = mapper.convertValue(setData, StrengthSetDTO.class);
				strengthSets.add(strengthSet);
			}
			exerciseDTO = ExerciseDTO.createStrengthExerciseDTO(null, exerciseName, strengthSets, userId, null);
			break;
		case "Stretch":
			List<StretchSetDTO> stretchSets = new ArrayList<>();
			for (Map<String, Object> setData : setsData) {
				StretchSetDTO stretchSet = mapper.convertValue(setData, StretchSetDTO.class);
				stretchSets.add(stretchSet);
			}
			exerciseDTO = ExerciseDTO.createStretchExerciseDTO(null, exerciseName, stretchSets, userId, null);
			break;
		default:
			System.out.println("Unknown set type in setsData: " + type);
		}

		System.out.println("ProcessExercise final exerciseDTO: " + exerciseDTO);
		return exerciseDTO;

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