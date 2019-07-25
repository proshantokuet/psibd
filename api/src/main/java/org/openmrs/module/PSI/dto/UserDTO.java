package org.openmrs.module.PSI.dto;

public class UserDTO {
	
	private int id;
	
	private String username;
	
	private String userRole;
	
	private String role;
	
	private String fullName;
	
	private String orgUnit;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserRole() {
		return userRole;
	}
	
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getOrgUnit() {
		return orgUnit;
	}
	
	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}
	
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", userRole=" + userRole + ", role=" + role + ", fullName="
		        + fullName + ", orgUnit=" + orgUnit + "]";
	}
	
}
