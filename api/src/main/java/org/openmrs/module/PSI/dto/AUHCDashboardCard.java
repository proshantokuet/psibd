package org.openmrs.module.PSI.dto;

public class AUHCDashboardCard {
	private String newRegistration;
	private String oldClients;
	private String newClients;
	private String patientServed;
	private String totalServiceContact;
	private String revenueEarned;
	private String totalDiscount;
	private String noOfSlipsDraft;
	private String totalPayableDraft;
	public String getNewRegistration() {
		return newRegistration;
	}
	public void setNewRegistration(String newRegistration) {
		this.newRegistration = newRegistration;
	}
	public String getOldClients() {
		return oldClients;
	}
	public void setOldClients(String oldClients) {
		this.oldClients = oldClients;
	}
	public String getNewClients() {
		return newClients;
	}
	public void setNewClients(String newClients) {
		this.newClients = newClients;
	}
	public String getPatientServed() {
		return patientServed;
	}
	public void setPatientServed(String patientServed) {
		this.patientServed = patientServed;
	}
	public String getTotalServiceContact() {
		return totalServiceContact;
	}
	public void setTotalServiceContact(String totalServiceContact) {
		this.totalServiceContact = totalServiceContact;
	}
	public String getRevenueEarned() {
		return revenueEarned;
	}
	public void setRevenueEarned(String revenueEarned) {
		this.revenueEarned = revenueEarned;
	}
	public String getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public String getNoOfSlipsDraft() {
		return noOfSlipsDraft;
	}
	public void setNoOfSlipsDraft(String noOfSlipsDraft) {
		this.noOfSlipsDraft = noOfSlipsDraft;
	}
	public String getTotalPayableDraft() {
		return totalPayableDraft;
	}
	public void setTotalPayableDraft(String totalPayableDraft) {
		this.totalPayableDraft = totalPayableDraft;
	}
	
	public void init(){
		
		this.setNewRegistration("0");
		this.setOldClients("0");
		this.setNewClients("0");
		this.setPatientServed("0");
		this.setTotalServiceContact("0");
		this.setRevenueEarned("0");
		this.setTotalDiscount("0");
		this.setNoOfSlipsDraft("0");
		this.setTotalPayableDraft("0");
	}
	
}
