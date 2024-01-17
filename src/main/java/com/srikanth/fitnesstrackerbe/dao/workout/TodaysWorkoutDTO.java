package com.srikanth.fitnesstrackerbe.dao.workout;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
public class TodaysWorkoutDTO {
	private Integer id;
	private Integer userId;
	private Date date;
	private List<ExerciseDTO> exercises = new ArrayList<>();

	// Constructors
	public TodaysWorkoutDTO() {

	}
	public TodaysWorkoutDTO(Integer id, Integer userId, Date date, List<ExerciseDTO> exercises) {
		super();
		this.id = id;
		this.userId = userId;
		this.date = date;
		this.exercises = exercises;
	}

	// Getters
	public Integer getUserId() {
		return userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public List<ExerciseDTO> getExercises() {
		return exercises;
	}

	// Setters
	public void setUserId(Integer userId) {
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
		return "TodaysWorkoutDTO{" + "userId=" + userId + ", date=" + date + ", exercises=" + exercises + '}';
	}
}