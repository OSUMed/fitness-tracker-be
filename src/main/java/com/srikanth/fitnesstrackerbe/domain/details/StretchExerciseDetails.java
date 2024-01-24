package com.srikanth.fitnesstrackerbe.domain.details;
import jakarta.persistence.Entity;

@Entity
public class StretchExerciseDetails  extends ExerciseDetails {
    private int duration;
    private String difficulty;
    public StretchExerciseDetails() {
    	
    }
	public StretchExerciseDetails(String type, String name, String infoLink, String notes, int duration, String difficulty) {
		super();
		this.duration = duration;
		this.difficulty = difficulty;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	@Override
	public String toString() {
		return "StretchExerciseDetails [duration=" + duration + ", difficulty=" + difficulty + ", toString()="
				+ super.toString() + "]";
	}

    
}
