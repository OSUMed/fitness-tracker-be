package com.srikanth.fitnesstrackerbe.dao.workout;

import java.util.List;

import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.domain.workout.ExerciseDetail;
import com.srikanth.fitnesstrackerbe.domain.workout.ExerciseSet;

public abstract class ExerciseDTO {

	private Long id;
	private String type;
	private String exerciseName;
	private  List<? extends ExerciseSetDTO> sets;
	private User user;
	private ExerciseDetail exerciseDetail;

	public ExerciseDTO() {
	}
    protected ExerciseDTO(String type, String exerciseName, List<? extends ExerciseSetDTO> sets) {
        this.type = type;
        this.exerciseName = exerciseName;
        this.sets = sets;
    }

	public ExerciseDetail getExerciseDetail() {
		return exerciseDetail;
	}

	public void setExerciseDetail(ExerciseDetail exerciseDetail) {
		this.exerciseDetail = exerciseDetail;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "Exercise [id=" + id + ", type=" + type + ", exerciseName=" + exerciseName + ", user=" + user
				+ ", exerciseDetail=" + exerciseDetail + "]";
	}

}