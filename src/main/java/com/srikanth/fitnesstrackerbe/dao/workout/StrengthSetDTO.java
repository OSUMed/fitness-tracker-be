package com.srikanth.fitnesstrackerbe.dao.workout;

public class StrengthSetDTO extends ExerciseSetDTO {
	private String reps;
	private String weight;

	public StrengthSetDTO() {
	}

	public StrengthSetDTO(String reps, String weight) {
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

	@Override
	public String toString() {
		return "StrengthSetDTO [reps=" + reps + ", weight=" + weight + "]";
	}


}