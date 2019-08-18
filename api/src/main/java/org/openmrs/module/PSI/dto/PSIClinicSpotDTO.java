package org.openmrs.module.PSI.dto;

public class PSIClinicSpotDTO {
	
	private int csid;
	
	private String name;
	
	private String code;
	
	private String address;
	
	private String dhisId;
	
	private int psiClinicManagementId;
	
	public int getCsid() {
		return csid;
	}
	
	public void setCsid(int csid) {
		this.csid = csid;
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDhisId() {
		return dhisId;
	}
	
	public void setDhisId(String dhisId) {
		this.dhisId = dhisId;
	}
	
	public int getPsiClinicManagementId() {
		return psiClinicManagementId;
	}
	
	public void setPsiClinicManagementId(int psiClinicManagementId) {
		this.psiClinicManagementId = psiClinicManagementId;
	}
	
}
