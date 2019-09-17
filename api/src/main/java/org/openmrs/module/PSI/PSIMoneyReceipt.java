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
	
	private Set<PSIServiceProvision> services;
	
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
	
	public String getOrgUnit() {
		return orgUnit;
	}
	
	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
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
		result = prime * result + ((field1 == null) ? 0 : field1.hashCode());
		result = prime * result + ((field2 == null) ? 0 : field2.hashCode());
		result = prime * result + field3;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + mid;
		result = prime * result + ((moneyReceiptDate == null) ? 0 : moneyReceiptDate.hashCode());
		result = prime * result + ((orgUnit == null) ? 0 : orgUnit.hashCode());
		result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
		result = prime * result + ((patientUuid == null) ? 0 : patientUuid.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((referenceId == null) ? 0 : referenceId.hashCode());
		result = prime * result + ((sateliteClinicId == null) ? 0 : sateliteClinicId.hashCode());
		result = prime * result + ((servicePoint == null) ? 0 : servicePoint.hashCode());
		result = prime * result + ((services == null) ? 0 : services.hashCode());
		result = prime * result + ((shift == null) ? 0 : shift.hashCode());
		result = prime * result + ((slipNo == null) ? 0 : slipNo.hashCode());
		result = prime * result + ((teamNo == null) ? 0 : teamNo.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + ((uic == null) ? 0 : uic.hashCode());
		result = prime * result + ((wealth == null) ? 0 : wealth.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PSIMoneyReceipt other = (PSIMoneyReceipt) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (clinicCode == null) {
			if (other.clinicCode != null)
				return false;
		} else if (!clinicCode.equals(other.clinicCode))
			return false;
		if (clinicName == null) {
			if (other.clinicName != null)
				return false;
		} else if (!clinicName.equals(other.clinicName))
			return false;
		if (clinicType == null) {
			if (other.clinicType != null)
				return false;
		} else if (!clinicType.equals(other.clinicType))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (field1 == null) {
			if (other.field1 != null)
				return false;
		} else if (!field1.equals(other.field1))
			return false;
		if (field2 == null) {
			if (other.field2 != null)
				return false;
		} else if (!field2.equals(other.field2))
			return false;
		if (field3 != other.field3)
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (mid != other.mid)
			return false;
		if (moneyReceiptDate == null) {
			if (other.moneyReceiptDate != null)
				return false;
		} else if (!moneyReceiptDate.equals(other.moneyReceiptDate))
			return false;
		if (orgUnit == null) {
			if (other.orgUnit != null)
				return false;
		} else if (!orgUnit.equals(other.orgUnit))
			return false;
		if (patientName == null) {
			if (other.patientName != null)
				return false;
		} else if (!patientName.equals(other.patientName))
			return false;
		if (patientUuid == null) {
			if (other.patientUuid != null)
				return false;
		} else if (!patientUuid.equals(other.patientUuid))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (referenceId == null) {
			if (other.referenceId != null)
				return false;
		} else if (!referenceId.equals(other.referenceId))
			return false;
		if (sateliteClinicId == null) {
			if (other.sateliteClinicId != null)
				return false;
		} else if (!sateliteClinicId.equals(other.sateliteClinicId))
			return false;
		if (servicePoint == null) {
			if (other.servicePoint != null)
				return false;
		} else if (!servicePoint.equals(other.servicePoint))
			return false;
		if (services == null) {
			if (other.services != null)
				return false;
		} else if (!services.equals(other.services))
			return false;
		if (shift == null) {
			if (other.shift != null)
				return false;
		} else if (!shift.equals(other.shift))
			return false;
		if (slipNo == null) {
			if (other.slipNo != null)
				return false;
		} else if (!slipNo.equals(other.slipNo))
			return false;
		if (teamNo == null) {
			if (other.teamNo != null)
				return false;
		} else if (!teamNo.equals(other.teamNo))
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (uic == null) {
			if (other.uic != null)
				return false;
		} else if (!uic.equals(other.uic))
			return false;
		if (wealth == null) {
			if (other.wealth != null)
				return false;
		} else if (!wealth.equals(other.wealth))
			return false;
		return true;
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
