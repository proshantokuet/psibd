package org.openmrs.module.PSI.dto;

import java.util.Date;

public class PSIReportSlipTracking {
	private int sL;
	private String slipNo;
	private String slipDate;
	private String patientName;
	private String phone;
	private String wealthClassification;
	private String servicePoint;
	private Long totalAmount;
	private Double discount;
	private Double netPayable;
	private String slipLink;
	
	public String getSlipNo() {
		return slipNo;
	}
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	public String getSlipDate() {
		return slipDate;
	}
	public void setSlipDate(String slipDate) {
		this.slipDate = slipDate;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWealthClassification() {
		return wealthClassification;
	}
	public void setWealthClassification(String wealthClassification) {
		this.wealthClassification = wealthClassification;
	}
	public String getServicePoint() {
		return servicePoint;
	}
	public void setServicePoint(String servicePoint) {
		this.servicePoint = servicePoint;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getNetPayable() {
		return netPayable;
	}
	public void setNetPayable(Double netPayable) {
		this.netPayable = netPayable;
	}
	public String getSlipLink() {
		return slipLink;
	}
	public void setSlipLink(String slipLink) {
		this.slipLink = slipLink;
	}
	public void setsL(int sL) {
		this.sL = sL;
	}

	public PSIReportSlipTracking() {
		// TODO Auto-generated constructor stub
	}
	public int getsL() {
		return sL;
	}
	@Override
	public String toString() {
		return "PSIReportSlipTracking [sL=" + sL + ", slipNo=" + slipNo
				+ ", slipDate=" + slipDate + ", patientName=" + patientName
				+ ", phone=" + phone + ", wealthClassification="
				+ wealthClassification + ", servicePoint=" + servicePoint
				+ ", totalAmount=" + totalAmount + ", discount=" + discount
				+ ", netPayable=" + netPayable + ", slipLink=" + slipLink + "]";
	}


}
