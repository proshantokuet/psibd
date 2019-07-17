package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Set;

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
	
	private int divisionId;
	
	private String divisionUuid;
	
	private String district;
	
	private int districtId;
	
	private String districtUuid;
	
	private String upazila;
	
	private int upazilaId;
	
	private String upazilaUuid;
	
	private String unionName;
	
	private Set<PSIClinicUser> pSIClinicUser;
	
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
	
	public Set<PSIClinicUser> getpSIClinicUser() {
		return pSIClinicUser;
	}
	
	public void setpSIClinicUser(Set<PSIClinicUser> pSIClinicUser) {
		this.pSIClinicUser = pSIClinicUser;
	}
	
	public int getDistrictId() {
		return districtId;
	}
	
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	
	public String getDistrictUuid() {
		return districtUuid;
	}
	
	public void setDistrictUuid(String districtUuid) {
		this.districtUuid = districtUuid;
	}
	
	public int getUpazilaId() {
		return upazilaId;
	}
	
	public void setUpazilaId(int upazilaId) {
		this.upazilaId = upazilaId;
	}
	
	public String getUpazilaUuid() {
		return upazilaUuid;
	}
	
	public void setUpazilaUuid(String upazilaUuid) {
		this.upazilaUuid = upazilaUuid;
	}
	
	public int getDivisionId() {
		return divisionId;
	}
	
	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}
	
	public String getDivisionUuid() {
		return divisionUuid;
	}
	
	public void setDivisionUuid(String divisionUuid) {
		this.divisionUuid = divisionUuid;
	}
	
	@Override
	public String toString() {
		return "PSIClinicManagement [cid=" + cid + ", name=" + name + ", clinicId=" + clinicId + ", category=" + category
		        + ", address=" + address + ", dhisId=" + dhisId + ", description=" + description + ", division=" + division
		        + ", district=" + district + ", upazila=" + upazila + ", unionName=" + unionName + ", pSIClinicUser="
		        + pSIClinicUser + ", timestamp=" + timestamp + "]";
	}
	
}
