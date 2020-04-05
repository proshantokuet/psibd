package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class SHNDhisObsElement extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private int eid;
	
	private String elementName;
	
	private String elementDhisId;

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getElementDhisId() {
		return elementDhisId;
	}

	public void setElementDhisId(String elementDhisId) {
		this.elementDhisId = elementDhisId;
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
