package org.openmrs.module.PSI.dto;

public class ShnIndicatorDetailsDTO {

	private int fpContraceptiveMethod;
	
	private int fpHypertensionAndDiabetic;
	
	private int fpPermanentMethod;
	
	private int fpAncTakenAtleastOne;
	
	private int calculatePercentageOfFp;
	
	private int calculateAncAllTakenFullCount;

	public int getFpContraceptiveMethod() {
		return fpContraceptiveMethod;
	}

	public void setFpContraceptiveMethod(int fpContraceptiveMethod) {
		this.fpContraceptiveMethod = fpContraceptiveMethod;
	}

	public int getFpHypertensionAndDiabetic() {
		return fpHypertensionAndDiabetic;
	}

	public void setFpHypertensionAndDiabetic(int fpHypertensionAndDiabetic) {
		this.fpHypertensionAndDiabetic = fpHypertensionAndDiabetic;
	}

	public int getFpPermanentMethod() {
		return fpPermanentMethod;
	}

	public void setFpPermanentMethod(int fpPermanentMethod) {
		this.fpPermanentMethod = fpPermanentMethod;
	}

	public int getFpAncTakenAtleastOne() {
		return fpAncTakenAtleastOne;
	}

	public void setFpAncTakenAtleastOne(int fpAncTakenAtleastOne) {
		this.fpAncTakenAtleastOne = fpAncTakenAtleastOne;
	}

	public int getCalculatePercentageOfFp() {
		return calculatePercentageOfFp;
	}

	public void setCalculatePercentageOfFp(int calculatePercentageOfFp) {
		this.calculatePercentageOfFp = calculatePercentageOfFp;
	}

	public int getCalculateAncAllTakenFullCount() {
		return calculateAncAllTakenFullCount;
	}

	public void setCalculateAncAllTakenFullCount(int calculateAncAllTakenFullCount) {
		this.calculateAncAllTakenFullCount = calculateAncAllTakenFullCount;
	}


}
