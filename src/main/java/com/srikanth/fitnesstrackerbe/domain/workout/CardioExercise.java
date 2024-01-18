package com.srikanth.fitnesstrackerbe.domain.workout;

import jakarta.persistence.Entity;

@Entity
public class CardioExercise extends Exercise {

	public CardioExercise() {
		super();
	}

	@Override
	public String toString() {
		return "CardioExercise{" + "id=" + getId() + ", name='" + getExerciseName() + '\'' + ", user=" + getUser()
				+ ", sets=" + getSets() + '}';
	}
}
