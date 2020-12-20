package org.openmrs.module.PSI.dto;

public class SHNDrugDTO {

	private int drugId;
	
	private String name;
	
	private String uuid;
	
	private String description;
	
	private String dosageFrom;
	
	private String maximumDailyDose;
	
	private String minimumDailyDose;
	
	private String concept;
	
	private boolean combination;
	
	private String strength;

	public int getDrugId() {
		return drugId;
	}

	public void setDrugId(int drugId) {
		this.drugId = drugId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDosageFrom() {
		return dosageFrom;
	}

	public void setDosageFrom(String dosageFrom) {
		this.dosageFrom = dosageFrom;
	}

	public String getMaximumDailyDose() {
		return maximumDailyDose;
	}

	public void setMaximumDailyDose(String maximumDailyDose) {
		this.maximumDailyDose = maximumDailyDose;
	}

	public String getMinimumDailyDose() {
		return minimumDailyDose;
	}

	public void setMinimumDailyDose(String minimumDailyDose) {
		this.minimumDailyDose = minimumDailyDose;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public boolean isCombination() {
		return combination;
	}

	public void setCombination(boolean combination) {
		this.combination = combination;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

}
