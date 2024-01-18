package com.srikanth.fitnesstrackerbe.domain.workout;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import com.srikanth.fitnesstrackerbe.domain.User;

import java.util.Date;

@Entity
public class ExerciseLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date date; // The date when the exercise was performed
	private String notes; // Any specific notes about the exercise instance

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "exerciseLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<TodaysWorkout> todaysWorkouts;

	// Constructors
	public ExerciseLog() {
	}

	public ExerciseLog(Long id, Date date, String notes, User user, List<TodaysWorkout> todaysWorkouts) {
		super();
		this.id = id;
		this.date = date;
		this.notes = notes;
		this.user = user;
		this.todaysWorkouts = todaysWorkouts;
	}



	public List<TodaysWorkout> getTodaysWorkouts() {
		return todaysWorkouts;
	}

	public void setTodaysWorkouts(List<TodaysWorkout> todaysWorkouts) {
		this.todaysWorkouts = todaysWorkouts;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ExerciseLog{" + "id=" + id + ", date=" + date + ", notes='" + notes + '\'' + ", user=" + user + '}';
	}
}
