package org.openmrs.module.PSI.dto;

public class SHNConceptSetDTO {
	
	private Integer conceptSetId;
		
	//private SHNConceptDTO conceptSet; // parent concept that uses this set
	
	private Integer conceptId;
	
	private Integer parentConceptSet;
	
	private Double sortWeight;
	
	private String uuid;

	public Integer getConceptSetId() {
		return conceptSetId;
	}

	public void setConceptSetId(Integer conceptSetId) {
		this.conceptSetId = conceptSetId;
	}

	public Double getSortWeight() {
		return sortWeight;
	}

	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}

	public Integer getParentConceptSet() {
		return parentConceptSet;
	}

	public void setParentConceptSet(Integer parentConceptSet) {
		this.parentConceptSet = parentConceptSet;
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
