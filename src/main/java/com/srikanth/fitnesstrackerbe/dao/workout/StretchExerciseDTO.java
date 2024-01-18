package com.srikanth.fitnesstrackerbe.dao.workout;

import java.util.List;

public class StretchExerciseDTO extends ExerciseDTO {
	public StretchExerciseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "StretchExerciseDTO{" + "id=" + getId() + ", name='" + getExerciseName() + '\'' + ", sets=" + getSets()
				+ '}';
	}
}
