package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;

public class AUHCServiceCategory extends BaseOpenmrsData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String categoryName;
	private int sctid;
	
	
	
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getSctid() {
		return sctid;
	}
	public void setSctid(int sctid) {
		this.sctid = sctid;
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
		return "AUHCServiceCategory [categoryName=" + categoryName + ", sctid="
				+ sctid + "]";
	}
	
	
	

}
