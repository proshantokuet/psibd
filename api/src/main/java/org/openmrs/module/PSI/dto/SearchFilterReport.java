package org.openmrs.module.PSI.dto;

public class SearchFilterReport {
	private String start_date;
	private String end_date;
	private String service_category;
	private String search_string;
	
	
	public String getSearch_string() {
		return search_string;
	}
	public void setSearch_string(String search_string) {
		this.search_string = search_string;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getService_category() {
		return service_category;
	}
	public void setService_category(String service_category) {
		this.service_category = service_category;
	}
}
