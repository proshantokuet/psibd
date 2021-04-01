package org.openmrs.module.PSI.dto;

public class SHNDataSyncStatusDTO {

	private String patientUuid;
	
	private String encounterUuid;
	
	private String sendToDhisFromGlobal;
	
	private String patientOrigin;

	public String getPatientUuid() {
		return patientUuid;
	}

	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}

	public String getEncounterUuid() {
		return encounterUuid;
	}

	public void setEncounterUuid(String encounterUuid) {
		this.encounterUuid = encounterUuid;
	}

	public String getSendToDhisFromGlobal() {
		return sendToDhisFromGlobal;
	}

	public void setSendToDhisFromGlobal(String sendToDhisFromGlobal) {
		this.sendToDhisFromGlobal = sendToDhisFromGlobal;
	}

	public String getPatientOrigin() {
		return patientOrigin;
	}

	public void setPatientOrigin(String patientOrigin) {
		this.patientOrigin = patientOrigin;
	}
	
	
}
