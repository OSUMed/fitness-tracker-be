package com.srikanth.fitnesstrackerbe.domain.workout;

import com.srikanth.fitnesstrackerbe.dao.workout.ExerciseSetDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StrengthSet extends ExerciseSet {
	private String reps;
	private String weight;

	public StrengthSet() {
	}

	public StrengthSet(String reps, String weight) {
		this.reps = reps;
		this.weight = weight;
	}

	public String getReps() {
		return reps;
	}

	public void setReps(String reps) {
		this.reps = reps;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	// toString method
	@Override
	public String toString() {
		return "StrengthSet{" + "id=" + getId() + ", reps='" + reps + '\'' + ", weight='" + weight + '\'' + ", strength="
				 + '}';
	}
}
