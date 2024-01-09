package com.srikanth.fitnesstrackerbe.domain.workout;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import com.srikanth.fitnesstrackerbe.domain.User;

@Entity
public class Stretch extends Exercise {

    @OneToMany(mappedBy = "stretch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StretchSet> sets;

    // Constructors
    public Stretch() {
        super();
    }

    public Stretch(String name, User user, List<StretchSet> sets) {
        super(name, user);
        this.sets = sets;
    }

    // Getters and Setters
    public List<StretchSet> getSets() {
        return sets;
    }

    public void setSets(List<StretchSet> sets) {
        this.sets = sets;
    }

    // toString method
    @Override
    public String toString() {
        return "Stretch{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", user=" + getUser() +
                ", sets=" + sets +
                '}';
    }
}