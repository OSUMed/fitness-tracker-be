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
public class TodayWorkoutTableService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TodaysWorkoutRepository todaysWorkoutRepository;

	public TodaysWorkoutDTO processWorkoutData(Map<String, Object> workoutData) {
		Integer userId = (Integer) workoutData.get("userId");
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Date date = new Date((Long) workoutData.get("date"));

		System.out.println("Received workout data: " + workoutData);

		List<Map<String, Object>> exercisesData = (List<Map<String, Object>>) workoutData.get("workout");

		List<ExerciseDTO> exercises = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();

		for (Map<String, Object> exerciseMap : exercisesData) {
			String type = (String) exerciseMap.get("type");
			String exerciseName = (String) exerciseMap.get("exercise_name");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> setsData = (List<Map<String, Object>>) exerciseMap.get("sets");

			switch (type) {
			case "Cardio":
				List<CardioSetDTO> cardioSets = new ArrayList<>();
				for (Map<String, Object> setData : setsData) {
					CardioSetDTO set = mapper.convertValue(setData, CardioSetDTO.class);
					cardioSets.add(set);
				}
				CardioExerciseDTO cardioExercise = new CardioExerciseDTO(type, exerciseName, cardioSets);
				exercises.add(cardioExercise);
				break;
			case "Strength":
				List<StrengthSetDTO> strengthSets = new ArrayList<>();
				for (Map<String, Object> setData : setsData) {
					StrengthSetDTO set = mapper.convertValue(setData, StrengthSetDTO.class);
					strengthSets.add(set);
				}
				StrengthExerciseDTO strengthExercise = new StrengthExerciseDTO(type, exerciseName, strengthSets);
				exercises.add(strengthExercise);
				break;
			case "Stretch":
				List<StretchSetDTO> stretchSets = new ArrayList<>();
				for (Map<String, Object> setData : setsData) {
					StretchSetDTO set = mapper.convertValue(setData, StretchSetDTO.class);
					stretchSets.add(set);
				}
				StretchExerciseDTO stretchExercise = new StretchExerciseDTO(type, exerciseName, stretchSets);
				exercises.add(stretchExercise);
				break;
			default:
				System.out.println("Unknown exercise type: " + type);
			}

		}
		return new TodaysWorkoutDTO(userId, date, exercises);
	}

	public TodaysWorkoutDTO addExercise(TodaysWorkoutDTO todaysWorkoutDTO) {
		// Convert exerciseDTO to domain entity if necessary
		TodaysWorkout todayWorkout = convertToEntity(todaysWorkoutDTO);
		// Check if there's an existing workout for today
		Optional<TodaysWorkout> existingWorkout = todaysWorkoutRepository
				.findByUserIdAndDate(todaysWorkoutDTO.getUserId(), todaysWorkoutDTO.getDate());

		System.out.println("Returned workout is: " + existingWorkout);

		if (existingWorkout.isPresent()) {
			// Logic to add the exercise to the existing workout
		} else {
			// Logic to create a new workout
		}
		// Add exercise to the workout
		// Save updated workout in the database
		// Convert the updated workout to TodaysWorkoutDTO
		// Return the DTO

		// Delete Line
		return new TodaysWorkoutDTO(null, null, null);

	}

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