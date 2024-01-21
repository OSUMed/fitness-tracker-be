package com.srikanth.fitnesstrackerbe.domain.weekplan;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class DayPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "weekly_plan_id")
    private WeeklyPlan weeklyPlan;

    private String day;
    private String duration;
    private String intensity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dayPlan")
    private List<PlannedWorkout> workouts;

    public DayPlan() {
    	
    }
    
	public DayPlan(Long id, WeeklyPlan weeklyPlan, String day, String duration, String intensity,
			List<PlannedWorkout> workouts) {
		super();
		this.id = id;
		this.weeklyPlan = weeklyPlan;
		this.day = day;
		this.duration = duration;
		this.intensity = intensity;
		this.workouts = workouts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WeeklyPlan getWeeklyPlan() {
		return weeklyPlan;
	}

	public void setWeeklyPlan(WeeklyPlan weeklyPlan) {
		this.weeklyPlan = weeklyPlan;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getIntensity() {
		return intensity;
	}

	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}

	public List<PlannedWorkout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(List<PlannedWorkout> workouts) {
		this.workouts = workouts;
	}

	@Override
	public String toString() {
		return "DayPlan [id=" + id + ", weeklyPlan=" + weeklyPlan + ", day=" + day + ", duration=" + duration
				+ ", intensity=" + intensity + ", workouts=" + workouts + "]";
	}
    

}
