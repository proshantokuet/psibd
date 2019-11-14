package org.openmrs.module.PSI.dto;

public class AUHCRegistrationReport {
	private Long sl;
	private String patient_name;
	private String uic;
	private String health_id;
	private String mobile_no;
	private String gender;
	private String register_date;
	private Double age;
	private String cc;
	
	public Long getSl() {
		return sl;
	}
	public void setSl(Long sl) {
		this.sl = sl;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getUic() {
		return uic;
	}
	public void setUic(String uic) {
		this.uic = uic;
	}
	public String getHealth_id() {
		return health_id;
	}
	public void setHealth_id(String health_id) {
		this.health_id = health_id;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	public Double getAge() {
		return age;
	}
	public void setAge(Double age) {
		this.age = age;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	
	
}
