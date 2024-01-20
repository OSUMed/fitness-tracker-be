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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

		Instant instant = Instant.parse((String) fullExerciseData.get("date"));
		LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		System.out.println("processWorkoutData: Received workout data: " + fullExerciseData);

//		Object exercisesDataObj = fullExerciseData.get("exerciseData");
//		if (!(exercisesDataObj instanceof List)) {
//			throw new IllegalArgumentException(
//					"Expected exerciseData to be a List, but was: " + exercisesDataObj.getClass().getName());
//		}
		@SuppressWarnings("unchecked")
		Map<String, Object> exerciseData = (Map<String, Object>) fullExerciseData.get("exerciseData");

		String type = (String) exerciseData.get("type");
		String exerciseName = (String) exerciseData.get("exerciseName");
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
//		exercise.setId(exerciseDTO.getId());
		exercise.setType(exerciseDTO.getType());
		exercise.setExerciseName(exerciseDTO.getExerciseName());

		System.out.println("convertExerciseDTOToExercise initial-- " + exercise);
		List<ExerciseSet> exerciseSets = convertSetDTOsToExerciseSets(exerciseDTO.getSets(), exercise);
		exercise.setSets(exerciseSets);
		System.out.println("convertExerciseDTOToExercise final-- " + exercise);
		return exercise;
	}

	public List<ExerciseSet> convertSetDTOsToExerciseSets(List<? extends ExerciseSetDTO> setDTOs, Exercise exercise) {
		List<ExerciseSet> exerciseSets = new ArrayList<>();
		for (ExerciseSetDTO setDTO : setDTOs) {
			ExerciseSet exerciseSet = null;
			if (setDTO instanceof CardioSetDTO) {
				CardioSet cardioSet = new CardioSet();
				cardioSet.setExercise(exercise);
//				cardioSet.setId(setDTO.getId());
				cardioSet.setDistance(((CardioSetDTO) setDTO).getDistance());
				exerciseSet = cardioSet;
			} else if (setDTO instanceof StrengthSetDTO) {
				StrengthSet strengthSet = new StrengthSet();
				strengthSet.setExercise(exercise);
				strengthSet.setReps(((StrengthSetDTO) setDTO).getReps());
				strengthSet.setWeight(((StrengthSetDTO) setDTO).getWeight());
//				strengthSet.setId(setDTO.getId());
				exerciseSet = strengthSet;
			} else if (setDTO instanceof StretchSetDTO) {
				StretchSet stretchSet = new StretchSet();
				stretchSet.setExercise(exercise);
				stretchSet.setSeconds(((StretchSetDTO) setDTO).getSeconds());
//				stretchSet.setId(setDTO.getId());
				exerciseSet = stretchSet;
			}
			if (exerciseSet != null) {
				exerciseSet.setExercise(exercise);
				exerciseSets.add(exerciseSet);
			}
		}
		System.out.println("convertSetDTOsToExerciseSets final is: " + exerciseSets);
		return exerciseSets;
	}

	public TodaysWorkoutDTO convertDomainToDTO(TodaysWorkout todaysWorkout) {
		TodaysWorkoutDTO todaysWorkoutDTO = new TodaysWorkoutDTO();
		todaysWorkoutDTO.setUserId(todaysWorkout.getUserId());

		// Convert java.util.Date to java.sql.Date
		java.util.Date utilDate = todaysWorkout.getDate();
		if (utilDate != null) {
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			todaysWorkoutDTO.setDate(sqlDate);
		}

		List<ExerciseDTO> exerciseDTOs = todaysWorkout.getExercises().stream().map(this::convertExerciseToDTO)
				.collect(Collectors.toList());
		System.out.println(
				"convertDomainToDTO exerciseDTOs -> todaysWorkoutDTO.setExercises(exerciseDTOs): " + exerciseDTOs);
		todaysWorkoutDTO.setExercises(exerciseDTOs);

		return todaysWorkoutDTO;
	}

	private ExerciseDTO convertExerciseToDTO(Exercise exercise) {
		ExerciseDTO exerciseDTO = null;
		if (exercise instanceof CardioExercise) {
			List<CardioSetDTO> cardioSetDTOs = ((CardioExercise) exercise).getSets().stream()
					.map(set -> (CardioSetDTO) convertSetToDTO(set)).collect(Collectors.toList());
			exerciseDTO = ExerciseDTO.createCardioExerciseDTO(exercise.getId(), exercise.getExerciseName(),
					cardioSetDTOs, exercise.getUser().getId(), null);
		} else if (exercise instanceof StrengthExercise) {
			List<StrengthSetDTO> strengthSetDTOs = ((StrengthExercise) exercise).getSets().stream()
					.map(set -> (StrengthSetDTO) convertSetToDTO(set)).collect(Collectors.toList());
			exerciseDTO = ExerciseDTO.createStrengthExerciseDTO(exercise.getId(), exercise.getExerciseName(),
					strengthSetDTOs, exercise.getUser().getId(), null);
		} else if (exercise instanceof StretchExercise) {
			List<StretchSetDTO> stretchSetDTOs = ((StretchExercise) exercise).getSets().stream()
					.map(set -> (StretchSetDTO) convertSetToDTO(set)).collect(Collectors.toList());
			exerciseDTO = ExerciseDTO.createStretchExerciseDTO(exercise.getId(), exercise.getExerciseName(),
					stretchSetDTOs, exercise.getUser().getId(), null);
		}
		return exerciseDTO;
	}

	private ExerciseSetDTO convertSetToDTO(ExerciseSet set) {
		if (set instanceof CardioSet) {
			CardioSet cardioSet = (CardioSet) set;
			CardioSetDTO cardioSetDTO = new CardioSetDTO();
			System.out.println("convertSetToDTO: initial cardioSet is: " + cardioSet);
			System.out.println("convertSetToDTO: initial cardioSetDTO is: " + cardioSetDTO);
			cardioSetDTO.setDistance(cardioSet.getDistance());
//			cardioSetDTO.setId(cardioSet.getId());
			System.out.println("convertSetToDTO: final product is: " + cardioSetDTO);
			return cardioSetDTO;
		} else if (set instanceof StrengthSet) {
			StrengthSet strengthSet = (StrengthSet) set;
			StrengthSetDTO strengthSetDTO = new StrengthSetDTO();
			strengthSetDTO.setReps(strengthSet.getReps());
			strengthSetDTO.setWeight(strengthSet.getWeight());
//			strengthSetDTO.setId(strengthSet.getId());
			System.out.println("convertSetToDTO: final product is: " + strengthSetDTO);
			return strengthSetDTO;
		} else if (set instanceof StretchSet) {
			StretchSet stretchSet = (StretchSet) set;
			StretchSetDTO stretchSetDTO = new StretchSetDTO();
			stretchSetDTO.setSeconds(stretchSet.getSeconds());
//			stretchSetDTO.setId(stretchSet.getId());
			System.out.println("convertSetToDTO: final product is: " + stretchSetDTO);
			return stretchSetDTO;
		} else {
			throw new IllegalArgumentException("Unknown set type: " + set.getClass().getSimpleName());
		}
	}

}