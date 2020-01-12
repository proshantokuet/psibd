package org.openmrs.module.PSI.dto;

import java.util.Date;

public class PSIReportSlipTracking {
	private Long sl;
	private String slip_no;
	private String slip_date;
	private String patient_name;
	private String phone;
	private String wealth_classification;
	private String service_point;
	private Long total_amount;
	private String discount;
	private String net_payable;
	private String slip_link;
	private String patient_uuid;
	
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getNet_payable() {
		return net_payable;
	}
	public void setNet_payable(String net_payable) {
		this.net_payable = net_payable;
	}
	public String getPatient_uuid() {
		return patient_uuid;
	}
	public void setPatient_uuid(String patient_uuid) {
		this.patient_uuid = patient_uuid;
	}
	public long getSl() {
		return sl;
	}
	public void setSl(Long sl) {
		this.sl = sl;
	}
	public String getSlip_no() {
		return slip_no;
	}
	public void setSlip_no(String slip_no) {
		this.slip_no = slip_no;
	}
	public String getSlip_date() {
		return slip_date;
	}
	public void setSlip_date(String slip_date) {
		this.slip_date = slip_date;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWealth_classification() {
		return wealth_classification;
	}
	public void setWealth_classification(String wealth_classification) {
		this.wealth_classification = wealth_classification;
	}
	public String getService_point() {
		return service_point;
	}
	public void setService_point(String service_point) {
		this.service_point = service_point;
	}
	public Long getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Long total_amount) {
		this.total_amount = total_amount;
	}
	
	public void setSlip_link(String slip_link) {
		this.slip_link = slip_link;
	}
	@Override
	public String toString() {
		return "PSIReportSlipTracking [sl=" + sl + ", slip_no=" + slip_no
				+ ", slip_date=" + slip_date + ", patient_name=" + patient_name
				+ ", phone=" + phone + ", wealth_classification="
				+ wealth_classification + ", service_point=" + service_point
				+ ", total_amount=" + total_amount + ", discount=" + discount
				+ ", net_payable=" + net_payable + ", slip_link=" + slip_link
				+ "]";
	}
	
	
	
	


}
