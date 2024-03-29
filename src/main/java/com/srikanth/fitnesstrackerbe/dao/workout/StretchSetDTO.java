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

	@Override
	public String toString() {
		return "StretchSetDTO [seconds=" + seconds + "]";
	}
}