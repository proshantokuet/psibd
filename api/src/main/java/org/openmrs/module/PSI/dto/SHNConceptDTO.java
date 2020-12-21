package org.openmrs.module.PSI.dto;

import java.util.Collection;

public class SHNConceptDTO {
	
	private Integer conceptId;
	
	private Boolean retired = false;
		
	private String retireReason;
	
	private SHNConceptDataTypeDTO datatype;
	
	private SHNConceptClassDTO conceptClass;
	
	private Boolean set = false;
	
	private Collection<SHNConceptNameDTO> names;
	
	private Collection<ConceptAnswerDTO> answers;
	
	private Collection<SHNConceptSetDTO> conceptSets;
	
	private Collection<ConceptDescriptionDTO> descriptions;
	
	private String uuid;
	
	private boolean isNumeric;
	
	private Double hiAbsolute;
	
	private Double hiCritical;
	
	private Double hiNormal;
	
	private Double lowAbsolute;
	
	private Double lowCritical;
	
	private Double lowNormal;
	
	private String units;
	
	private Boolean allowDecimal;
	
	private Integer displayPrecision;

	public Integer getConceptId() {
		return conceptId;
	}

	public void setConceptId(Integer conceptId) {
		this.conceptId = conceptId;
	}

	public Boolean getRetired() {
		return retired;
	}

	public void setRetired(Boolean retired) {
		this.retired = retired;
	}

	public String getRetireReason() {
		return retireReason;
	}

	public void setRetireReason(String retireReason) {
		this.retireReason = retireReason;
	}

	public SHNConceptDataTypeDTO getDatatype() {
		return datatype;
	}

	public void setDatatype(SHNConceptDataTypeDTO datatype) {
		this.datatype = datatype;
	}

	public SHNConceptClassDTO getConceptClass() {
		return conceptClass;
	}

	public void setConceptClass(SHNConceptClassDTO conceptClass) {
		this.conceptClass = conceptClass;
	}

	public Boolean getSet() {
		return set;
	}

	public void setSet(Boolean set) {
		this.set = set;
	}

	public Collection<SHNConceptNameDTO> getNames() {
		return names;
	}

	public void setNames(Collection<SHNConceptNameDTO> names) {
		this.names = names;
	}

	public Collection<ConceptAnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<ConceptAnswerDTO> answers) {
		this.answers = answers;
	}

	public Collection<SHNConceptSetDTO> getConceptSets() {
		return conceptSets;
	}

	public void setConceptSets(Collection<SHNConceptSetDTO> conceptSets) {
		this.conceptSets = conceptSets;
	}

	public Collection<ConceptDescriptionDTO> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Collection<ConceptDescriptionDTO> descriptions) {
		this.descriptions = descriptions;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isNumeric() {
		return isNumeric;
	}

	public void setNumeric(boolean isNumeric) {
		this.isNumeric = isNumeric;
	}

	public Double getHiAbsolute() {
		return hiAbsolute;
	}

	public void setHiAbsolute(Double hiAbsolute) {
		this.hiAbsolute = hiAbsolute;
	}

	public Double getHiCritical() {
		return hiCritical;
	}

	public void setHiCritical(Double hiCritical) {
		this.hiCritical = hiCritical;
	}

	public Double getHiNormal() {
		return hiNormal;
	}

	public void setHiNormal(Double hiNormal) {
		this.hiNormal = hiNormal;
	}

	public Double getLowAbsolute() {
		return lowAbsolute;
	}

	public void setLowAbsolute(Double lowAbsolute) {
		this.lowAbsolute = lowAbsolute;
	}

	public Double getLowCritical() {
		return lowCritical;
	}

	public void setLowCritical(Double lowCritical) {
		this.lowCritical = lowCritical;
	}

	public Double getLowNormal() {
		return lowNormal;
	}

	public void setLowNormal(Double lowNormal) {
		this.lowNormal = lowNormal;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Boolean getAllowDecimal() {
		return allowDecimal;
	}

	public void setAllowDecimal(Boolean allowDecimal) {
		this.allowDecimal = allowDecimal;
	}

	public Integer getDisplayPrecision() {
		return displayPrecision;
	}

	public void setDisplayPrecision(Integer displayPrecision) {
		this.displayPrecision = displayPrecision;
	}


}
