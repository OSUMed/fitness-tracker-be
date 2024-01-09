package com.srikanth.fitnesstrackerbe.domain.workout;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CardioSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String distance;

    @ManyToOne
    @JoinColumn(name = "cardio_id")
    private Cardio cardio;

    // Constructors
    public CardioSet() {
    }

    public CardioSet(String distance, Cardio cardio) {
        this.distance = distance;
        this.cardio = cardio;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Cardio getCardio() {
        return cardio;
    }

    public void setCardio(Cardio cardio) {
        this.cardio = cardio;
    }

    // toString method
    @Override
    public String toString() {
        return "CardioSet{" +
                "id=" + id +
                ", distance='" + distance + '\'' +
                ", cardio=" + cardio +
                '}';
    }
}