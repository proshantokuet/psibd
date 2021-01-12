package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.HnqisToShnConfigMapping;
import org.openmrs.module.PSI.SHNDhisMultipleChoiceObsElement;
import org.openmrs.module.PSI.SHNDhisObsElement;

public interface SHNDhisObsElementDAO {
	
	public List<SHNDhisObsElement> getAllDhisElement(String formName);
	
	public List<SHNDhisMultipleChoiceObsElement> getAllMultipleChoiceDhisElement(String formName);
	
	public List<HnqisToShnConfigMapping> getAllConfigMappingData();


}
