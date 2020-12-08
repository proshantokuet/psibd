package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class SHNDhisIndicatorDetails extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int indicatorId;
	
	private String postJson;
	
	private String response;
	
	private String url;
	
	private String error;
	
	private String indicatorType;
		
	private String referenceId;

	private int status;

	public int getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(int indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getPostJson() {
		return postJson;
	}

	public void setPostJson(String postJson) {
		this.postJson = postJson;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
