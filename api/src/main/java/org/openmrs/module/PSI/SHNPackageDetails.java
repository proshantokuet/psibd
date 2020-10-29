package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class SHNPackageDetails extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int packageDetailsId;
	
	private int serviceProductId;
		
	private SHNPackage shnPackage;	
	
	private int quantity;
	
	private float packageItemPriceInPackage;
	

	public int getPackageDetailsId() {
		return packageDetailsId;
	}

	public void setPackageDetailsId(int packageDetailsId) {
		this.packageDetailsId = packageDetailsId;
	}

	public int getServiceProductId() {
		return serviceProductId;
	}

	public void setServiceProductId(int serviceProductId) {
		this.serviceProductId = serviceProductId;
	}



	public SHNPackage getShnPackage() {
		return shnPackage;
	}

	public void setShnPackage(SHNPackage shnPackage) {
		this.shnPackage = shnPackage;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPackageItemPriceInPackage() {
		return packageItemPriceInPackage;
	}

	public void setPackageItemPriceInPackage(float packageItemPriceInPackage) {
		this.packageItemPriceInPackage = packageItemPriceInPackage;
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
