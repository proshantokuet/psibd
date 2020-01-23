package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class AUHCDhisErrorVisualize extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String person_id;
	
	private String clinic_name;
	
	private String clinic_code;
	
	private String identifier;
	
	private String error;
	
	private String date_created;
	
	private String date_changed;
	
	private String mid;


	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public String getClinic_name() {
		return clinic_name;
	}

	public void setClinic_name(String clinic_name) {
		this.clinic_name = clinic_name;
	}

	public String getClinic_code() {
		return clinic_code;
	}

	public void setClinic_code(String clinic_code) {
		this.clinic_code = clinic_code;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDate_created() {
		return date_created;
	}

	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	public String getDate_changed() {
		return date_changed;
	}

	public void setDate_changed(String date_changed) {
		this.date_changed = date_changed;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
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
