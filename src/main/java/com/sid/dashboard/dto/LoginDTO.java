package com.sid.dashboard.dto;

import javax.validation.constraints.NotNull;

public class LoginDTO {
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;

	public LoginDTO() {
		super();
	}

	public LoginDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
