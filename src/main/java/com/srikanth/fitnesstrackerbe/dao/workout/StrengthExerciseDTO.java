package com.srikanth.fitnesstrackerbe.dao.workout;

import java.util.List;

public class StrengthExerciseDTO extends ExerciseDTO {
	private List<StrengthSetDTO> strengthSets;

	public StrengthExerciseDTO(String type, String exerciseName, List<StrengthSetDTO> strengthSets) {
		super();
		this.strengthSets = strengthSets;
	}

	
	public List<StrengthSetDTO> getStrengthSets() {
		return strengthSets;
	}


	public void setStrengthSets(List<StrengthSetDTO> strengthSets) {
		this.strengthSets = strengthSets;
	}


	@Override
	public String toString() {
		return "CardioExerciseDTO [cardioSets=" + strengthSets + "]";
	}

}
