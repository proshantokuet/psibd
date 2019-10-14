package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class AUHCClinicType extends BaseOpenmrsData implements Serializable {

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getCtid() {
		return ctid;
	}

	public void setCtid(int ctid) {
		this.ctid = ctid;
	}

	
	private static final long serialVersionUID = 1L;
	private String typeName;
	private int ctid;

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
		return "AUHCClinicType [typeName=" + typeName + ", ctid="
				+ ctid + "]";
	}

}
