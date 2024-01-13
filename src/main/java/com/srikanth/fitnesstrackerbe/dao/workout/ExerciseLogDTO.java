package com.srikanth.fitnesstrackerbe.dao.workout;

import java.util.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import com.srikanth.fitnesstrackerbe.domain.User;

import java.util.Date;

@Entity
public class ExerciseLogDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date; // The date when the exercise was performed
    private String notes; // Any specific notes about the exercise instance

    @OneToOne
    @JoinColumn(name = "exercise_detail_id")
    private ExerciseDetailDTO exerciseDetail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public ExerciseLogDTO() {}

    public ExerciseLogDTO(Date date, String notes, ExerciseDetailDTO exerciseDetail, User user) {
        this.date = date;
        this.notes = notes;
        this.exerciseDetail = exerciseDetail;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ExerciseDetailDTO getExerciseDetail() {
        return exerciseDetail;
    }

    public void setExerciseDetail(ExerciseDetailDTO exerciseDetail) {
        this.exerciseDetail = exerciseDetail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ExerciseLog{" +
                "id=" + id +
                ", date=" + date +
                ", notes='" + notes + '\'' +
                ", exerciseDetail=" + exerciseDetail +
                ", user=" + user +
                '}';
    }
}

