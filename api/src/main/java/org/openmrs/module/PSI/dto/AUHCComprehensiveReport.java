package org.openmrs.module.PSI.dto;

public class AUHCComprehensiveReport {
	private String service_code;
	private String service_name;
	private String category;
	
	private double revenue_static;
	private double revenue_satellite;
	private double revenue_csp;
	private double revenue_total;
	
	private float service_contact_static;
	private float service_contact_satellite;
	private float service_contact_csp;
	private float service_total;
	
	private double discount_static;
	private double discount_satellite;
	private double discount_csp;
	private double discount_total;
	
	private double total_revenue;
	private double total_discount;
	
	private float total_service_contact;
	
	
	public double getTotal_revenue() {
		return total_revenue;
	}
	public void setTotal_revenue(double total_revenue) {
		this.total_revenue = total_revenue;
	}
	public double getTotal_discount() {
		return total_discount;
	}
	public void setTotal_discount(double total_discount) {
		this.total_discount = total_discount;
	}
	public float getTotal_service_contact() {
		return total_service_contact;
	}
	public void setTotal_service_contact(float total_service_contact) {
		this.total_service_contact = total_service_contact;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getRevenue_static() {
		return revenue_static;
	}
	public void setRevenue_static(double revenue_static) {
		this.revenue_static = revenue_static;
	}
	public double getRevenue_satellite() {
		return revenue_satellite;
	}
	public void setRevenue_satellite(double revenue_satellite) {
		this.revenue_satellite = revenue_satellite;
	}
	public double getRevenue_csp() {
		return revenue_csp;
	}
	public void setRevenue_csp(double revenue_csp) {
		this.revenue_csp = revenue_csp;
	}
	public double getRevenue_total() {
		return revenue_total;
	}
	public void setRevenue_total(double revenue_total) {
		this.revenue_total = revenue_total;
	}
	public float getService_contact_static() {
		return service_contact_static;
	}
	public void setService_contact_static(float service_contact_static) {
		this.service_contact_static = service_contact_static;
	}
	public float getService_contact_satellite() {
		return service_contact_satellite;
	}
	public void setService_contact_satellite(float service_contact_satellite) {
		this.service_contact_satellite = service_contact_satellite;
	}
	public float getService_contact_csp() {
		return service_contact_csp;
	}
	public void setService_contact_csp(float service_contact_csp) {
		this.service_contact_csp = service_contact_csp;
	}
	public float getService_total() {
		return service_total;
	}
	public void setService_total(float service_total) {
		this.service_total = service_total;
	}
	public double getDiscount_static() {
		return discount_static;
	}
	public void setDiscount_static(double discount_static) {
		this.discount_static = discount_static;
	}
	public double getDiscount_satellite() {
		return discount_satellite;
	}
	public void setDiscount_satellite(double discount_satellite) {
		this.discount_satellite = discount_satellite;
	}
	public double getDiscount_csp() {
		return discount_csp;
	}
	public void setDiscount_csp(double discount_csp) {
		this.discount_csp = discount_csp;
	}
	public double getDiscount_total() {
		return discount_total;
	}
	public void setDiscount_total(double discount_total) {
		this.discount_total = discount_total;
	}
	
	
		
	
}
