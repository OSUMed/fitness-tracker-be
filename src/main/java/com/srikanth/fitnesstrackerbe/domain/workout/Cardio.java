package com.srikanth.fitnesstrackerbe.domain.workout;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import com.srikanth.fitnesstrackerbe.domain.User;

@Entity
public class Cardio extends Exercise {

    @OneToMany(mappedBy = "cardio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardioSet> sets;

    // Constructors
    public Cardio() {
        super();
    }

    public Cardio(String name, User user, List<CardioSet> sets) {
        super(name, user);
        this.sets = sets;
    }

    // Getters and Setters
    public List<CardioSet> getSets() {
        return sets;
    }

    public void setSets(List<CardioSet> sets) {
        this.sets = sets;
    }

    // toString method
    @Override
    public String toString() {
        return "Cardio{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", user=" + getUser() +
                ", sets=" + sets +
                '}';
    }
}
