package com.srikanth.fitnesstrackerbe.domain.workout;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class StrengthSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reps;
    private String weight;

    @ManyToOne
    @JoinColumn(name = "strength_id")
    private Strength strength;

    // Constructors
    public StrengthSet() {
    }

    public StrengthSet(String reps, String weight, Strength strength) {
        this.reps = reps;
        this.weight = weight;
        this.strength = strength;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Strength getStrength() {
        return strength;
    }

    public void setStrength(Strength strength) {
        this.strength = strength;
    }

    // toString method
    @Override
    public String toString() {
        return "StrengthSet{" +
                "id=" + id +
                ", reps='" + reps + '\'' +
                ", weight='" + weight + '\'' +
                ", strength=" + strength +
                '}';
    }
}
