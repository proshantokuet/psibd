package org.openmrs.module.PSI.dto;

import java.math.BigDecimal;

public class SHNStockReportDTO {

	private String clinicname;
	
	private String clinic_id;
	
	private int productid;
	
	private String productname;
	
	private String category;
	
	private String brandname;
	
	private String earliestExpiry;
	
	private BigDecimal starting_balance; 
	
	private BigDecimal sales;
	
	private BigDecimal adjust;
	
	private BigDecimal supply;
	
	private BigDecimal endBalance;

	public String getClinicname() {
		return clinicname;
	}

	public void setClinicname(String clinicname) {
		this.clinicname = clinicname;
	}

	public String getClinic_id() {
		return clinic_id;
	}

	public void setClinic_id(String clinic_id) {
		this.clinic_id = clinic_id;
	}

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getEarliestExpiry() {
		return earliestExpiry;
	}

	public void setEarliestExpiry(String earliestExpiry) {
		this.earliestExpiry = earliestExpiry;
	}

	public BigDecimal getStarting_balance() {
		return starting_balance;
	}

	public void setStarting_balance(BigDecimal starting_balance) {
		this.starting_balance = starting_balance;
	}

	public BigDecimal getAdjust() {
		return adjust;
	}

	public void setAdjust(BigDecimal adjust) {
		this.adjust = adjust;
	}

	public BigDecimal getSupply() {
		return supply;
	}

	public void setSupply(BigDecimal supply) {
		this.supply = supply;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	
}
