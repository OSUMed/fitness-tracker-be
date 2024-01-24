package com.srikanth.fitnesstrackerbe.domain.details;

import jakarta.persistence.Entity;

@Entity
public class StrengthExerciseDetails extends ExerciseDetails {
	private String muscle;
	
	public StrengthExerciseDetails() {
		
	}
	public StrengthExerciseDetails(String type, String name, String infoLink, String notes, String muscle) {
		super(type, name, infoLink, notes);
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
		return "StrengthExerciseDetails [muscle=" + muscle + ", getId()=" + getId() + ", getType()=" + getType()
				+ ", getName()=" + getName() + ", getInfoLink()=" + getInfoLink() + ", getNotes()=" + getNotes()
				+ ", toString()=" + "]";
	}

	

}
