package com.srikanth.fitnesstrackerbe.domain.workout;

import jakarta.persistence.Entity;

@Entity
public class CardioSet extends ExerciseSet {

    private String distance;

    public CardioSet() {
    }

    public CardioSet(String distance) {
        this.distance = distance;
    }

    // Getters and Setters
    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "CardioSet{" +
                "id=" + getId() +
                ", distance='" + distance + '\'' +
                '}';
    }
}
