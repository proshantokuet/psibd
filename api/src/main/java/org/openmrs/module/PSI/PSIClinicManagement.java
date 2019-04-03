package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class PSIClinicManagement extends BaseOpenmrsData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int cid;
	
	private String name;
	
	private String clinicId;
	
	private String category;
	
	private String address;
	
	private String dhisId;
	
	private String description;
	
	private String division;
	
	private String district;
	
	private String upazila;
	
	private String unionName;
	
	private long timestamp;
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getClinicId() {
		return clinicId;
	}
	
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDhisId() {
		return dhisId;
	}
	
	public void setDhisId(String dhisId) {
		this.dhisId = dhisId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDivision() {
		return division;
	}
	
	public void setDivision(String division) {
		this.division = division;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getUpazila() {
		return upazila;
	}
	
	public void setUpazila(String upazila) {
		this.upazila = upazila;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getCid() {
		return cid;
	}
	
	public void setCid(int cid) {
		this.cid = cid;
	}
	
	public String getUnionName() {
		return unionName;
	}
	
	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}
	
}
