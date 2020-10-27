package org.openmrs.module.PSI.dto;

import java.util.Set;

public class SHNPackageDTO {
	
	private int packageId;
	
	private String packageName;
	
	private String packageCode;
	
	private Set<SHNPackageDetailsDTO> shnPackageDetails;
	
	private String clinicName;
	
	private int clinicId;
	
	private String clinicCode;
	
	private float accumulatedPrice;
	
	private float packagePrice;
	
	private Boolean voided;
	
	private String uuid;

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public Set<SHNPackageDetailsDTO> getShnPackageDetails() {
		return shnPackageDetails;
	}

	public void setShnPackageDetails(Set<SHNPackageDetailsDTO> shnPackageDetails) {
		this.shnPackageDetails = shnPackageDetails;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public int getClinicId() {
		return clinicId;
	}

	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}

	public float getAccumulatedPrice() {
		return accumulatedPrice;
	}

	public void setAccumulatedPrice(float accumulatedPrice) {
		this.accumulatedPrice = accumulatedPrice;
	}

	public float getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(float packagePrice) {
		this.packagePrice = packagePrice;
	}

	public Boolean getVoided() {
		return voided;
	}

	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
