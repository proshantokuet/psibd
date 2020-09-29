package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.openmrs.BaseOpenmrsData;

public class SHNStock extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int stkid;
	
	private String stockInId;
	
	private String clinicName;

	private String clinicCode;
	
	private Set<SHNStockDetails> stockDetails;
	
	private String invoiceNumber;
	
	private Date receiveDate;
	
	public int getStkid() {
		return stkid;
	}

	public void setStkid(int stkid) {
		this.stkid = stkid;
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

	public Set<SHNStockDetails> getStockDetails() {
		return stockDetails;
	}

	public void setStockDetails(Set<SHNStockDetails> stockDetails) {
		this.stockDetails = stockDetails;
	}

	public String getStockInId() {
		return stockInId;
	}

	public void setStockInId(String stockInId) {
		this.stockInId = stockInId;
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

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
