package org.openmrs.module.PSI.dto;

public class PSIReportSlipTracking {
	private int sL;
	private String slipNo;
	private String slipDate;
	private String patientName;
	private String phone;
	private String wealthClassification;
	private String servicePoint;
	private int totalAmount;
	private int discount;
	private int netPayable;
	private String slipLink;
	
	public PSIReportSlipTracking() {
		// TODO Auto-generated constructor stub
	}
	public int getsL() {
		return sL;
	}
	public void setsL(int sL) {
		this.sL = sL;
	}
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
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getNetPayable() {
		return netPayable;
	}
	public void setNetPayable(int netPayable) {
		this.netPayable = netPayable;
	}
	public String getSlipLink() {
		return slipLink;
	}

	public void setSlipLink(String slipLink) {
		this.slipLink = slipLink;
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
