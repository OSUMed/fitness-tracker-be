package com.srikanth.fitnesstrackerbe.dao.workout;

import java.util.List;

public abstract class ExerciseDTO {
    private String type;
    private String exerciseName;
    private List<? extends ExerciseSetDTO> sets;

    // Constructors
    protected ExerciseDTO(String type, String exerciseName, List<? extends ExerciseSetDTO> sets) {
        this.type = type;
        this.exerciseName = exerciseName;
        this.sets = sets;
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
		return "ExerciseDTO [type=" + type + ", exerciseName=" + exerciseName + ", sets=" + sets + "]";
	}

   
   
}