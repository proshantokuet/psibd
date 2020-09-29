package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;

public class SHNStockDetails extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int stockDetailsId;
	
	private int debit;
	
	private int credit;
	
	private Date expiryDate;
	
	private SHNStock stockId;
	
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



	public SHNStock getStockId() {
		return stockId;
	}

	public void setStockId(SHNStock stockId) {
		this.stockId = stockId;
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
