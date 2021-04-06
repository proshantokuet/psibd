package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHNDhisIndicatorDetails;

import org.openmrs.module.PSI.HnqisToShnConfigMapping;

import org.openmrs.module.PSI.SHNDhisMultipleChoiceObsElement;
import org.openmrs.module.PSI.SHNDhisObsElement;

public interface SHNDhisObsElementDAO {
	
	public List<SHNDhisObsElement> getAllDhisElement(String formName);
	
	public List<SHNDhisMultipleChoiceObsElement> getAllMultipleChoiceDhisElement(String formName);
	
	public SHNDhisIndicatorDetails saveOrupdate(SHNDhisIndicatorDetails shnDhisIndicatorDetails);
	
	public SHNDhisIndicatorDetails getDhisIndicatorByType(String indicatorType);
	
	public int calculateCountOfFpConraceptiveMethod();
	
	public int calculateCountOfFphypertensionAndDiabetic();
	
	public int calculateCountOfFpPermanentMethod();
	
	public int calculateCountOfFpAncTakenAtleastOne();
	
	public int calculatePercentageOfFp();
	
	public int getCompletedAncFullCountFromMoneyReceipt();
	
	public List<HnqisToShnConfigMapping> getAllConfigMappingData();
	
	public String getAncCountForGovtDHis2(int month, int year);


}
