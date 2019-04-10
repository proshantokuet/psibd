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
	
	private String referenceId;
	
	private String shift;
	
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
	
	private PSIClinicManagement PSIClinicManagement;
	
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
	
	public PSIClinicManagement getPSIClinicManagement() {
		return PSIClinicManagement;
	}
	
	public void setPSIClinicManagement(PSIClinicManagement pSIClinicManagement) {
		PSIClinicManagement = pSIClinicManagement;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((clinicCode == null) ? 0 : clinicCode.hashCode());
		result = prime * result + ((clinicName == null) ? 0 : clinicName.hashCode());
		result = prime * result + ((clinicType == null) ? 0 : clinicType.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + mid;
		result = prime * result + ((moneyReceiptDate == null) ? 0 : moneyReceiptDate.hashCode());
		result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
		result = prime * result + ((patientUuid == null) ? 0 : patientUuid.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((referenceId == null) ? 0 : referenceId.hashCode());
		result = prime * result + ((sateliteClinicId == null) ? 0 : sateliteClinicId.hashCode());
		result = prime * result + ((servicePoint == null) ? 0 : servicePoint.hashCode());
		
		result = prime * result + ((shift == null) ? 0 : shift.hashCode());
		result = prime * result + ((slipNo == null) ? 0 : slipNo.hashCode());
		result = prime * result + ((teamNo == null) ? 0 : teamNo.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + ((uic == null) ? 0 : uic.hashCode());
		result = prime * result + ((wealth == null) ? 0 : wealth.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "PSIMoneyReceipt [mid=" + mid + ", patientName=" + patientName + ", patientUuid=" + patientUuid + ", uic="
		        + uic + ", contact=" + contact + ", dob=" + dob + ", address=" + address + ", clinicName=" + clinicName
		        + ", clinicCode=" + clinicCode + ", sateliteClinicId=" + sateliteClinicId + ", teamNo=" + teamNo
		        + ", moneyReceiptDate=" + moneyReceiptDate + ", reference=" + reference + ", referenceId=" + referenceId
		        + ", shift=" + shift + ", servicePoint=" + servicePoint + ", wealth=" + wealth + ", gender=" + gender
		        + ", slipNo=" + slipNo + ", clinicType=" + clinicType + ", services=" + services + "]";
	}
	
}
