package com.srikanth.fitnesstrackerbe.domain.workout;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import com.srikanth.fitnesstrackerbe.domain.User;

@Entity
public class StrengthExercise extends Exercise {

    @OneToMany(mappedBy = "strength", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StrengthSet> sets;

    // Constructors
    public StrengthExercise() {
        super();
    }

    public StrengthExercise(String name, User user, List<StrengthSet> sets) {
        this.sets = sets;
    }

    // Getters and Setters
    public List<StrengthSet> getSets() {
        return sets;
    }

    @Override
    public void setSets(List<ExerciseSet> sets) {
        // Need to ensure that the sets are indeed List<CardioSet> before assigning
        this.sets = sets.stream()
                        .filter(s -> s instanceof StrengthSet)
                        .map(s -> (StrengthSet)s)
                        .collect(Collectors.toList());
    }

    // toString method
    @Override
    public String toString() {
        return "Strength{" +
                "id=" + getId() +
                ", name='" + getExerciseName() + '\'' +
                ", user=" + getUser() +
                ", sets=" + sets +
                '}';
    }
}