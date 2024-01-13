package com.srikanth.fitnesstrackerbe.dao.workout;

public class CardioSetDTO extends ExerciseSetDTO{
    private String distance;

    public CardioSetDTO() {
    }

    public CardioSetDTO(String distance) {
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
