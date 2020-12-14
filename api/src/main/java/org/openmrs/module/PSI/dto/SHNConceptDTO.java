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

}
