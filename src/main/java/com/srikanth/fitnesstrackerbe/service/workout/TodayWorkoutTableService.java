package com.srikanth.fitnesstrackerbe.service.workout;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.srikanth.fitnesstrackerbe.dao.workout.*;

@Service
public class TodayWorkoutTableService {

	@Autowired
	private UserRepository userRepository;

	// Other repositories autowired

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
}