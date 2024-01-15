package com.srikanth.fitnesstrackerbe.dao.workout;

import java.util.List;

import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.domain.workout.ExerciseDetail;
import com.srikanth.fitnesstrackerbe.domain.workout.ExerciseSet;

public abstract class ExerciseDTO {

	private Long id;
	private String type;
	private String exerciseName;
	private List<? extends ExerciseSetDTO> sets;
	private Integer userId;
	private ExerciseDetail exerciseDetail;

	public ExerciseDTO() {
	}

	public ExerciseDTO(Long id, String type, String exerciseName, List<? extends ExerciseSetDTO> sets, Integer userId,
			ExerciseDetail exerciseDetail) {
		this.id = id;
		this.type = type;
		this.exerciseName = exerciseName;
		this.sets = sets;
		this.userId = userId;
		this.exerciseDetail = exerciseDetail;
	}

    // Factory methods for each exercise type
    public static ExerciseDTO createCardioExerciseDTO(Long id, String exerciseName, List<CardioSetDTO> sets, Integer userId, ExerciseDetail exerciseDetail) {
        CardioExerciseDTO cardioExerciseDTO = new CardioExerciseDTO("Cardio", exerciseName, sets);
        cardioExerciseDTO.setId(id);
        cardioExerciseDTO.setType("Cardio");
        cardioExerciseDTO.setExerciseName(exerciseName);
        cardioExerciseDTO.setSets(sets);
        cardioExerciseDTO.setUserId(userId);
        cardioExerciseDTO.setExerciseDetail(exerciseDetail);
        return cardioExerciseDTO;
    }

    public static ExerciseDTO createStrengthExerciseDTO(Long id, String exerciseName, List<StrengthSetDTO> sets, Integer userId, ExerciseDetail exerciseDetail) {
        StrengthExerciseDTO strengthExerciseDTO = new StrengthExerciseDTO("Strength", exerciseName, sets);
        strengthExerciseDTO.setId(id);
        strengthExerciseDTO.setType("Strength");
        strengthExerciseDTO.setExerciseName(exerciseName);
        strengthExerciseDTO.setSets(sets);
        strengthExerciseDTO.setUserId(userId);
        strengthExerciseDTO.setExerciseDetail(exerciseDetail);
        return strengthExerciseDTO;
    }

    public static ExerciseDTO createStretchExerciseDTO(Long id, String exerciseName, List<StretchSetDTO> sets, Integer userId, ExerciseDetail exerciseDetail) {
        StretchExerciseDTO stretchExerciseDTO = new StretchExerciseDTO("Stretch", exerciseName, sets);
        stretchExerciseDTO.setId(id);
        stretchExerciseDTO.setType("Stretch");
        stretchExerciseDTO.setExerciseName(exerciseName);
        stretchExerciseDTO.setSets(sets);
        stretchExerciseDTO.setUserId(userId);
        stretchExerciseDTO.setExerciseDetail(exerciseDetail);
        return stretchExerciseDTO;
    }
	public ExerciseDetail getExerciseDetail() {
		return exerciseDetail;
	}

	public void setExerciseDetail(ExerciseDetail exerciseDetail) {
		this.exerciseDetail = exerciseDetail;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public List<? extends ExerciseSetDTO> getSets() {
		return sets;
	}

	public void setSets(List<? extends ExerciseSetDTO> sets) {
		this.sets = sets;
	}

	@Override
	public String toString() {
		return "ExerciseDTO [id=" + id + ", type=" + type + ", exerciseName=" + exerciseName + ", sets=" + sets
				+ ", userId=" + userId + ", exerciseDetail=" + exerciseDetail + "]";
	}

}