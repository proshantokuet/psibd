package org.openmrs.module.PSI.dto;

public class DashboardDTO {
	
	private int servedPatient;
	
	private int earned;
	
	private int newPatient;
	
	public int getServedPatient() {
		return servedPatient;
	}
	
	public void setServedPatient(int servedPatient) {
		this.servedPatient = servedPatient;
	}
	
	public int getEarned() {
		return earned;
	}
	
	public void setEarned(int earned) {
		this.earned = earned;
	}
	
	public int getNewPatient() {
		return newPatient;
	}
	
	public void setNewPatient(int newPatient) {
		this.newPatient = newPatient;
	}
	
}
