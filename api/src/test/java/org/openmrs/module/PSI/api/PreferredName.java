package org.openmrs.module.PSI.api;

import java.util.Date;

public class PreferredName {
	
	private Date dateChanged;
	
	private String name;
	
	private int id;
	
	private float unitCost;
	
	private long timestamp;
	
	public Date getDateChanged() {
		return dateChanged;
	}
	
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public float getUnitCost() {
		return unitCost;
	}
	
	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "PreferredName [dateChanged=" + dateChanged + ", name=" + name + ", id=" + id + ", unitCost=" + unitCost
		        + ", timestamp=" + timestamp + "]";
	}
	
}
