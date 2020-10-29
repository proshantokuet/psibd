package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Set;

import org.openmrs.BaseOpenmrsData;

public class SHNPackage extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int packageId;
	
	private String packageName;
	
	private String packageCode;
	
	private Set<SHNPackageDetails> shnPackageDetails;
	
	private String clinicName;
	
	private int clinicId;
	
	private String clinicCode;
	
	private float packagePrice;
	
	

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

	public Set<SHNPackageDetails> getShnPackageDetails() {
		return shnPackageDetails;
	}

	public void setShnPackageDetails(Set<SHNPackageDetails> shnPackageDetails) {
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

	public float getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(float packagePrice) {
		this.packagePrice = packagePrice;
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
