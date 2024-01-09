package com.srikanth.fitnesstrackerbe.dao.workout;

import java.util.List;

public class StretchExerciseDTO extends ExerciseDTO {
	private List<StretchSetDTO> stretchSets;

	public StretchExerciseDTO(String type, String exerciseName, List<StretchSetDTO> stretchSets) {
		super(type, exerciseName, stretchSets);
		this.stretchSets = stretchSets;
	}

	public List<StretchSetDTO> getStretchSets() {
		return stretchSets;
	}

	public void setStretchSets(List<StretchSetDTO> strengthSets) {
		this.stretchSets = strengthSets;
	}

	@Override
	public String toString() {
		return "CardioExerciseDTO [cardioSets=" + stretchSets + "]";
	}
}
