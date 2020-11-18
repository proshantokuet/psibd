package org.openmrs.module.PSI.dto;

import java.util.Date;

public class SHNFollowUPReportDTO {

	private String encounterUuid;
	
	private String visitUuid;
	
	private int valueCoded;
	
	private String codedConceptName;
	
	private Date contactDate;
	
	private boolean isResponded;
	
	private String respondResult;
	
	private boolean valueChanged;
	
	private String uuid;
	
	private int age;
	
	private String contactNumber;
	
	private String patientName;
	
	private String visitType;
	
	private String visitStart;
	
	private String visitEnd;
	
	private String followUpFor;

	private String followUpDate;
	
	private String identifier;
	
	private String clinicCode;
	
	private String followUpStatus;

	public String getEncounterUuid() {
		return encounterUuid;
	}

	public void setEncounterUuid(String encounterUuid) {
		this.encounterUuid = encounterUuid;
	}

	public String getVisitUuid() {
		return visitUuid;
	}

	public void setVisitUuid(String visitUuid) {
		this.visitUuid = visitUuid;
	}

	public int getValueCoded() {
		return valueCoded;
	}

	public void setValueCoded(int valueCoded) {
		this.valueCoded = valueCoded;
	}

	public String getCodedConceptName() {
		return codedConceptName;
	}

	public void setCodedConceptName(String codedConceptName) {
		this.codedConceptName = codedConceptName;
	}

	public Date getContactDate() {
		return contactDate;
	}

	public void setContactDate(Date contactDate) {
		this.contactDate = contactDate;
	}

	public boolean isResponded() {
		return isResponded;
	}

	public void setResponded(boolean isResponded) {
		this.isResponded = isResponded;
	}

	public String getRespondResult() {
		return respondResult;
	}

	public void setRespondResult(String respondResult) {
		this.respondResult = respondResult;
	}

	public boolean isValueChanged() {
		return valueChanged;
	}

	public void setValueChanged(boolean valueChanged) {
		this.valueChanged = valueChanged;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getVisitStart() {
		return visitStart;
	}

	public void setVisitStart(String visitStart) {
		this.visitStart = visitStart;
	}

	public String getVisitEnd() {
		return visitEnd;
	}

	public void setVisitEnd(String visitEnd) {
		this.visitEnd = visitEnd;
	}

	public String getFollowUpFor() {
		return followUpFor;
	}

	public void setFollowUpFor(String followUpFor) {
		this.followUpFor = followUpFor;
	}

	public String getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(String followUpDate) {
		this.followUpDate = followUpDate;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}

	public String getFollowUpStatus() {
		return followUpStatus;
	}

	public void setFollowUpStatus(String followUpStatus) {
		this.followUpStatus = followUpStatus;
	}
}
