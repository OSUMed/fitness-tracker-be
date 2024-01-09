package com.srikanth.fitnesstrackerbe.domain.workout;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.srikanth.fitnesstrackerbe.dao.workout.ExerciseSetDTO;
import com.srikanth.fitnesstrackerbe.domain.User;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	private String exerciseName;
	private List<? extends ExerciseSet> sets;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "exercise_detail_id")
	private ExerciseDetail exerciseDetail;

	public Exercise() {
	}

	public ExerciseDetail getExerciseDetail() {
		return exerciseDetail;
	}

	public void setExerciseDetail(ExerciseDetail exerciseDetail) {
		this.exerciseDetail = exerciseDetail;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public List<? extends ExerciseSet> getSets() {
		return sets;
	}

	public void setSets(List<ExerciseSet> sets) {
	    this.sets = sets;
	}

	@Override
	public String toString() {
		return "Exercise [id=" + id + ", type=" + type + ", exerciseName=" + exerciseName + ", user=" + user
				+ ", exerciseDetail=" + exerciseDetail + "]";
	}

}