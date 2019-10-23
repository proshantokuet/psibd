package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class AUHCClinicType extends BaseOpenmrsData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String clinicTypeName;
	private int ctid;

	public String getClinicTypeName() {
		return clinicTypeName;
	}

	public void setClinicTypeName(String _clinicTypeName) {
		this.clinicTypeName = _clinicTypeName;
	}

	public int getCtid() {
		return ctid;
	}

	public void setCtid(int ctid) {
		this.ctid = ctid;
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
		return "AUHCClinicType [typeName=" + clinicTypeName + ", ctid="
				+ ctid + "]";
	}

}
