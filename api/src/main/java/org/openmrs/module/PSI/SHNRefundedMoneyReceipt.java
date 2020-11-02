package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.openmrs.BaseOpenmrsData;

public class SHNRefundedMoneyReceipt extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int refundId;
	
	private int moneyReceiptId;
	
	private String patientName;
	
	private String patientUuid;
	
	private String uic;
	
	private String contact;
	
	private Date dob;
	
	private String address;
	
	private String clinicName;
	
	private String clinicCode;
	
	private String orgUnit;
	
	private String sateliteClinicId;
	
	private String teamNo;
	
	private Date moneyReceiptDate;
	
	private String reference;
	
	private String referenceId;
	
	private String shift;
	
	private String servicePoint;
	
	private String wealth;
	
	private String gender;
	
	private String slipNo;
	
	private String clinicType;
	
	private String session;
	
	private String others;
	
	private String cspId;
	
	private Set<SHNRefundedMoneyReceiptDetails> services;
	
	private Set<SHNRefundedMoneyReceiptPaymentLog> payments;

	private long timestamp;
	
	private String field1;
	
	private String field2;
	
	private int field3;
	
	private String dataCollector;
	
	private String designation;
	
	private float totalAmount;
	
	private float totalDiscount;
	
	private Date patientRegisteredDate;
	
	private int isComplete;
	
	private String eslipNo;
	
	private float dueAmount;
	
	private float overallDiscount;
	
	private PSIClinicManagement PSIClinicManagement;

	public int getRefundId() {
		return refundId;
	}

	public void setRefundId(int refundId) {
		this.refundId = refundId;
	}

	public int getMoneyReceiptId() {
		return moneyReceiptId;
	}

	public void setMoneyReceiptId(int moneyReceiptId) {
		this.moneyReceiptId = moneyReceiptId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientUuid() {
		return patientUuid;
	}

	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
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

	public String getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
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

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
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

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getCspId() {
		return cspId;
	}

	public void setCspId(String cspId) {
		this.cspId = cspId;
	}

	public Set<SHNRefundedMoneyReceiptDetails> getServices() {
		return services;
	}

	public void setServices(Set<SHNRefundedMoneyReceiptDetails> services) {
		this.services = services;
	}

	public Set<SHNRefundedMoneyReceiptPaymentLog> getPayments() {
		return payments;
	}

	public void setPayments(Set<SHNRefundedMoneyReceiptPaymentLog> payments) {
		this.payments = payments;
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

	public String getDataCollector() {
		return dataCollector;
	}

	public void setDataCollector(String dataCollector) {
		this.dataCollector = dataCollector;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(float totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public Date getPatientRegisteredDate() {
		return patientRegisteredDate;
	}

	public void setPatientRegisteredDate(Date patientRegisteredDate) {
		this.patientRegisteredDate = patientRegisteredDate;
	}

	public int getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(int isComplete) {
		this.isComplete = isComplete;
	}

	public String getEslipNo() {
		return eslipNo;
	}

	public void setEslipNo(String eslipNo) {
		this.eslipNo = eslipNo;
	}

	public float getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(float dueAmount) {
		this.dueAmount = dueAmount;
	}

	public float getOverallDiscount() {
		return overallDiscount;
	}

	public void setOverallDiscount(float overallDiscount) {
		this.overallDiscount = overallDiscount;
	}

	public PSIClinicManagement getPSIClinicManagement() {
		return PSIClinicManagement;
	}

	public void setPSIClinicManagement(PSIClinicManagement pSIClinicManagement) {
		PSIClinicManagement = pSIClinicManagement;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
