package com.srikanth.fitnesstrackerbe.dao.workout;


public class CardioExerciseDTO extends ExerciseDTO {
	public CardioExerciseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "CardioExerciseDTO{" + "id=" + getId() + ", name='" + getExerciseName() + '\'' + 
				", sets=" + getSets() + '}';
	}

}
