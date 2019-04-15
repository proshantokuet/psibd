package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class PSIDHISMarker extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private int mid;
	
	private long timestamp;
	
	private String type;
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getMid() {
		return mid;
	}
	
	public void setMid(int mid) {
		this.mid = mid;
	}
	
}
