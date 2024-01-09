package com.srikanth.fitnesstrackerbe.dao.workout;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TodaysWorkoutDTO {
    private Long userId;
    private Date date;
    private List<ExerciseDTO> exercises;

    // Constructors
    public TodaysWorkoutDTO(Long userId, Date date, List<ExerciseDTO> exercises) {
        this.userId = userId;
        this.date = date;
        this.exercises = exercises;
    }

    // Getters
    public Long getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public List<ExerciseDTO> getExercises() {
        return exercises;
    }

    // Setters
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setExercises(List<ExerciseDTO> exercises) {
        this.exercises = exercises;
    }

    // toString
    @Override
    public String toString() {
        return "TodaysWorkoutDTO{" +
                "userId=" + userId +
                ", date=" + date +
                ", exercises=" + exercises +
                '}';
    }
}