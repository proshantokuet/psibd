package org.openmrs.module.PSI.dto;

public class ConceptAnswerDTO {
	
	private Integer conceptAnswerId;
	
	//private SHNConceptDTO answerConcept;
	
	private Integer conceptId;
	
	private Integer answerConceptId;
	
	private SHNConceptDrugDTO answerDrug;
	
	private Double sortWeight;
	
	private String uuid;

	public Integer getConceptAnswerId() {
		return conceptAnswerId;
	}

	public void setConceptAnswerId(Integer conceptAnswerId) {
		this.conceptAnswerId = conceptAnswerId;
	}

	public SHNConceptDrugDTO getAnswerDrug() {
		return answerDrug;
	}

	public void setAnswerDrug(SHNConceptDrugDTO answerDrug) {
		this.answerDrug = answerDrug;
	}

	public Double getSortWeight() {
		return sortWeight;
	}

	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}

	public Integer getAnswerConceptId() {
		return answerConceptId;
	}

	public void setAnswerConceptId(Integer answerConceptId) {
		this.answerConceptId = answerConceptId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getConceptId() {
		return conceptId;
	}

	public void setConceptId(Integer conceptId) {
		this.conceptId = conceptId;
	}
}
