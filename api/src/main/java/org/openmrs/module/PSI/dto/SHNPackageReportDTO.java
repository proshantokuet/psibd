package org.openmrs.module.PSI.dto;

public class SHNPackageReportDTO  {

	private int packageId;
	
	private String packageName;
	
	private String packageCode;	
	
	private Double accumulatedPrice;
	
	private Double packagePrice;
	
	private boolean voided;
	
	private int packageDetailsId;
	
	private String itemName;
	
	private String itemCode;
	
	private int itemId;
	
	private int quantity;
	
	private float totalPackagePrice;
	
	private float unitCost;
	
	private float itemsPriceInPackage;
	
	private String uuid;
	
	private Double packageDiscount;

	private float unitPriceInPackage;


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

	public Double getAccumulatedPrice() {
		return accumulatedPrice;
	}

	public void setAccumulatedPrice(Double accumulatedPrice) {
		this.accumulatedPrice = accumulatedPrice;
	}

	public Double getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(Double packagePrice) {
		this.packagePrice = packagePrice;
	}

	public boolean isVoided() {
		return voided;
	}

	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	public int getPackageDetailsId() {
		return packageDetailsId;
	}

	public void setPackageDetailsId(int packageDetailsId) {
		this.packageDetailsId = packageDetailsId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTotalPackagePrice() {
		return totalPackagePrice;
	}

	public void setTotalPackagePrice(float totalPackagePrice) {
		this.totalPackagePrice = totalPackagePrice;
	}

	public float getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}

	public float getItemsPriceInPackage() {
		return itemsPriceInPackage;
	}

	public void setItemsPriceInPackage(float itemsPriceInPackage) {
		this.itemsPriceInPackage = itemsPriceInPackage;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Double getPackageDiscount() {
		return packageDiscount;
	}

	public void setPackageDiscount(Double packageDiscount) {
		this.packageDiscount = packageDiscount;
	}

	public float getUnitPriceInPackage() {
		return unitPriceInPackage;
	}

	public void setUnitPriceInPackage(float unitPriceInPackage) {
		this.unitPriceInPackage = unitPriceInPackage;
	}


}
