package org.openmrs.module.PSI.dto;

public class ClinicServiceDTO {
	
	private int sid;
	
	private String name;
	
	private String code;
	
	private String category;
	
	private String provider;
	
	private float unitCost;
	
	private int psiClinicManagement;
	
	private int yearTo;
	
	private int monthTo;
	
	private int daysTo;
	
	private int yearFrom;
	
	private int monthFrom;
	
	private int daysFrom;
	
	private String gender;
	
	private boolean voided;
	
	private String brandName;
	
	private float purchasePrice;
	
	private String type;
	
	private String clinicName;
	
	private long stock;
	
	public boolean isVoided() {
		return voided;
	}

	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	public int getSid() {
		return sid;
	}
	
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public float getUnitCost() {
		return unitCost;
	}
	
	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}
	
	public int getPsiClinicManagement() {
		return psiClinicManagement;
	}
	
	public void setPsiClinicManagement(int psiClinicManagement) {
		this.psiClinicManagement = psiClinicManagement;
	}
	
	public int getYearTo() {
		return yearTo;
	}
	
	public void setYearTo(int yearTo) {
		this.yearTo = yearTo;
	}
	
	public int getMonthTo() {
		return monthTo;
	}
	
	public void setMonthTo(int monthTo) {
		this.monthTo = monthTo;
	}
	
	public int getDaysTo() {
		return daysTo;
	}
	
	public void setDaysTo(int daysTo) {
		this.daysTo = daysTo;
	}
	
	public int getYearFrom() {
		return yearFrom;
	}
	
	public void setYearFrom(int yearFrom) {
		this.yearFrom = yearFrom;
	}
	
	public int getMonthFrom() {
		return monthFrom;
	}
	
	public void setMonthFrom(int monthFrom) {
		this.monthFrom = monthFrom;
	}
	
	public int getDaysFrom() {
		return daysFrom;
	}
	
	public void setDaysFrom(int daysFrom) {
		this.daysFrom = daysFrom;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public float getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}


}
