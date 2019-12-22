package org.openmrs.module.PSI.dto;

public class SearchFilterRegistrationReport {
	private String startDate;
	private String endDate;
	private String gender;
	
	private String wlthPoor;
	private String wlthPop;
	private String wlthAbleToPay;
	
	private String clinicCode;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWlthPoor() {
		return wlthPoor;
	}

	public void setWlthPoor(String wlthPoor) {
		this.wlthPoor = wlthPoor;
	}

	public String getWlthPop() {
		return wlthPop;
	}

	public void setWlthPop(String wlthPop) {
		this.wlthPop = wlthPop;
	}

	public String getWlthAbleToPay() {
		return wlthAbleToPay;
	}

	public void setWlthAbleToPay(String wlthAbleToPay) {
		this.wlthAbleToPay = wlthAbleToPay;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}
	
	
}
