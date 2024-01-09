package com.srikanth.fitnesstrackerbe.domain.workout;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import com.srikanth.fitnesstrackerbe.domain.User;

@Entity
public class Strength extends Exercise {

    @OneToMany(mappedBy = "strength", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StrengthSet> sets;

    // Constructors
    public Strength() {
        super();
    }

    public Strength(String name, User user, List<StrengthSet> sets) {
        super(name, user);
        this.sets = sets;
    }

    // Getters and Setters
    public List<StrengthSet> getSets() {
        return sets;
    }

    public void setSets(List<StrengthSet> sets) {
        this.sets = sets;
    }

    // toString method
    @Override
    public String toString() {
        return "Strength{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", user=" + getUser() +
                ", sets=" + sets +
                '}';
    }
}