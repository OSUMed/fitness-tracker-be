package com.srikanth.fitnesstrackerbe.domain.workout;

import jakarta.persistence.Entity;

@Entity
public class StrengthExercise extends Exercise {

    public StrengthExercise() {
        super();
    }
    
    @Override
    public String toString() {
        return "StrengthExercise{" +
                "id=" + getId() +
                ", name='" + getExerciseName() + '\'' +
                ", user=" + getUser() +
                ", sets=" + getSets() +
                '}';
    }

}