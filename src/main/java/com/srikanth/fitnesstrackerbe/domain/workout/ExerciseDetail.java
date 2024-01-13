package com.srikanth.fitnesstrackerbe.domain.workout;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import com.srikanth.fitnesstrackerbe.domain.User;

@Entity
public class ExerciseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // Strength, Cardio, Stretch
    private String name;
    private String muscle; // Mainly for Strength
    private Integer duration; // For Cardio and Stretch
    private Double distance; // Optional for Cardio
    private String intensity; // For Cardio
    private String difficulty; // For Stretch
    private String infoLink;
    private String notes;
	@Column
	private Integer userId;

    // One-to-Many relationship with Exercise
    @OneToMany(mappedBy = "exerciseDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises;

    // Constructors
    public ExerciseDetail() {}

    public ExerciseDetail(String type, String name, String muscle, Integer duration, Double distance, 
                          String intensity, String difficulty, String infoLink, String notes) {
        this.type = type;
        this.name = name;
        this.muscle = muscle;
        this.duration = duration;
        this.distance = distance;
        this.intensity = intensity;
        this.difficulty = difficulty;
        this.infoLink = infoLink;
        this.notes = notes;
    }



    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMuscle() {
		return muscle;
	}

	public void setMuscle(String muscle) {
		this.muscle = muscle;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getIntensity() {
		return intensity;
	}

	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getInfoLink() {
		return infoLink;
	}

	public void setInfoLink(String infoLink) {
		this.infoLink = infoLink;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	// toString method
    @Override
    public String toString() {
        return "ExerciseDetail{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", muscle='" + muscle + '\'' +
                ", duration=" + duration +
                ", distance=" + distance +
                ", intensity='" + intensity + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", infoLink='" + infoLink + '\'' +
                ", notes='" + notes + '\'' +
                ", exercises=" + exercises +
                '}';
    }
}
