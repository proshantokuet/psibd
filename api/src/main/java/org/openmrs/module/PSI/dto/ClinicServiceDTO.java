package org.openmrs.module.PSI.dto;


public class ClinicServiceDTO {
	
	private int sid;
	
	private String name;
	
	private String code;
	
	private String category;
	
	private String provider;
	
	private float unitCost;
	
	private int psiClinicManagement;
	
	public int getSid() {
		return sid;
	}
	
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public float getUnitCost() {
		return unitCost;
	}
	
	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}
	
	public int getPsiClinicManagement() {
		return psiClinicManagement;
	}
	
	public void setPsiClinicManagement(int psiClinicManagement) {
		this.psiClinicManagement = psiClinicManagement;
	}
	
}
