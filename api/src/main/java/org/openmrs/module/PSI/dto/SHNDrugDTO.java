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
	
	private Boolean combination;
	
	private String strength;
	
	private Boolean retired;
	
	private String retireReason;
	
	private String eventId;

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

	public Boolean getCombination() {
		return combination;
	}

	public void setCombination(Boolean combination) {
		this.combination = combination;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
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

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}


}
