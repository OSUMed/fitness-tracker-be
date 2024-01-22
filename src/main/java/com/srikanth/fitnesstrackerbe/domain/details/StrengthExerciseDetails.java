package com.srikanth.fitnesstrackerbe.domain.details;

import jakarta.persistence.Entity;

@Entity
public class StrengthExerciseDetails extends ExerciseDetails {
	private String muscle;
	public StrengthExerciseDetails(Long id, String type, String name, String infoLink, String notes, String muscle) {
		super(id, type, name, infoLink, notes);
		this.muscle = muscle;
	}


	public String getMuscle() {
		return muscle;
	}

	public void setMuscle(String muscle) {
		this.muscle = muscle;
	}

	@Override
	public String toString() {
		return "StrengthExerciseDetails [muscle=" + muscle + "]";
	}

}