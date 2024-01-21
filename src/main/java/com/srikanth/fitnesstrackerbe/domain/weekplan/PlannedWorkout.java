package com.srikanth.fitnesstrackerbe.domain.weekplan;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;

@Entity
public class PlannedWorkout {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String type;

	@ManyToOne
	@JoinColumn(name = "day_plan_id")
	private DayPlan dayPlan;

	@ElementCollection
	private List<Exercise> exercises;

	public PlannedWorkout() {
	}

	public PlannedWorkout(String id, String type, DayPlan dayPlan, List<Exercise> exercises) {
		this.id = id;
		this.type = type;
		this.dayPlan = dayPlan;
		this.exercises = exercises;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	@Override
	public String toString() {
		return "PlannedWorkout{" + "id='" + id + '\'' + ", type='" + type + '\'' + ", dayPlan="
				+ (dayPlan != null ? dayPlan.getId() : "null") + ", exercises=" + exercises + '}';
	}

	@Embeddable
	public static class Exercise {
		private String name;

		public Exercise() {
		}

		public Exercise(String name) {
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
