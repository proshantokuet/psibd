package org.openmrs.module.PSI.dto;

public class SHNPackageReportDTO  {

	private int packageId;
	
	private String packageName;
	
	private String packageCode;	
	
	private Double accumulatedPrice;
	
	private Double packagePrice;
	
	private boolean voided;

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


}
