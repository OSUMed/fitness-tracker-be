package com.srikanth.fitnesstrackerbe.domain.workout;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StretchSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seconds;

    @ManyToOne
    @JoinColumn(name = "stretch_id")
    private Stretch stretch;

    // Constructors
    public StretchSet() {
    }

    public StretchSet(String seconds, Stretch stretch) {
        this.seconds = seconds;
        this.stretch = stretch;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public Stretch getStretch() {
        return stretch;
    }

    public void setStretch(Stretch stretch) {
        this.stretch = stretch;
    }

    // toString method
    @Override
    public String toString() {
        return "StretchSet{" +
                "id=" + id +
                ", seconds='" + seconds + '\'' +
                ", stretch=" + stretch +
                '}';
    }
}