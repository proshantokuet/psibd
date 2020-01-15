package org.openmrs.module.PSI.dto;

public class AUHCVisitReport {
	private String patient_name;
	private String hid;
	private String mobile_number;
	private String gender;
	private String age;
	private String reg_date;
	private String last_visit_date;
	private Long visit_count;
	
	
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getLast_visit_date() {
		return last_visit_date;
	}
	public void setLast_visit_date(String last_visit_date) {
		this.last_visit_date = last_visit_date;
	}
	public Long getVisit_count() {
		return visit_count;
	}
	public void setVisit_count(Long visit_count) {
		this.visit_count = visit_count;
	}
	
	
}
