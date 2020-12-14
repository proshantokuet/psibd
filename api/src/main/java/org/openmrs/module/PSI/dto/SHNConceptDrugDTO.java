package org.openmrs.module.PSI.dto;

import org.openmrs.Concept;

public class SHNConceptDrugDTO {

	private Integer drugId;
	
	private Boolean combination = false;
	
	//private Concept dosageForm;
	
	private Integer dosageFormConceptId;
	
	private Double maximumDailyDose;
	
	private Double minimumDailyDose;
	
	private String strength;
	
	private String uuid;

	public Integer getDrugId() {
		return drugId;
	}

	public void setDrugId(Integer drugId) {
		this.drugId = drugId;
	}

	public Boolean getCombination() {
		return combination;
	}

	public void setCombination(Boolean combination) {
		this.combination = combination;
	}

	public Double getMaximumDailyDose() {
		return maximumDailyDose;
	}

	public void setMaximumDailyDose(Double maximumDailyDose) {
		this.maximumDailyDose = maximumDailyDose;
	}

	public Double getMinimumDailyDose() {
		return minimumDailyDose;
	}

	public void setMinimumDailyDose(Double minimumDailyDose) {
		this.minimumDailyDose = minimumDailyDose;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public Integer getDosageFormConceptId() {
		return dosageFormConceptId;
	}

	public void setDosageFormConceptId(Integer dosageFormConceptId) {
		this.dosageFormConceptId = dosageFormConceptId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}	
}
