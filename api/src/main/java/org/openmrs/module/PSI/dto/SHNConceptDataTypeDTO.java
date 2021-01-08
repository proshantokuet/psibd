package org.openmrs.module.PSI.dto;

public class SHNConceptDataTypeDTO {

	private Integer conceptDatatypeId;
	
	private String hl7Abbreviation;

	public Integer getConceptDatatypeId() {
		return conceptDatatypeId;
	}

	public void setConceptDatatypeId(Integer conceptDatatypeId) {
		this.conceptDatatypeId = conceptDatatypeId;
	}

	public String getHl7Abbreviation() {
		return hl7Abbreviation;
	}

	public void setHl7Abbreviation(String hl7Abbreviation) {
		this.hl7Abbreviation = hl7Abbreviation;
	}
	
}
