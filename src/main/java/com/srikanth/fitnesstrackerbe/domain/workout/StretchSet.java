package com.srikanth.fitnesstrackerbe.domain.workout;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StretchSet extends ExerciseSet {
    private String seconds;

    public StretchSet() {
    }

    public StretchSet(String seconds) {
        this.seconds = seconds;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    // toString method
    @Override
    public String toString() {
        return "StretchSet{" +
                "id=" + getId() +
                ", seconds='" + seconds + '\'' +
                '}';
    }
}