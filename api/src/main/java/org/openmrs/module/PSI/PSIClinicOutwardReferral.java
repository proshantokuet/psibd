package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class PSIClinicOutwardReferral extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int outwardReferralId;
	
	private String referredTo;
	
	private String provisonalDiagnosis;
	
	private String cheiefComplaints;
	
	private String referralReason;
	
	private String patientUuid;
	
	private String allergy;
	
	private String airway;
	
	private String medication;
	
	private String breathing;
	
	private String pastMedicalHistory;
	
	private String circulation;
	
	private String lastMeal;
	
	private String Conscious;
	
	private String events;
	
	private String exposure;

	public int getOutwardReferralId() {
		return outwardReferralId;
	}

	public void setOutwardReferralId(int outwardReferralId) {
		this.outwardReferralId = outwardReferralId;
	}

	public String getReferredTo() {
		return referredTo;
	}

	public void setReferredTo(String referredTo) {
		this.referredTo = referredTo;
	}

	public String getProvisonalDiagnosis() {
		return provisonalDiagnosis;
	}

	public void setProvisonalDiagnosis(String provisonalDiagnosis) {
		this.provisonalDiagnosis = provisonalDiagnosis;
	}

	public String getCheiefComplaints() {
		return cheiefComplaints;
	}

	public void setCheiefComplaints(String cheiefComplaints) {
		this.cheiefComplaints = cheiefComplaints;
	}

	public String getReferralReason() {
		return referralReason;
	}

	public void setReferralReason(String referralReason) {
		this.referralReason = referralReason;
	}

	public String getPatientUuid() {
		return patientUuid;
	}

	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}

	public String getAllergy() {
		return allergy;
	}

	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}

	public String getAirway() {
		return airway;
	}

	public void setAirway(String airway) {
		this.airway = airway;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getBreathing() {
		return breathing;
	}

	public void setBreathing(String breathing) {
		this.breathing = breathing;
	}

	public String getPastMedicalHistory() {
		return pastMedicalHistory;
	}

	public void setPastMedicalHistory(String pastMedicalHistory) {
		this.pastMedicalHistory = pastMedicalHistory;
	}

	public String getCirculation() {
		return circulation;
	}

	public void setCirculation(String circulation) {
		this.circulation = circulation;
	}

	public String getLastMeal() {
		return lastMeal;
	}

	public void setLastMeal(String lastMeal) {
		this.lastMeal = lastMeal;
	}

	public String getConscious() {
		return Conscious;
	}

	public void setConscious(String conscious) {
		Conscious = conscious;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getExposure() {
		return exposure;
	}

	public void setExposure(String exposure) {
		this.exposure = exposure;
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