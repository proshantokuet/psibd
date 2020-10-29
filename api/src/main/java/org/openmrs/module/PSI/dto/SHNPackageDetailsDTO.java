package org.openmrs.module.PSI.dto;

public class SHNPackageDetailsDTO {
	
	private int packageDetailsId;
	
	private String packageItemName;
	
	private String packageItemCode;
		
	private float packageItemUnitPrice;
	
	private int quantity;
	
	private float packageItemPriceInPackage;
	
	private String uuid;
	
	private int serviceProductId;

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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getServiceProductId() {
		return serviceProductId;
	}

	public void setServiceProductId(int serviceProductId) {
		this.serviceProductId = serviceProductId;
	}

}
