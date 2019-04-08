package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.openmrs.BaseOpenmrsData;

public class PSIMoneyReceipt extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private int mid;
	
	private String patientName;
	
	private String patientUuid;
	
	private String uic;
	
	private String contact;
	
	private Date dob;
	
	private String address;
	
	private String clinicName;
	
	private String clinicCode;
	
	private String sateliteClinicId;
	
	private String teamNo;
	
	private Date moneyReceiptDate;
	
	private String reference;
	
	private String servicePoint;
	
	private String wealth;
	
	private String gender;
	
	private String slipNo;
	
	private String clinicType;
	
	private Set<PSIServiceProvision> services;
	
	private long timestamp;
	
	private String field1;
	
	private String field2;
	
	private int field3;
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	public String getPatientName() {
		return patientName;
	}
	
	public int getMid() {
		return mid;
	}
	
	public void setMid(int mid) {
		this.mid = mid;
	}
	
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	public String getUic() {
		return uic;
	}
	
	public void setUic(String uic) {
		this.uic = uic;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
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
	
	public String getSateliteClinicId() {
		return sateliteClinicId;
	}
	
	public void setSateliteClinicId(String sateliteClinicId) {
		this.sateliteClinicId = sateliteClinicId;
	}
	
	public String getTeamNo() {
		return teamNo;
	}
	
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}
	
	public Date getMoneyReceiptDate() {
		return moneyReceiptDate;
	}
	
	public void setMoneyReceiptDate(Date moneyReceiptDate) {
		this.moneyReceiptDate = moneyReceiptDate;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getServicePoint() {
		return servicePoint;
	}
	
	public void setServicePoint(String servicePoint) {
		this.servicePoint = servicePoint;
	}
	
	public String getWealth() {
		return wealth;
	}
	
	public void setWealth(String wealth) {
		this.wealth = wealth;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getSlipNo() {
		return slipNo;
	}
	
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	
	public String getClinicType() {
		return clinicType;
	}
	
	public void setClinicType(String clinicType) {
		this.clinicType = clinicType;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getField1() {
		return field1;
	}
	
	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	public String getField2() {
		return field2;
	}
	
	public void setField2(String field2) {
		this.field2 = field2;
	}
	
	public int getField3() {
		return field3;
	}
	
	public void setField3(int field3) {
		this.field3 = field3;
	}
	
	public String getPatientUuid() {
		return patientUuid;
	}
	
	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}
	
	public Set<PSIServiceProvision> getServices() {
		return services;
	}
	
	public void setServices(Set<PSIServiceProvision> services) {
		this.services = services;
	}
	
}
