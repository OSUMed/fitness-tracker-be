package com.srikanth.fitnesstrackerbe.domain.workout;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import com.srikanth.fitnesstrackerbe.domain.User;

@Entity
public class StretchExercise extends Exercise {

    @OneToMany(mappedBy = "stretch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StretchSet> sets;

    // Constructors
    public StretchExercise() {
        super();
    }

    public StretchExercise(String name, User user, List<StretchSet> sets) {
        super();
        this.sets = sets;
    }

    // Getters and Setters
    public List<StretchSet> getSets() {
        return sets;
    }

    @Override
    public void setSets(List<ExerciseSet> sets) {
        // Need to ensure that the sets are indeed List<CardioSet> before assigning
        this.sets = sets.stream()
                        .filter(s -> s instanceof StretchSet)
                        .map(s -> (StretchSet)s)
                        .collect(Collectors.toList());
    }

    // toString method
    @Override
    public String toString() {
        return "Stretch{" +
                "id=" + getId() +
                ", name='" + getExerciseName() + '\'' +
                ", user=" + getUser() +
                ", sets=" + sets +
                '}';
    }
}