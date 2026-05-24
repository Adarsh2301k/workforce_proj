package com.workforcepro.auth.dto;

import com.workforcepro.common.enums.Role;
import lombok.Getter;
import lombok.Setter;

public class RegisterRequest {

    private String email;
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	private String password;
    private Role role;
}