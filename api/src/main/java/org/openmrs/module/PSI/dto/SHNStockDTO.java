package org.openmrs.module.PSI.dto;

import java.util.Date;
import java.util.Set;


public class SHNStockDTO {
	
	private int stockId;
	
	private String stockInId;
	
	private String clinicName;

	private String clinicCode;
	
	private int clinicId;
	
	private Set<SHNStockDetailsDTO> stockDetails;
	
	private String invoiceNumber;
	
	private Date receiveDate;

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getStockInId() {
		return stockInId;
	}

	public void setStockInId(String stockInId) {
		this.stockInId = stockInId;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}

	public int getClinicId() {
		return clinicId;
	}

	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}

	public Set<SHNStockDetailsDTO> getStockDetails() {
		return stockDetails;
	}

	public void setStockDetails(Set<SHNStockDetailsDTO> stockDetails) {
		this.stockDetails = stockDetails;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

}
