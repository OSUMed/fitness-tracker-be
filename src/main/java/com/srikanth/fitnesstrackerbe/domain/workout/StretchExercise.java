package com.srikanth.fitnesstrackerbe.domain.workout;
import jakarta.persistence.Entity;

@Entity
public class StretchExercise extends Exercise {

	public StretchExercise() {
		super();
	}

	@Override
	public String toString() {
		return "StretchExercise{" + "id=" + getId() + ", name='" + getExerciseName() + '\'' + ", user=" + getUser()
				+ ", sets=" + getSets() + '}';
	}
}