package org.openmrs.module.PSI.dto;

public class PSIReport {
	
	private String code;
	
	private String item;
	
	private float clinic;
	
	private float satelite;
	
	private float csp;
	
	private float total;
	
	private String total_;
	
	private int serviceCount;
	
	private String category;
	
	private double discount;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public float getClinic() {
		return clinic;
	}
	
	public void setClinic(float clinic) {
		this.clinic = clinic;
	}
	
	public float getSatelite() {
		return satelite;
	}
	
	public void setSatelite(float satelite) {
		this.satelite = satelite;
	}
	
	public float getCsp() {
		return csp;
	}
	
	public void setCsp(float csp) {
		this.csp = csp;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getServiceCount() {
		return serviceCount;
	}
	
	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}
	
	public String getTotal_() {
		return total_;
	}

	public void setTotal_(String total_) {
		this.total_ = total_;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "PSIReport [code=" + code + ", item=" + item + ", clinic=" + clinic + ", satelite=" + satelite + ", csp="
		        + csp + ", total=" + total + ", serviceCount=" + serviceCount + ", category=" + category + "]";
	}
	
}
