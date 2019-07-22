package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class PSIClinicSpot extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private int ccsid;
	
	private String name;
	
	private String code;
	
	private String address;
	
	private String dhisId;
	
	private PSIClinicManagement psiClinicManagement;
	
	public String getDhisId() {
		return dhisId;
	}
	
	public void setDhisId(String dhisId) {
		this.dhisId = dhisId;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getCcsid() {
		return ccsid;
	}
	
	public void setCcsid(int ccsid) {
		this.ccsid = ccsid;
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
	
	public PSIClinicManagement getPsiClinicManagement() {
		return psiClinicManagement;
	}
	
	public void setPsiClinicManagement(PSIClinicManagement psiClinicManagement) {
		this.psiClinicManagement = psiClinicManagement;
	}
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
}
