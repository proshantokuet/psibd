package org.openmrs.module.PSI.dto;

import java.util.Date;

public class SHNStockDetailsDTO {

	private int stockDetailsId;
	
	private int debit;
	
	private int credit;
	
	private Date expiryDate;
	
	private int productID;
	
	private String productName;

	public int getStockDetailsId() {
		return stockDetailsId;
	}

	public void setStockDetailsId(int stockDetailsId) {
		this.stockDetailsId = stockDetailsId;
	}

	public int getDebit() {
		return debit;
	}

	public void setDebit(int debit) {
		this.debit = debit;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
