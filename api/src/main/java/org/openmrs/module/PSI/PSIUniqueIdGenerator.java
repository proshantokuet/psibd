package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class PSIUniqueIdGenerator extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
	
	private int uid;
	
	private static final long serialVersionUID = 1L;
	
	private String clinicCode;
	
	private int generateId;
	
	public int getGenerateId() {
		return generateId;
	}
	
	public void setGenerateId(int generateId) {
		this.generateId = generateId;
	}
	
	public String getClinicCode() {
		return clinicCode;
	}
	
	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}
	
	public int getUid() {
		return uid;
	}
	
	public void setUid(int uid) {
		this.uid = uid;
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
	
	@Override
	public String toString() {
		return "PSIUniqueIdGenerator [uid=" + uid + ", clinicCode=" + clinicCode + ", generateId=" + generateId + "]";
	}
	
}
