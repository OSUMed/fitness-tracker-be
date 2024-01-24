package com.srikanth.fitnesstrackerbe.domain.weekplan;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class PlannedWorkout {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type;

    @ManyToOne
    @JoinColumn(name = "day_plan_id")
    @JsonBackReference // Prevents infinite recursion
    private DayPlan dayPlan;

    @ElementCollection
    @CollectionTable(name = "planned_workout_exercises", joinColumns = @JoinColumn(name = "planned_workout_id"))
    @Column(name = "exercise")
	private List<WorkoutExercise> workoutExercises;

	public PlannedWorkout() {
	}

	public PlannedWorkout(Long id, String type, DayPlan dayPlan, List<WorkoutExercise> workoutExercises) {
		this.id = id;
		this.type = type;
		this.dayPlan = dayPlan;
		this.workoutExercises = workoutExercises;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DayPlan getDayPlan() {
		return dayPlan;
	}

	public void setDayPlan(DayPlan dayPlan) {
		this.dayPlan = dayPlan;
	}

	public List<WorkoutExercise> getExercises() {
		return workoutExercises;
	}

	public void setExercises(List<WorkoutExercise> workoutExercises) {
		this.workoutExercises = workoutExercises;
	}

	@Override
	public String toString() {
		return "PlannedWorkout{" + "id='" + id + '\'' + ", type='" + type + '\'' + ", dayPlan="
				+ (dayPlan != null ? dayPlan.getId() : "null") + ", exercises=" + workoutExercises + '}';
	}

	@Embeddable
	public static class WorkoutExercise {
		private String name;

		public WorkoutExercise() {
		}

		public WorkoutExercise(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Exercise{" + "name='" + name + '\'' + '}';
		}

	}
}
