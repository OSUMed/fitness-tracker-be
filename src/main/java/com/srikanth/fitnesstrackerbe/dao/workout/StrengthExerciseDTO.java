package com.srikanth.fitnesstrackerbe.dao.workout;

import java.util.List;

public class StrengthExerciseDTO extends ExerciseDTO {
	public StrengthExerciseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "StrengthExerciseDTO{" + "id=" + getId() + ", name='" + getExerciseName() + '\'' + ", sets=" + getSets()
				+ '}';
	}

}
