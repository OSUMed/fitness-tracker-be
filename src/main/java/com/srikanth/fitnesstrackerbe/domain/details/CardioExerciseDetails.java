package com.srikanth.fitnesstrackerbe.domain.details;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import javax.validation.constraints.Min;


@Entity
public class CardioExerciseDetails extends ExerciseDetails {
//	@Min(0)
    private int duration;
	@Column(nullable = true)
    private Integer distance; 
    public CardioExerciseDetails() {
    	
    }
    public CardioExerciseDetails(String type, String name, String infoLink, String notes, int duration, Integer distance) {
		super(type, name, infoLink, notes);
		this.duration = duration;
		this.distance = distance;
	}
    
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return "CardioExerciseDetails [duration=" + duration + ", distance=" + distance + "]";
	}

    
}
