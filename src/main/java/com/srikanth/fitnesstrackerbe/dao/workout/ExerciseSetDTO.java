package com.srikanth.fitnesstrackerbe.dao.workout;

public class ExerciseSetDTO {
	private Long id;
	public ExerciseSetDTO() {
	}

	public ExerciseSetDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ExerciseSetDTO [id=" + id + "]";
	}

}
