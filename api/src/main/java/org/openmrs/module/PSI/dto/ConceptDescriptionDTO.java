package org.openmrs.module.PSI.dto;

public class ConceptDescriptionDTO {
	
	private Integer conceptDescriptionId;
		
	private String description;
	
	private String locale;
	
	private String uuid;

	public Integer getConceptDescriptionId() {
		return conceptDescriptionId;
	}

	public void setConceptDescriptionId(Integer conceptDescriptionId) {
		this.conceptDescriptionId = conceptDescriptionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
