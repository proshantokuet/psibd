package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class SHNEslipNoGenerate extends BaseOpenmrsData implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private int eid;
	
	private String clinicCode;
	
	private int generateId;

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}

	public int getGenerateId() {
		return generateId;
	}

	public void setGenerateId(int generateId) {
		this.generateId = generateId;
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
