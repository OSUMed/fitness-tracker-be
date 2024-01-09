package com.srikanth.fitnesstrackerbe.domain.workout;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import com.srikanth.fitnesstrackerbe.domain.User;

@Entity
public class CardioExercise extends Exercise {

    @OneToMany(mappedBy = "cardio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardioSet> sets;

    // Constructors
    public CardioExercise() {
        super();
    }

    public CardioExercise(String name, User user, List<CardioSet> sets) {
        this.sets = sets;
    }

    // Getters and Setters
    public List<CardioSet> getSets() {
        return sets;
    }

    @Override
    public void setSets(List<ExerciseSet> sets) {
        // Need to ensure that the sets are indeed List<CardioSet> before assigning
        this.sets = sets.stream()
                        .filter(s -> s instanceof CardioSet)
                        .map(s -> (CardioSet)s)
                        .collect(Collectors.toList());
    }

    // toString method
    @Override
    public String toString() {
        return "Cardio{" +
                "id=" + getId() +
                ", name='" + getExerciseName() + '\'' +
                ", user=" + getUser() +
                ", sets=" + sets +
                '}';
    }
}
