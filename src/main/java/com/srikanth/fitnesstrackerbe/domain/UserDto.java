package com.srikanth.fitnesstrackerbe.domain;

public class UserDto {
    private String username;
    private String roles;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public UserDto(String username, String roles) {
		super();
		this.username = username;
		this.roles = roles;
	}

}
