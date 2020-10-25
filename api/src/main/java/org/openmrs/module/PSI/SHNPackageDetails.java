package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class SHNPackageDetails extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int packageDetailsId;
	
	private String packageItemName;
	
	private String packageItemCode;
	
	private SHNPackage shnPackage;
	
	private float packageItemUnitPrice;
	
	private int quantity;
	
	private float packageItemPriceInPackage;
	

	public int getPackageDetailsId() {
		return packageDetailsId;
	}

	public void setPackageDetailsId(int packageDetailsId) {
		this.packageDetailsId = packageDetailsId;
	}

	public String getPackageItemName() {
		return packageItemName;
	}

	public void setPackageItemName(String packageItemName) {
		this.packageItemName = packageItemName;
	}

	public String getPackageItemCode() {
		return packageItemCode;
	}

	public void setPackageItemCode(String packageItemCode) {
		this.packageItemCode = packageItemCode;
	}

	public SHNPackage getShnPackage() {
		return shnPackage;
	}

	public void setShnPackage(SHNPackage shnPackage) {
		this.shnPackage = shnPackage;
	}

	public float getPackageItemUnitPrice() {
		return packageItemUnitPrice;
	}

	public void setPackageItemUnitPrice(float packageItemUnitPrice) {
		this.packageItemUnitPrice = packageItemUnitPrice;
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
