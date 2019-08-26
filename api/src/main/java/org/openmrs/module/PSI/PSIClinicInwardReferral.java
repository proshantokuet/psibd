package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;

public class PSIClinicInwardReferral extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int inwardReferralId;
	
	private String referralNo;
	
	private Date referralDate;
	
	private String referredBy;
	
	private String cspId;
	
	private String notes;
	
	private String patientUuid;

	public int getInwardReferralId() {
		return inwardReferralId;
	}

	public void setInwardReferralId(int inwardReferralId) {
		this.inwardReferralId = inwardReferralId;
	}

	public String getReferralNo() {
		return referralNo;
	}

	public void setReferralNo(String referralNo) {
		this.referralNo = referralNo;
	}

	public Date getReferralDate() {
		return referralDate;
	}

	public void setReferralDate(Date referralDate) {
		this.referralDate = referralDate;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public String getCspId() {
		return cspId;
	}

	public void setCspId(String cspId) {
		this.cspId = cspId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPatientUuid() {
		return patientUuid;
	}

	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
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
