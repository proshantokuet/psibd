package org.openmrs.module.PSI.dto;

public class UserDTO {
	
	private int id;
	
	private String username;
	
	private String userName;
	
	private String userRole;
	
	private String role;
	
	private String fullName;
	
	private String orgUnit;
	
	private String mobile;
	
	private String email;
	
	private int cuid;
	
	private int userId;
	
	private String gender;
	
	private int personId;
	
	private String firstName;
	
	private String lastName;
	
	private int clinicId;
	
	private String roles;
	
	private String password;
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getClinicId() {
		return clinicId;
	}
	
	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}
	
	public String getRoles() {
		return roles;
	}
	
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public int getCuid() {
		return cuid;
	}
	
	public void setCuid(int cuid) {
		this.cuid = cuid;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getPersonId() {
		return personId;
	}
	
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
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
