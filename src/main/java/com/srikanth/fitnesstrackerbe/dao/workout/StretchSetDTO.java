package com.srikanth.fitnesstrackerbe.dao.workout;

public class StretchSetDTO extends ExerciseSetDTO {
	private String seconds;

	public StretchSetDTO() {
	}

	public StretchSetDTO(String seconds) {
		this.seconds = seconds;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	// toString method
	@Override
	public String toString() {
		return "StretchSet{" + "id=" + getId() + ", seconds='" + seconds + '\'' + '}';
	}
}