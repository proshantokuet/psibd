package org.openmrs.module.PSI.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterSlipTracking {
	private String startDate;
	private String endDate;
	private String dataCollector;
	
	private List<String> wealthClasses;
	private List<String> servicePoints;
	
	public SearchFilterSlipTracking() {
		this.wealthClasses = new ArrayList<String>();
		this.servicePoints = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDataCollector() {
		return dataCollector;
	}

	public void setDataCollector(String dataCollector) {
		this.dataCollector = dataCollector;
	}

	public List<String> getWealthClasses() {
		return wealthClasses;
	}

	public void setWealthClasses(List<String> wealthClasses) {
		this.wealthClasses = wealthClasses;
	}

	public List<String> getServicePoints() {
		return servicePoints;
	}

	public void setServicePoints(List<String> servicePoints) {
		this.servicePoints = servicePoints;
	}
	@Override
	public String toString() {
		return "SearchFilterSlipTrackingPSIReport [startDate=" + startDate
				+ ", endDate=" + endDate + ", dataCollector=" + dataCollector
				+ ", wealthClasses=" + wealthClasses + ", servicePoints="
				+ servicePoints + "]";
	}

	
	
}
