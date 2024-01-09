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
import com.srikanth.fitnesstrackerbe.domain.User;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "exercise_detail_id")
	private ExerciseDetail exerciseDetail;

	public Exercise() {
	}

	public Exercise(String name, User user) {
		this.name = name;
		this.user = user;
	}

	public ExerciseDetail getExerciseDetail() {
		return exerciseDetail;
	}

	public void setExerciseDetail(ExerciseDetail exerciseDetail) {
		this.exerciseDetail = exerciseDetail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Exercise{" + "id=" + id + ", name='" + name + '\'' + ", user=" + user + '}';
	}
}