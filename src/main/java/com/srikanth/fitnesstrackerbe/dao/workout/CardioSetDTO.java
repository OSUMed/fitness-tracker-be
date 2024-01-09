package com.srikanth.fitnesstrackerbe.dao.workout;

public class CardioSetDTO extends ExerciseSetDTO{
    private String distance;

    public CardioSetDTO(String distance) {
    	super();
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    // toString
    @Override
    public String toString() {
        return "CardioSetDTO{" +
                "distance='" + distance + '\'' +
                '}';
    }
}
