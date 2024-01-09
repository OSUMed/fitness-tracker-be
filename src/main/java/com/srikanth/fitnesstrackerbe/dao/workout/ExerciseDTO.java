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

   
   
}