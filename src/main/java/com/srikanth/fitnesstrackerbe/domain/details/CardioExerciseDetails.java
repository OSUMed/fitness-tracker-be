package com.srikanth.fitnesstrackerbe.domain.details;

import jakarta.persistence.Entity;

@Entity
public class CardioExerciseDetails extends ExerciseDetails {
    private int duration;
    private Integer distance; 
    
    public CardioExerciseDetails(Long id, String type, String name, String infoLink, String notes, int duration, Integer distance) {
		super(id, type, name, infoLink, notes);
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
