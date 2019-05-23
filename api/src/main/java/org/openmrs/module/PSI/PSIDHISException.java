package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class PSIDHISException extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private int rid;
	
	private long timestamp;
	
	private int patientId;
	
	private String json;
	
	private String error;
	
	private String response;
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	public int getRid() {
		return rid;
	}
	
	public void setRid(int rid) {
		this.rid = rid;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getPatientId() {
		return patientId;
	}
	
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
	public String getJson() {
		return json;
	}
	
	public void setJson(String json) {
		this.json = json;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
}
