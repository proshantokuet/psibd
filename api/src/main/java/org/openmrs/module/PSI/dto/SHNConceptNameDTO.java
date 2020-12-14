package org.openmrs.module.PSI.dto;

public class SHNConceptNameDTO {

	private Integer conceptNameId;
		
	private String name;
	
	private String locale;
		
	private String conceptNameType;
	
	private Boolean localePreferred = false;
	
	private Boolean voided = false;
	
	private String uuid;

	public Integer getConceptNameId() {
		return conceptNameId;
	}

	public void setConceptNameId(Integer conceptNameId) {
		this.conceptNameId = conceptNameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getConceptNameType() {
		return conceptNameType;
	}

	public void setConceptNameType(String conceptNameType) {
		this.conceptNameType = conceptNameType;
	}

	public Boolean getLocalePreferred() {
		return localePreferred;
	}

	public void setLocalePreferred(Boolean localePreferred) {
		this.localePreferred = localePreferred;
	}

	public Boolean getVoided() {
		return voided;
	}

	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
