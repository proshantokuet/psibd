package org.openmrs.module.PSI.dto;

public class EventReceordDTO {
	
	private int id;
	
	private String url;
	
	private String dhisResponse;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getDhisResponse() {
		return dhisResponse;
	}

	public void setDhisResponse(String dhisResponse) {
		this.dhisResponse = dhisResponse;
	}
	
}
